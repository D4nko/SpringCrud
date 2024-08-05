package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.exceptions.CarNotFoundException;
import pl.kurs.model.Car;
import pl.kurs.model.command.CreatCarCommand;
import pl.kurs.model.command.EditCarCommand;
import pl.kurs.model.dto.CarDto;
import pl.kurs.repository.CarRepository;
import pl.kurs.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@Slf4j
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carService;
    private final CarService cs;

    @GetMapping
    public ResponseEntity<List<CarDto>> findAll() {
        log.info("findAll");
        return ResponseEntity.ok(carService.findAll().stream().map(CarDto::from).toList());
    }

    @PostMapping
    public ResponseEntity<CarDto> addCar(@RequestBody CreatCarCommand command) {
        Car car = carService.saveAndFlush(new Car(command.getBrand(), command.getModel(), command.getFuelType()));
        return ResponseEntity.status(HttpStatus.CREATED).body(CarDto.from(car));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findCar(@PathVariable int id) {
        return ResponseEntity.ok(CarDto.from(carService.findById(id).orElseThrow(CarNotFoundException::new)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable int id) {
        carService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> editCar(@PathVariable int id, @RequestBody EditCarCommand command) {
        Car car = cs.edit(id, command);
        return ResponseEntity.status(HttpStatus.OK).body(CarDto.from(car));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarDto> editCarPartially(@PathVariable int id, @RequestBody EditCarCommand command) {
        Car car = cs.partiallyEdit(id, command);
        return ResponseEntity.status(HttpStatus.OK).body(CarDto.from(car));
    }
}
