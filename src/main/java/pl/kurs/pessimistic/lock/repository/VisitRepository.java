package pl.kurs.pessimistic.lock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.pessimistic.lock.example.model.Doctor;
import pl.kurs.pessimistic.lock.example.model.Visit;

public interface VisitRepository extends JpaRepository<Visit, Integer> {


}
