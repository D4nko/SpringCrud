package pl.kurs.model.inheritance.facade;

import pl.kurs.model.PersonParameter;
import pl.kurs.model.dto.PersonDto;
import pl.kurs.model.inheritance.Person;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PersonFacade <ENTITY extends Person, DTO extends PersonDto> {
    default  ENTITY createPerson(List<PersonParameter> parameters){
        return createPersonInternal(parameters.stream().collect(Collectors.toMap(PersonParameter::getName, PersonParameter::getValue)));
    }
    ENTITY createPersonInternal(Map<String, String> parameters);
    DTO toDto(ENTITY person);
}
