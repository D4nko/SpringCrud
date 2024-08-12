package pl.kurs.model.inheritance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.model.inheritance.Employee;
import pl.kurs.model.inheritance.facade.PersonFacade;
import pl.kurs.model.command.CreatePersonCommand;
import pl.kurs.model.dto.PersonDto;
import pl.kurs.model.inheritance.Person;
import pl.kurs.model.inheritance.repository.PersonRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final Map<String, PersonFacade> facades;

//    @PostConstruct
//    public void init(){
//        personRepository.saveAllAndFlush(
//                List.of(
//                        new Employee("A", 30,"172836871263","programmer", 30000),
//                        new Employee("B", 25,"272836871263","programmer", 25000),
//                        new Employee("V", 35,"372836871263","programmer", 14000),
//                        new Student("X", 20,"472836871263", 1000, "1a"),
//                        new Student("T", 19,"572836871263", 500, "1a"),
//                        new Student("Z", 20,"672836871263", 0, "1b")
//                )
//        );
//    }

    public List<PersonDto> findAll() {
        return personRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Transactional
    public PersonDto createPerson(CreatePersonCommand command) {
        PersonFacade facade = facades.get(command.getClassType().toLowerCase() + "Facade");
        Person person = personRepository.saveAndFlush(facade.createPerson(command.getParameters()));
        return facade.toDto(person);
    }

    private PersonDto mapToDto(Person person){
        PersonFacade facade = facades.get(person.getClass().getSimpleName().toLowerCase() + "Facade");
        return facade.toDto(person);
    }
}
