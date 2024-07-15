package pl.kurs.dictionary.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@Where(clause = "deleted = false")
@SQLDelete(sql = "update dictionary set deleted = true where id = ?1")
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dictionaryIdGenerator")
    @SequenceGenerator(name = "dictionaryIdGenerator", sequenceName = "dictionary_seq", initialValue = 1000, allocationSize = 100)
    private int id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "dictionary", cascade = {CascadeType.PERSIST})
    private Set<DictionaryValue> values = new HashSet<>();
    private boolean deleted = false;

    public Dictionary(String name) {
        this.name = name;
    }

    public Dictionary(String name, Set<String> values) {
        this.name = name;
        this.values = values.stream().map(v -> new DictionaryValue(v, this)).collect(Collectors.toSet());
    }


    public void addNewValues(Set<String> values) {
        this.values = values.stream().map(v -> new DictionaryValue(v, this)).collect(Collectors.toSet());
    }
}
