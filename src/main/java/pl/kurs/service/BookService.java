package pl.kurs.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.exceptions.AuthorNotFoundException;
import pl.kurs.exceptions.BookNotFoundException;
import pl.kurs.model.Author;
import pl.kurs.model.Book;
import pl.kurs.model.command.CreateBookCommand;
import pl.kurs.model.command.EditBookCommand;
import pl.kurs.model.query.FindBookQuery;
import pl.kurs.repository.AuthorRepository;
import pl.kurs.repository.BookRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final EntityManager entityManager;


    public Page<Book> findAll(Pageable pageable, FindBookQuery query) {
        return bookRepository.findAll(query.toPredicate(), pageable);
    }

    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

    public Book save(CreateBookCommand command) {
        return bookRepository.save(new Book(command.getTitle(), command.getCategory(), true, authorRepository.findById(command.getAuthorId()).orElseThrow(AuthorNotFoundException::new)));
    }

    @Transactional
    public Book edit(int id, EditBookCommand command) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        Book copy = new Book(book);
        copy.setTitle(command.getTitle());
        copy.setCategory(command.getCategory());
        copy.setAvailable(command.getAvailable());
        copy.setVersion(command.getVersion());
        return bookRepository.saveAndFlush(copy);
    }

    public Book partiallyEdit(int id, EditBookCommand command) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        Optional.ofNullable(command.getTitle()).ifPresent(book::setTitle);
        Optional.ofNullable(command.getCategory()).ifPresent(book::setCategory);
        Optional.ofNullable(command.getAvailable()).ifPresent(book::setAvailable);
        return bookRepository.saveAndFlush(book);
    }



    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    // sprawdzcie sobie na starcie jak to u was idzie i postarajcie poprawic ten wynik
    // 1) 20k-40k na sekunde mniej wiecej
    // 2) zeby zuzycie pamieci sie nie zwiekszylo
    // 3) wszystko albo nic
    // 4) testy jakies


}
