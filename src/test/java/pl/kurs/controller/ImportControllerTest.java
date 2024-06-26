package pl.kurs.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.model.ImportStatus;
import pl.kurs.service.ImportService;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

@WebMvcTest(ImportController.class)
public class ImportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportService importService;


    @InjectMocks
    private ImportController importController;




    @Test
    void testImportBooks() throws IOException {
        MockMultipartFile file = new MockMultipartFile("books", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Sample text".getBytes());
        ImportStatus importStatus = new ImportStatus();
        importStatus.setId(1);

        when(importService.startImport("test.txt")).thenReturn(importStatus);

        ResponseEntity<ImportStatus> responseEntity = importController.importBooks(file);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(importStatus, responseEntity.getBody());

        verify(importService, times(1)).importBook(any(InputStream.class), eq(importStatus.getId()));
    }






//    @Test
//    void shouldImportBooks() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("books", "books.csv", "text/csv", "content".getBytes());
//
//        mockMvc.perform(multipart("/api/v1/import/books").file(file))
//                .andExpect(status().isOk());
//
//        verify(importService, times(1)).importBook(any());
//    }
}
