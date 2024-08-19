package pl.kurs.inheritance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.inheritance.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
