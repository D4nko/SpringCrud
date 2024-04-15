package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.exceptions.BookNotFoundException;
import pl.kurs.model.Author;
import pl.kurs.model.Book;
import pl.kurs.model.command.CreateBookCommand;
import pl.kurs.model.command.EditBookCommand;
import pl.kurs.repository.AuthorRepository;
import pl.kurs.repository.BookRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;


    @Test
    void shouldFindBookById() {
        int bookId = 1;
        Author author = new Author("Kazimierz", "Wielki", 1900, 2000); // Assuming a constructor exists
        Book expectedBook = new Book("Ogniem i Mieczem", "Historical", true, author); // Assuming a constructor exists
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        Optional<Book> actualBookOptional = bookService.findBookById(bookId);

        assertTrue(actualBookOptional.isPresent(), "Book should be found");
        Book actualBook = actualBookOptional.get();
        assertEquals(expectedBook, actualBook, "The found book should match the expected book");
        verify(bookRepository).findById(bookId);
    }


    @Test
    void shouldPartiallyEditBook() {
        int bookId = 1;
        EditBookCommand command = new EditBookCommand();
        command.setTitle("Updated Title");
        Book book = new Book("Old Title", "Old Category", true, new Author());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book partiallyEditedBook = bookService.partiallyEdit(bookId, command);

        assertEquals(command.getTitle(), partiallyEditedBook.getTitle());
        assertEquals(book.getCategory(), partiallyEditedBook.getCategory());
        assertEquals(book.isAvailable(), partiallyEditedBook.isAvailable());
    }
    @Test
    void shouldSaveBook() {
        CreateBookCommand command = new CreateBookCommand("Title", "Category",  1);
        Author author = new Author("Kazimierz", "Wielki", 1900, 2000);
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Book savedBook = bookService.save(command);

        assertEquals(command.getTitle(), savedBook.getTitle());
        assertEquals(command.getCategory(), savedBook.getCategory());
        assertEquals(author, savedBook.getAuthor());
    }



    @Test
    void shouldDeleteBook() {
        int bookId = 1;

        assertDoesNotThrow(() -> bookService.deleteById(bookId));

        verify(bookRepository).deleteById(bookId);

    }



}
