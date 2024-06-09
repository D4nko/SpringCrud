package pl.kurs.model.inheritance;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person {

;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personIdGenerator")
    @SequenceGenerator(name = "personIdGenerator", sequenceName = "person_seq", initialValue = 100, allocationSize = 100)
    private int id;
    private String name;
    private int age;
    @Column(unique = true)
    private String pesel;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, String pesel) {
        this.name = name;
        this.age = age;
        this.pesel = pesel;
    }

}
