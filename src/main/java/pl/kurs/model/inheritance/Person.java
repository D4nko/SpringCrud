package pl.kurs.model.inheritance;

import jakarta.persistence.*;
import lombok.*;
import pl.kurs.dictionary.model.DictionaryValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personIdGenerator")
    @SequenceGenerator(name = "personIdGenerator", sequenceName = "person_seq", initialValue = 100, allocationSize = 100)
    private int id;
    private String name;
    private int age;
    private LocalDate dateOfBirth;
    private String gender;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private DictionaryValue country;


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, LocalDate dateOfBirth, String gender, DictionaryValue country) {
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
    }
}
