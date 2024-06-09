package pl.kurs.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentDto extends PersonDto{

    private int scholarship;
    private String group;


    public StudentDto(int id, String name, int age, int scholarship, String group) {
        super(id, name, age);
        this.scholarship = scholarship;
        this.group = group;
    }
}
