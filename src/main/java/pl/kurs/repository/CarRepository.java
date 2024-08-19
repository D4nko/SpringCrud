package pl.kurs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kurs.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {

    @Query("SELECT c FROM Car c")
    Page<Car> findCarsByGarageId(int garageId, Pageable pageable);


}
