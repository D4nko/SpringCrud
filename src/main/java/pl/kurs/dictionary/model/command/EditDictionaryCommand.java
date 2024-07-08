package pl.kurs.dictionary.model.command;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EditDictionaryCommand {
    private String name;
    private Set<String> values;
}
