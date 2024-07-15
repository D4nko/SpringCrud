package pl.kurs.dictionary.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CreateValueForDictionaryCommand {
    private int dictionaryId;
    private Set<String> values;
}
