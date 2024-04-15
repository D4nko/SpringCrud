package pl.kurs.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.model.inheritance.Employee;
import pl.kurs.model.inheritance.Person;
import pl.kurs.model.inheritance.Student;
import pl.kurs.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;


    @PostConstruct
    public void init(){
        personRepository.saveAllAndFlush(
                List.of(
                        new Employee("A", 30, "programmer", 30000),
                        new Employee("B", 25, "programmer", 25000),
                        new Employee("V", 35, "programmer", 14000),
                        new Student("X", 20, 1000, "1a"),
                        new Student("T", 19, 500, "1a"),
                        new Student("Z", 20, 0, "1b")
                )
        );
    }


    @GetMapping
    public List<Person> findAll(){
        return personRepository.findAll();
    }
}
