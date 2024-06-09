package pl.kurs.facade;

import org.springframework.stereotype.Component;
import pl.kurs.model.PersonParameter;
import pl.kurs.model.dto.EmployeeDto;
import pl.kurs.model.inheritance.Employee;

import java.util.List;
import java.util.Map;

@Component("employeeFacade")
public class EmployeeFacade implements PersonFacade<Employee, EmployeeDto> {


    @Override
    public Employee createPersonInternal(Map<String, String> parameters) {
        Employee employee = new Employee();
        employee.setAge(Integer.parseInt(parameters.get("age")));
        employee.setSalary(Integer.parseInt(parameters.get("salary")));
        employee.setPosition(parameters.get("position"));
        employee.setName(parameters.get("name"));
        return employee;
    }

    @Override
    public EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(),
                employee.getName(),
                employee.getAge(),
//                employee.getDateOfBirth(),
//                employee.getGender(),
                employee.getPosition(),
                employee.getSalary());
    }
}
