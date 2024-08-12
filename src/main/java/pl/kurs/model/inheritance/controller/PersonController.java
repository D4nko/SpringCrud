package pl.kurs.model.inheritance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kurs.model.command.CreatePersonCommand;
import pl.kurs.model.dto.PersonDto;
import pl.kurs.model.inheritance.service.PersonService;

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
