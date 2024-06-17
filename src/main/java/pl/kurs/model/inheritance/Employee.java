package pl.kurs.model.inheritance;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.dictionary.model.DictionaryValue;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee extends Person{
    private final String type = "EMPLOYEE";
    @ManyToOne
    @JoinColumn(name = "position_id")
    private DictionaryValue position;
    private int salary;


}
