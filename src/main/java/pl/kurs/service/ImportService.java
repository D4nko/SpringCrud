package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.exceptions.ImportStatusNotFoundException;
import pl.kurs.model.ImportStatus;
import pl.kurs.repository.ImportStatusRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService {
    private static final String INSERT_BOOK_SQL = "insert into book(book_title, category, available, author_id) values (?,?,?,?)";
    private final JdbcTemplate jdbcTemplate;
    private final ImportStatusRepository importStatusRepository;

    private void save(String[] args) {
        jdbcTemplate.update(INSERT_BOOK_SQL,
                args[0], args[1], true, Integer.parseInt(args[2]));
    }

    @Transactional
    @Async("booksImportExecutor")
    public void importBook(InputStream inputStream, int id) {
        updateToProcessing(id);

        AtomicInteger counter = new AtomicInteger(0);
        AtomicLong start = new AtomicLong(System.currentTimeMillis());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines()
                    .map(line -> line.split(","))
                    .peek(command -> countTime(counter, start, id))
                    .forEach(this::save);
            updateToSuccess(id);
        } catch (IOException e) {
            log.error("Import failed due to an IOException: {}", e.getMessage());
            updateToFail(id);
            throw new RuntimeException("Import failed, see log for details.", e);
        }

    }


    private void countTime(AtomicInteger counter, AtomicLong start, int id) {
        int progress = counter.incrementAndGet();
        if (progress % 10000 == 0) {
            log.info("Imported: {} in {} ms", counter, (System.currentTimeMillis() - start.get()));
            start.set(System.currentTimeMillis());
            updateProgress(id, progress);
        }
    }

    private void updateToProcessing(int id) {
        ImportStatus toUpdate = importStatusRepository.findById(id).orElseThrow(ImportStatusNotFoundException::new);
        toUpdate.setStartDate(LocalDateTime.now());
        toUpdate.setStatus(ImportStatus.Status.PROCESSING);
        importStatusRepository.saveAndFlush(toUpdate);
    }

    private void updateProgress(int id, int progress) {
        ImportStatus toUpdate = importStatusRepository.findById(id).orElseThrow(ImportStatusNotFoundException::new);
        toUpdate.setProcessed(progress);
        importStatusRepository.saveAndFlush(toUpdate);
    }

    private void updateToSuccess(int id) {
        ImportStatus toUpdate = importStatusRepository.findById(id).orElseThrow(ImportStatusNotFoundException::new);
        toUpdate.setFinishDate(LocalDateTime.now());
        toUpdate.setStatus(ImportStatus.Status.SUCCESS);
        importStatusRepository.saveAndFlush(toUpdate);

    }

    private void updateToFail(int id) {
        ImportStatus toUpdate = importStatusRepository.findById(id).orElseThrow(ImportStatusNotFoundException::new);
        toUpdate.setFinishDate(LocalDateTime.now());
        toUpdate.setStatus(ImportStatus.Status.FAILED);
        importStatusRepository.saveAndFlush(toUpdate);
    }


    public ImportStatus startImport(String fileName) {
        return importStatusRepository.saveAndFlush(new ImportStatus(fileName));
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public ImportStatus findById(int id) {
        return importStatusRepository.findById(id).orElseThrow(ImportStatusNotFoundException::new);
    }


}
