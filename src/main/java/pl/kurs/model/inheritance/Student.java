package pl.kurs.model.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Student extends Person{
    private final String type = "STUDENT";
    private int scholarship;
    @Column(name = "std_group")
    private String group;

    public Student(String name, int age, String pesel, int scholarship, String group) {
        super(name, age, pesel);
        this.scholarship = scholarship;
        this.group = group;
    }
}
