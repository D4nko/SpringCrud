package pl.kurs.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.dictionary.model.Dictionary;
import pl.kurs.dictionary.model.DictionaryValue;

import java.util.Optional;

public interface DictionaryValueRepository extends JpaRepository<DictionaryValue, Integer>{
    Optional<DictionaryValue> findByDictionaryNameAndValue(String dictionaryName, String value);
}
