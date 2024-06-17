package pl.kurs.dictionary.model.command;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateDictionaryCommand {
    private String name;
    private Set<String> initialValues;
}
