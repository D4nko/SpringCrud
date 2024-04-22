package pl.kurs.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kurs.model.command.CreatePersonCommand;
import pl.kurs.model.dto.PersonDto;
import pl.kurs.model.inheritance.Employee;
import pl.kurs.model.inheritance.Person;
import pl.kurs.model.inheritance.Student;
import pl.kurs.repository.PersonRepository;
import pl.kurs.service.PersonService;

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
    public PersonDto addPerson(@RequestBody CreatePersonCommand command){
        return personService.createPerson(command);
    }
}
