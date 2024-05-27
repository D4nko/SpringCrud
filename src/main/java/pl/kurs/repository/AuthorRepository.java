package pl.kurs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kurs.model.Author;
import pl.kurs.model.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("select a from Author a left join fetch a.books")
    List<Author> findAllWithBooks();

    @Query(value = "select new pl.kurs.model.dto.AuthorDto(a.id, a.name, a.surname, a.birthYear, a.deathYear, (select count(b) from Book b where b.author.id = a.id)) from Author a",
    countQuery = "select count(a) from Author a")
    Page<AuthorDto> findAllWithBooks(Pageable pageable);
}
