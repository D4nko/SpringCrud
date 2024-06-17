package pl.kurs.dictionary.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dictionary.model.command.CreateDictionaryCommand;
import pl.kurs.dictionary.model.dto.DictionaryDto;
import pl.kurs.dictionary.repository.DictionaryRepository;

@RestController
@RequestMapping("/api/v1/dictionaries")
@RequiredArgsConstructor
public class DictionaryController {


    private final DictionaryRepository dictionaryRepository;

    @PostMapping
    public ResponseEntity<DictionaryDto> saveDictionary(@RequestBody CreateDictionaryCommand command){

        return null;
    }

    @PostMapping()
    public ResponseEntity<DictionaryDto> saveValuesForDictionary(@RequestBody CreateDictionaryCommand command){

        return null;
    }

    @PutMapping()
    public ResponseEntity<DictionaryDto> editValueForDictionary(@RequestBody CreateDictionaryCommand command){

        return null;
    }
    @GetMapping()
    public ResponseEntity<DictionaryDto> getDictionary(@PathVariable int id){

        return null;
    }

    @DeleteMapping()
    public ResponseEntity<DictionaryDto> deleteDictionary(@PathVariable int id){

        return null;
    }

    @DeleteMapping()
    public ResponseEntity<DictionaryDto> deleteValueFromDictionary(@PathVariable int id){

        return null;
    }




}
