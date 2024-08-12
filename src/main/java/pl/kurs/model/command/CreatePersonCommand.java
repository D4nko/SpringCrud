package pl.kurs.model.command;

import lombok.Getter;
import lombok.Setter;
import pl.kurs.model.PersonParameter;
import pl.kurs.validation.annotation.CheckEntityType;

import java.util.List;

@Getter
@Setter
public class CreatePersonCommand {
    @CheckEntityType
    private String classType;
    private List<PersonParameter> parameters;
    private String pesel;
}
