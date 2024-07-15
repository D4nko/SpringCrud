package pl.kurs.dictionary.model.command;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateValueForictionaryCommand {
    private int dictionaryId;
    private Set<String> values;
}
