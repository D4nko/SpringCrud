package pl.kurs.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dictionary.model.command.CreateDictionaryCommand;
import pl.kurs.dictionary.model.command.CreateValueForictionaryCommand;
import pl.kurs.dictionary.model.command.EditDictionaryCommand;
import pl.kurs.dictionary.model.dto.DictionaryDto;
import pl.kurs.dictionary.service.DictionaryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dictionaries")
@Slf4j
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping
    public ResponseEntity<List<DictionaryDto>> findAll() {
        log.info("findAll()");
        List<DictionaryDto> dictionaries = dictionaryService.findAll();
        return ResponseEntity.ok(dictionaries);
    }

    @PostMapping
    public ResponseEntity<DictionaryDto> addDictionary(@RequestBody CreateDictionaryCommand command) {
        log.info("addDictionary({})", command);
        return ResponseEntity.status(HttpStatus.CREATED).body(dictionaryService.save(command));
    }

    @PatchMapping("/{id}/values")
    public ResponseEntity<DictionaryDto> addValuesToDictionary(@PathVariable int id, @RequestBody CreateValueForictionaryCommand command) {
        log.info("addValuesToDictionary({}, {})", id, command);
        if(id != command.getDictionaryId()){
            throw new IllegalStateException("Id conflict");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(dictionaryService.addValues(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DictionaryDto> findDictionary(@PathVariable int id) {
        log.info("findDictionary({})", id);
        DictionaryDto dictionary = dictionaryService.findById(id);
        return ResponseEntity.ok(dictionary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DictionaryDto> editDictionary(@PathVariable int id, @RequestBody EditDictionaryCommand command) {
        log.info("editDictionary({}, {})", id, command);
        DictionaryDto dictionary = dictionaryService.edit(id, command);
        return ResponseEntity.status(HttpStatus.OK).body(dictionary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDictionary(@PathVariable int id) {
        log.info("deleteDictionary({})", id);
        dictionaryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/{id}/values/{valueId}")
    public ResponseEntity<Void> deleteValueFromDictionary(@PathVariable int id, @PathVariable int valueId) {
        log.info("deleteValueFromDictionary({}, {})", id, valueId);
        dictionaryService.removeValue(id, valueId);
        return ResponseEntity.ok().build();
    }
}
