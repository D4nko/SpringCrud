package pl.kurs.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.kurs.dictionary.model.Dictionary;
import pl.kurs.dictionary.model.DictionaryValue;
import pl.kurs.dictionary.repository.DictionaryRepository;
import pl.kurs.model.Author;
import pl.kurs.model.Book;
import pl.kurs.model.Car;
import pl.kurs.model.Garage;
import pl.kurs.inheritance.repository.PersonRepository;
import pl.kurs.repository.*;

import java.util.List;

@Service
@Profile("no-liquibase")
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final DictionaryRepository dictionaryRepository;

    @PostConstruct
    public void init() {
        Author a1 = authorRepository.saveAndFlush(new Author("Kazimierz", "Wileki", 1900, 2000));
        Author a2 = authorRepository.saveAndFlush(new Author("Janina", "Kowalska", 1900, 2000));

        bookRepository.saveAndFlush(new Book("W pustyni i w puszczy", "Powiesc", true, a1));
        bookRepository.saveAndFlush(new Book("Ogniem i mieczem", "Powiesc", true, a1));
        bookRepository.saveAndFlush(new Book("Ogniem i mieczem", "Powiesc", true, a1));
        bookRepository.saveAndFlush(new Book("Potop1", "Powiesc", true, a2));
        bookRepository.saveAndFlush(new Book("Potop2", "Powiesc", true, a2));
        bookRepository.saveAndFlush(new Book("Potop3", "Powiesc", true, a2));

        carRepository.saveAndFlush(new Car("BMW", "M2", "PB"));
        carRepository.saveAndFlush(new Car("Ferarri", "F8", "PB"));


        authorRepository.saveAndFlush(new Author("Adam", "Mickiewicz", 1798, 1855));
        authorRepository.saveAndFlush(new Author("Henryk", "Sienkiewicz", 1846, 1916));

        garageRepository.saveAndFlush(new Garage(50, "ul. Kolejowa 23, Warszawa", true));
        garageRepository.saveAndFlush(new Garage(10, "ul. Tekturowa 3, Warszawa", false));

//            personRepository.saveAllAndFlush(
//                List.of(
//                        new Employee("A", 30,"172836871263","programmer", 30000),
//                        new Employee("B", 25,"272836871263","programmer", 25000),
//                        new Employee("V", 35,"372836871263","programmer", 14000),
//                        new Student("X", 20,"472836871263", 1000, "1a"),
//                        new Student("T", 19,"572836871263", 500, "1a"),
//                        new Student("Z", 20,"672836871263", 0, "1b")
//                )
//        );

        // todo mozna nulle na razie wpisac jako value

        log.info("Add temporary dictionaries");
        Dictionary countries = new Dictionary("COUNTRIES");
        Dictionary positions = new Dictionary("JOB_POSITION");

        new DictionaryValue("Polska", countries);
        new DictionaryValue("Niemcy", countries);
        new DictionaryValue("Wielka Brytania", countries);
        new DictionaryValue("Junior Developer", positions);
        new DictionaryValue("Mid Developer", positions);
        new DictionaryValue("Senior Developer", positions);

        dictionaryRepository.saveAllAndFlush(List.of(countries, positions));
        log.info("End adding temporary dictionaries");

    }

}
