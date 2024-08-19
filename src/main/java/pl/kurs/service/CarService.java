package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.exceptions.CarNotFoundException;
import pl.kurs.model.Car;
import pl.kurs.model.command.CreatCarCommand;
import pl.kurs.model.command.EditCarCommand;
import pl.kurs.model.dto.FullCarDto;
import pl.kurs.repository.CarRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car save(CreatCarCommand command) {
        return carRepository.saveAndFlush(new Car(command.getBrand(), command.getModel(), command.getFuelType()));
    }

    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    @Transactional
    public Car edit(int id, EditCarCommand command) {
        Car car = carRepository.findById(id).orElseThrow(CarNotFoundException::new);
        Car copy = new Car(car);
        copy.setBrand(command.getBrand());
        copy.setModel(command.getModel());
        copy.setFuelType(command.getFuelType());
        copy.setVersion(command.getVersion());
        return carRepository.saveAndFlush(copy);
    }

    public Car partiallyEdit(int id, EditCarCommand command) {
        Car car = carRepository.findById(id).orElseThrow(CarNotFoundException::new);
        Optional.ofNullable(command.getBrand()).ifPresent(car::setBrand);
        Optional.ofNullable(command.getFuelType()).ifPresent(car::setFuelType);
        Optional.ofNullable(command.getModel()).ifPresent(car::setModel);
        return carRepository.saveAndFlush(car);
    }

    @Transactional(readOnly = true)
    public Page<FullCarDto> getCarsByGarageId(int garageId, Pageable pageable) {
        return carRepository.findCarsByGarageId(garageId, pageable)
                .map(FullCarDto::from);
    }
}
