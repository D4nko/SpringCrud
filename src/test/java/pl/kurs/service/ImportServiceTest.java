package pl.kurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.kurs.model.ImportStatus;
import pl.kurs.repository.ImportStatusRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ImportServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ImportStatusRepository importStatusRepository;

    @InjectMocks
    private ImportService importService;

    private final int testId = 1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ImportStatus stubStatus = new ImportStatus();
        when(importStatusRepository.findById(testId)).thenReturn(Optional.of(stubStatus));
    }

    @Test
    public void testImportBook_Success(){
        String inputData = "Book1,Category1,1\nBook2,Category2,2";
        InputStream inputStream = new ByteArrayInputStream(inputData.getBytes(StandardCharsets.UTF_8));

        importService.importBook(inputStream, testId);

        verify(jdbcTemplate, times(2)).update(anyString(), any(), any(), anyBoolean(), anyInt());
        verify(importStatusRepository, times(2)).saveAndFlush(any(ImportStatus.class));
    // Tutaj sprawdzamy, czy saveAndFlush jest wywoływany 2 razy, bo wyrzucało org.mockito.exceptions.verification.TooManyActualInvocations:
    // Dzieje się tak, ponieważ podczas wykonywania metody importBook wywoływane są zarówno updateToProcessing, jak i updateToSuccess (lub potencjalnie updateToFail w przypadku błędu).
    }
    @Test
    public void testImportBook_Fail() throws Exception {
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> importService.importBook(inputStream, testId));
        verify(importStatusRepository).saveAndFlush(any(ImportStatus.class));
    }
}