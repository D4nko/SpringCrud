package pl.kurs.dictionary.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"value", "dictionary"})
@Where(clause = "deleted = false")
//@SQLDelete(sql = "update dictionary_value set deleted = true where id = ?1")
public class DictionaryValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dictionaryValueIdGenerator")
    @SequenceGenerator(name = "dictionaryValueIdGenerator", sequenceName = "dictionary_value_seq", initialValue = 10000, allocationSize = 100)
    private int id;
    @Column(name = "dict_value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;
    private boolean deleted;

    public DictionaryValue(String value, Dictionary dictionary) {
        this.value = value;
        this.dictionary = dictionary;
        dictionary.getValues().add(this);
    }
}
