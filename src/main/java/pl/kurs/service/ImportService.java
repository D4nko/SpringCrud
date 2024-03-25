package pl.kurs.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.exceptions.AuthorNotFoundException;
import pl.kurs.model.Book;
import pl.kurs.model.command.CreateBookCommand;
import pl.kurs.repository.AuthorRepository;
import pl.kurs.repository.BookRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_BOOK_SQL = "insert into book(book_title, category, available, author_id) values (?,?,?,?)";

    private void save(String[] args) {
        jdbcTemplate.update(INSERT_BOOK_SQL,
                args[0], args[1], true, Integer.parseInt(args[2]));
    }

    @Transactional
    @Async("booksImportExecutor")
    public void importBook(InputStream inputStream) {
        AtomicInteger counter = new AtomicInteger(0);
        AtomicLong start = new AtomicLong(System.currentTimeMillis());

        // Correct way to create a stream from an InputStream
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines()
                    .parallel()
                    .map(line -> line.split(","))
                    .peek(command -> countTime(counter, start)) // Side effect for timing
                    .forEach(this::save); // Ensure this method exists and matches signature
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void countTime(AtomicInteger counter, AtomicLong start) {
        if (counter.incrementAndGet() % 10000 == 0) {
            log.info("Imported: {} in {} ms", counter, (System.currentTimeMillis() - start.get()));
            start.set(System.currentTimeMillis());
        }
    }
}
