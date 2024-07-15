package pl.kurs.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.dictionary.model.Dictionary;

import java.util.List;
import java.util.Optional;

public interface DictionaryRepository extends JpaRepository<Dictionary, Integer>{

    @Query("SELECT d FROM Dictionary d WHERE d.deleted = false")
    List<Dictionary> findAllActive();

/*
    @Query("SELECT d FROM Dictionary d WHERE d.id = :id AND d.deleted = false")
    Optional<Dictionary> findActiveById(int id);
*/

    @Query("select d from Dictionary d left join fetch d.values where d.id = ?1")
    Optional<Dictionary> findByIdWithValues(int dictionaryId);

    @Modifying
    @Query("update Dictionary d set d.deleted = true where d.id = ?1")
    void deleteById2(int id);
}
