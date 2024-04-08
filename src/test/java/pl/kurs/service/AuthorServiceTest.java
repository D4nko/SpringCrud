package pl.kurs.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kurs.exceptions.AuthorNotFoundException;
import pl.kurs.model.Author;
import pl.kurs.model.Book;
import pl.kurs.model.command.CreateAuthorCommand;
import pl.kurs.model.command.EditAuthorCommand;
import pl.kurs.repository.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllTest() {
        Author author1 = new Author("Adam", "Mickiewicz", 1798, 1855);
        Author author2 = new Author("Henryk", "Sienkiewicz", 1846, 1916);
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<Author> authors = authorService.findAll();

        assertEquals(2, authors.size(), "Expected to find 2 authors");
        verify(authorRepository).findAll();
    }

    @Test
    void shouldFindByIdWhenAuthorExists() {
        int authorId = 1;
        Author expectedAuthor = new Author("Adam", "Mickiewicz", 1798, 1855);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthor));

        Author actualAuthor = authorService.findById(authorId);

        assertEquals(expectedAuthor, actualAuthor);
        verify(authorRepository).findById(authorId);
    }

    @Test
    void shouldFindByIdWhenAuthorDoesNotExistThenThrowException() {
        int authorId = 99;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.findById(authorId));
        verify(authorRepository).findById(authorId);
    }
    @Test
    void shouldSaveAuthor() {
        CreateAuthorCommand command = new CreateAuthorCommand("Leo", "Tolstoy", 1828, 1910);
        Author expectedAuthor = new Author("Leo", "Tolstoy", 1828, 1910);
        when(authorRepository.saveAndFlush(any())).thenReturn(expectedAuthor);

        Author savedAuthor = authorService.save(command);

        assertEquals(expectedAuthor, savedAuthor);
        verify(authorRepository).saveAndFlush(any());
    }

    @Test
    void shouldPartiallyEditAuthor() {
        int authorId = 1;
        EditAuthorCommand command = new EditAuthorCommand();
        command.setLastName("Sienkiewicz");
        Author existingAuthor = new Author("Adam", "Mickiewicz", 1798, 1855);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Author partiallyEditedAuthor = authorService.partiallyEdit(authorId, command);

        assertEquals("Adam", partiallyEditedAuthor.getName());
        assertEquals("Sienkiewicz", partiallyEditedAuthor.getSurname());
        assertEquals(1798, partiallyEditedAuthor.getBirthYear());
        assertEquals(1855, partiallyEditedAuthor.getDeathYear());
        verify(authorRepository).findById(authorId);
        verify(authorRepository).saveAndFlush(any());
    }


}

