package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.model.inheritance.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
