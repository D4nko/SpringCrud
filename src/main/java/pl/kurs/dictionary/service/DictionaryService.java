package pl.kurs.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.dictionary.model.Dictionary;
import pl.kurs.dictionary.model.DictionaryValue;
import pl.kurs.dictionary.model.command.CreateDictionaryCommand;
import pl.kurs.dictionary.model.command.CreateValueForDictionaryCommand;
import pl.kurs.dictionary.model.command.EditDictionaryCommand;
import pl.kurs.dictionary.model.dto.DictionaryDto;
import pl.kurs.dictionary.repository.DictionaryRepository;
import pl.kurs.dictionary.repository.DictionaryValueRepository;
import pl.kurs.exceptions.DictionaryNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryValueRepository dictionaryValueRepository;

    @Transactional(readOnly = true)
    public List<DictionaryDto> findAll() {
        return dictionaryRepository.findAllActive()
                .stream()
                .map(DictionaryDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public DictionaryDto save(CreateDictionaryCommand command) {
        Dictionary toSave = dictionaryRepository.save(new Dictionary(command.getName(), command.getInitialValues()));
        return DictionaryDto.from(toSave);
    }

    @Transactional(readOnly = true)
    public DictionaryDto findById(int id) {
        Dictionary dictionary = dictionaryRepository.findByIdWithValues(id)
                .orElseThrow(() -> new RuntimeException("Dictionary not found"));
        return DictionaryDto.from(dictionary);
    }

    // zmiana endpointa, ustawic edycje dla pojedynczego value w slowniku, edytowanie pojedynczego dictionary_value


    // poprawic endpoint, zeby nie dodawało nowych dictionary value, tylko zamieniało stare na nowe
    @Transactional
    public DictionaryDto edit(int id, EditDictionaryCommand command) {
        Dictionary dictionary = dictionaryRepository.findByIdWithValues(id)
                .orElseThrow(() -> new RuntimeException("Dictionary not found"));
        dictionary.setName(command.getName());
        dictionary.setValues(command.getValues().stream().map(value -> new DictionaryValue(value, dictionary)).collect(Collectors.toSet()));
        Dictionary updatedDictionary = dictionaryRepository.save(dictionary);
        return DictionaryDto.from(updatedDictionary);
    }

    @Transactional
    public DictionaryDto addValues(CreateValueForDictionaryCommand command) {
        Dictionary dictionary = dictionaryRepository.findByIdWithValues(command.getDictionaryId())
                .orElseThrow(DictionaryNotFoundException::new);
        dictionary.addNewValues(command.getValues());
        Dictionary updatedDictionary = dictionaryRepository.save(dictionary);
        return DictionaryDto.from(updatedDictionary);
    }

    @Transactional
    public void deleteById(int id) {
        dictionaryValueRepository.deleteByDictionary_id(id);
        dictionaryRepository.deleteById2(id);
    }

    @Transactional
    public void removeValueFromDictionary(int id, int valueId) {
        dictionaryValueRepository.deleteById(valueId);
    }
}
