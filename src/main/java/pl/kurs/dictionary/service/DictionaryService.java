package pl.kurs.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.dictionary.model.Dictionary;
import pl.kurs.dictionary.model.DictionaryValue;
import pl.kurs.dictionary.model.command.CreateDictionaryCommand;
import pl.kurs.dictionary.model.command.CreateValueForictionaryCommand;
import pl.kurs.dictionary.model.command.EditDictionaryCommand;
import pl.kurs.dictionary.model.dto.DictionaryDto;
import pl.kurs.dictionary.repository.DictionaryRepository;
import pl.kurs.dictionary.repository.DictionaryValueRepository;

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

    @Transactional
    public void deleteById(int id) {
        Dictionary dictionary = dictionaryRepository.findByIdWithValues(id)
                .orElseThrow(() -> new RuntimeException("Dictionary not found"));
        dictionary.setDeleted(true);
        dictionaryRepository.save(dictionary);
    }

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
    public DictionaryDto addValues(CreateValueForictionaryCommand command) {
        Dictionary dictionary = dictionaryRepository.findByIdWithValues(command.getDictionaryId())
                .orElseThrow(() -> new RuntimeException("Dictionary not found"));
        dictionary.addNewValues(command.getValues());
        Dictionary updatedDictionary = dictionaryRepository.save(dictionary);
        return DictionaryDto.from(updatedDictionary);
    }

    @Transactional
    public void removeValue(int id, int valueId) {
        Dictionary dictionary = dictionaryRepository.findByIdWithValues(id)
                .orElseThrow(() -> new RuntimeException("Dictionary not found"));
        DictionaryValue value = dictionaryValueRepository.findById(valueId)
                .orElseThrow(() -> new RuntimeException("Value not found"));
        dictionary.getValues().remove(value);
        dictionaryValueRepository.delete(value);
        dictionaryRepository.save(dictionary);
    }
}
