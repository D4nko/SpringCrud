package pl.kurs.model.inheritance;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee extends Person{
    private final String type = "EMPLOYEE";
    private String position;
    private int salary;

    public Employee(String name, int age, LocalDate dateOfBirth, String gender, String position, int salary) {
        super(name, age, dateOfBirth, gender);
        this.position = position;
        this.salary = salary;
    }

}

