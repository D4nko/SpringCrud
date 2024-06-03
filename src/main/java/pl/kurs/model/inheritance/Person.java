package pl.kurs.model.inheritance;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String country;


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
