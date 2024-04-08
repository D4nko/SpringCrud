package pl.kurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ImportServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ImportService importService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void shouldImportBook() throws Exception {
//        String bookData = "Book Title,Category,1";
//        InputStream inputStream = new ByteArrayInputStream(bookData.getBytes());
//
//        importService.importBook(inputStream);
//
//        verify(jdbcTemplate, times(1)).update(anyString(),
//                eq("Book Title"), eq("Category"), eq(true), eq(1));
//    }
}
