package pl.kurs.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeDto extends PersonDto {

    private String position;
    private int salary;


    public EmployeeDto(int id, String name, int age, String position, int salary) {
        super(id, name, age);
        this.position = position;
        this.salary = salary;
    }
}
