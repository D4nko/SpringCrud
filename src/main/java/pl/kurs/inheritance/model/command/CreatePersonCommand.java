package pl.kurs.inheritance.model.command;

import lombok.Getter;
import lombok.Setter;
import pl.kurs.model.PersonParameter;
import pl.kurs.validation.annotation.CheckEntityType;
import pl.kurs.validation.annotation.RequiredAttributes;

import java.util.List;

@Getter
@Setter
@RequiredAttributes
public class CreatePersonCommand {
    @CheckEntityType
    private String classType;
    private List<PersonParameter> parameters;
}
