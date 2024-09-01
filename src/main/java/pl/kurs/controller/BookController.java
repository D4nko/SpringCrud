package pl.kurs.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.exceptions.BookNotFoundException;
import pl.kurs.model.Author;
import pl.kurs.model.Book;
import pl.kurs.model.command.CreateBookCommand;
import pl.kurs.model.command.EditBookCommand;
import pl.kurs.model.dto.BookDto;
import pl.kurs.model.query.FindBookQuery;
import pl.kurs.repository.AuthorRepository;
import pl.kurs.repository.BookRepository;
import pl.kurs.service.BookService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookDto>> findAll(@PageableDefault Pageable pageable, FindBookQuery query) {
        log.info("findAll({}, {})", pageable, query);
        return ResponseEntity.ok(bookService.findAll(pageable, query).map(BookDto::from));
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody CreateBookCommand command) {
        Book book = bookService.save(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookDto.from(book));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get book with id")
    public ResponseEntity<BookDto> findBook(@PathVariable int id) {
        return ResponseEntity.ok(BookDto.from(bookService.findById(id).orElseThrow(BookNotFoundException::new)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> editBook(@PathVariable int id, @RequestBody EditBookCommand command) {
        Book book = bookService.edit(id, command);
        return ResponseEntity.status(HttpStatus.OK).body(BookDto.from(book));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDto> editBookPartially(@PathVariable int id, @RequestBody EditBookCommand command) {
        Book book = bookService.partiallyEdit(id, command);
        return ResponseEntity.status(HttpStatus.OK).body(BookDto.from(book));
    }


}
