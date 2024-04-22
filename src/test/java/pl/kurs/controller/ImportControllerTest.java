package pl.kurs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.service.ImportService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ImportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ImportService importService;


    @Test
    void shouldImportBooks() throws Exception {
        MockMultipartFile file = new MockMultipartFile("books", "books.csv", "text/plain", "Book1, Book2".getBytes());

        mockMvc.perform(multipart("/api/v1/import/books").file(file).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.fileName").value("books"));
    }


}
