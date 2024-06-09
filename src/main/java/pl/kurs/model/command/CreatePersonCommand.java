package pl.kurs.model.command;

import lombok.Getter;
import lombok.Setter;
import pl.kurs.model.PersonParameter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreatePersonCommand {
    private String classType;
    private List<PersonParameter> parameters;
    private String pesel;
}
