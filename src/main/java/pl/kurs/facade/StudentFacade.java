package pl.kurs.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.dictionary.model.DictionaryValue;
import pl.kurs.dictionary.repository.DictionaryValueRepository;
import pl.kurs.model.dto.EmployeeDto;
import pl.kurs.model.dto.StudentDto;
import pl.kurs.model.inheritance.Employee;
import pl.kurs.model.inheritance.Student;

import java.util.Map;

@Component("studentFacade")
@RequiredArgsConstructor
public class StudentFacade implements PersonFacade<Student, StudentDto> {

    private final DictionaryValueRepository dictionaryValueRepository;

    @Override
    public Student createPersonInternal(Map<String, String> parameters) {
        DictionaryValue countryValue = dictionaryValueRepository.findByDictionaryNameAndValue("COUNTRIES", parameters.get("country")).orElseThrow(() -> new IllegalStateException("MIssing dictionary value in COUNTRIES"));
        Student student = new Student();
        student.setAge(Integer.parseInt(parameters.get("age")));
        student.setScholarship(Integer.parseInt(parameters.get("scholarship")));
        student.setGroup(parameters.get("group"));
        student.setCountry(countryValue);
        student.setName(parameters.get("name"));
        return student;
    }

    @Override
    public StudentDto toDto(Student student) {
        return new StudentDto(student.getId(),
                student.getName(),
                student.getAge(),
                student.getCountry().getValue(),
                student.getScholarship(),
                student.getGroup());
    }
}
