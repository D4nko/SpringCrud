package pl.kurs.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.dictionary.model.Dictionary;
import pl.kurs.dictionary.model.DictionaryValue;

import java.util.Optional;

public interface DictionaryValueRepository extends JpaRepository<DictionaryValue, Integer>{
    Optional<DictionaryValue> findByDictionaryNameAndValue(String dictionaryName, String value);

    @Modifying
    @Query("update DictionaryValue dv set dv.deleted=true where dv.dictionary.id = ?1")
    void deleteByDictionary_id(int id);
}
