package pl.kurs.facade;

import org.springframework.stereotype.Component;
import pl.kurs.model.dto.EmployeeDto;
import pl.kurs.model.dto.StudentDto;
import pl.kurs.model.inheritance.Employee;
import pl.kurs.model.inheritance.Student;

import java.util.Map;

@Component("studentFacade")
public class StudentFacade implements PersonFacade<Student, StudentDto> {


    @Override
    public Student createPersonInternal(Map<String, String> parameters) {
        Student student = new Student();
        student.setAge(Integer.parseInt(parameters.get("age")));
        student.setScholarship(Integer.parseInt(parameters.get("scholarship")));
        student.setGroup(parameters.get("group"));
        student.setName(parameters.get("name"));
        return student;
    }

    @Override
    public StudentDto toDto(Student student) {
        return new StudentDto(student.getId(),
                student.getName(),
                student.getAge(),
//                student.getDateOfBirth(),
//                student.getGender(),
                student.getScholarship(),
                student.getGroup());
    }
}
