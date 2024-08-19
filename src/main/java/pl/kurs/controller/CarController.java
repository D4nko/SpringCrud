package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.exceptions.CarNotFoundException;
import pl.kurs.model.Car;
import pl.kurs.model.command.CreatCarCommand;
import pl.kurs.model.command.EditCarCommand;
import pl.kurs.model.dto.CarDto;
import pl.kurs.model.dto.FullCarDto;
import pl.kurs.repository.CarRepository;
import pl.kurs.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@Slf4j
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDto>> findAll() {
        log.info("findAll");
        return ResponseEntity.ok(carRepository.findAll().stream().map(CarDto::from).toList());
    }

    @PostMapping
    public ResponseEntity<CarDto> addCar(@RequestBody CreatCarCommand command) {
        Car car = carRepository.saveAndFlush(new Car(command.getBrand(), command.getModel(), command.getFuelType()));
        return ResponseEntity.status(HttpStatus.CREATED).body(CarDto.from(car));
    }
    @GetMapping("/garage/{garageId}")
    public ResponseEntity<Page<FullCarDto>> getCarsByGarage(@PathVariable int garageId, Pageable pageable) {
        return ResponseEntity.ok(carService.getCarsByGarageId(garageId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullCarDto> findCar(@PathVariable int id) {
        return ResponseEntity.ok(FullCarDto.from(carRepository.findById(id).orElseThrow(CarNotFoundException::new)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable int id) {
        carRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> editCar(@PathVariable int id, @RequestBody EditCarCommand command) {
        Car car = carService.edit(id, command);
        return ResponseEntity.status(HttpStatus.OK).body(CarDto.from(car));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarDto> editCarPartially(@PathVariable int id, @RequestBody EditCarCommand command) {
        Car car = carService.partiallyEdit(id, command);
        return ResponseEntity.status(HttpStatus.OK).body(CarDto.from(car));
    }
}
