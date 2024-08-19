package pl.kurs.inheritance.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.dictionary.model.DictionaryValue;
import pl.kurs.dictionary.repository.DictionaryValueRepository;
import pl.kurs.inheritance.model.Employee;
import pl.kurs.inheritance.model.dto.EmployeeDto;

import java.util.Map;

@Component("employeeFacade")
@RequiredArgsConstructor
public class EmployeeFacade implements PersonFacade<Employee, EmployeeDto> {

    private final DictionaryValueRepository dictionaryValueRepository;


    @Override
    public Employee createPersonInternal(Map<String, String> parameters) {
        DictionaryValue countryValue = dictionaryValueRepository.findByDictionaryNameAndValue("COUNTRIES", parameters.get("country")).orElseThrow(() -> new IllegalStateException("MIssing dictionary value in COUNTRIES"));
        DictionaryValue positionValue = dictionaryValueRepository.findByDictionaryNameAndValue("JOB_POSITION", parameters.get("position")).orElseThrow(() -> new IllegalStateException("MIssing dictionary value in JOB_POSITION"));
        Employee employee = new Employee();
        employee.setAge(Integer.parseInt(parameters.get("age")));
        employee.setSalary(Integer.parseInt(parameters.get("salary")));
        employee.setPosition(positionValue);
        employee.setName(parameters.get("name"));
        employee.setCountry(countryValue);
        return employee;
    }

    @Override
    public EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(),
                employee.getName(),
                employee.getAge(),
                employee.getCountry().getValue(),
                employee.getPosition().getValue(),
                employee.getSalary());
    }
}
