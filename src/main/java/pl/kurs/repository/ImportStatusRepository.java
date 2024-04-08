package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.model.ImportStatus;

@Repository
public interface ImportStatusRepository extends JpaRepository<ImportStatus, Integer> {
}
