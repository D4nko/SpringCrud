package pl.kurs.model.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Student extends Person{
    private final String type = "STUDENT";
    private int scholarship;
    @Column(name = "std_group")
    private String group;

    public Student(String name, int age, Date dateOfBirth, String gender, int scholarship, String group) {
        super(name, age, dateOfBirth, gender);
        this.scholarship = scholarship;
        this.group = group;
    }
}
