package pl.kurs.inheritance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.kurs.inheritance.service.PersonService;
import pl.kurs.inheritance.model.command.CreatePersonCommand;
import pl.kurs.inheritance.model.dto.PersonDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<PersonDto> findAll(){
        return personService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto addPerson(@RequestBody @Valid CreatePersonCommand command){
        return personService.createPerson(command);
    }
}
