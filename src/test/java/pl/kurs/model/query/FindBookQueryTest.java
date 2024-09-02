package pl.kurs.model.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.Main;
import pl.kurs.model.Author;
import pl.kurs.model.Book;
import pl.kurs.repository.AuthorRepository;
import pl.kurs.repository.BookRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FindBookQueryTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {

        Author author = new Author("Sample", "Autor", 1990, 2022);
        authorRepository.save(author);

        Book book1 = new Book("Sample Book", "Sample", true, author);
        bookRepository.save(book1);

        Book book2 = new Book("Another Book", "Sample but not so smaple", true, author);
        bookRepository.save(book2);
    }

    @Test
    public void shouldReturnBooksByAuthor() throws Exception {
        FindBookQuery query = new FindBookQuery();
        query.setAuthor("Sample Autor");

        String json = objectMapper.writeValueAsString(query);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content[0].authorId").value(1));
    }


}