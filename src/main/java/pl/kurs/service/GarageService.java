package pl.kurs.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.exceptions.CarNotFoundException;
import pl.kurs.exceptions.GarageNotFoundException;
import pl.kurs.model.Car;
import pl.kurs.model.Garage;
import pl.kurs.model.command.CreateGarageCommand;
import pl.kurs.model.command.EditGarageCommand;
import pl.kurs.repository.CarRepository;
import pl.kurs.repository.GarageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GarageService {

    private final GarageRepository garageRepository;
    private final CarRepository carRepository;


    @PostConstruct
    public void init() {
        garageRepository.saveAndFlush(new Garage(50, "ul. Kolejowa 23, Warszawa", true));
        garageRepository.saveAndFlush(new Garage(10, "ul. Tekturowa 3, Warszawa", false));
    }


    @Transactional(readOnly = true)
    public List<Garage> findAll() {
        return garageRepository.findAllWithCars();
    }

    public Garage save(CreateGarageCommand command) {
        return garageRepository.saveAndFlush(new Garage(command.getPlaces(), command.getAddress(), true));
    }

    @Transactional(readOnly = true)
    public Optional<Garage> findById(int id) {
        return garageRepository.findByIdWithCars(id);
    }

    public void deleteById(int id) {
        garageRepository.deleteById(id);
    }


    public Garage edit(int id, EditGarageCommand command) {
        Garage garage = garageRepository.findByIdWithCars(id).orElseThrow(GarageNotFoundException::new);
        garage.setLpgAllowed(command.getIsLpgAllowed());
        garage.setPlaces(command.getPlaces());
        garage.setAddress(command.getAddress());
        return garageRepository.saveAndFlush(garage);
    }

    @Transactional
    public Garage partiallyEdit(int id, EditGarageCommand command) {
        Garage garage = garageRepository.findByIdWithCars(id).orElseThrow(GarageNotFoundException::new);
        Optional.ofNullable(command.getAddress()).ifPresent(garage::setAddress);
        Optional.ofNullable(command.getPlaces()).ifPresent(garage::setPlaces);
        Optional.ofNullable(command.getIsLpgAllowed()).ifPresent(garage::setLpgAllowed);
        return garageRepository.saveAndFlush(garage);
    }

    @Transactional
    public void addCarToGarage(int id, int carId) {
        Garage garage = garageRepository.findById(id).orElseThrow(GarageNotFoundException::new);
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
        garage.addCar(car);
        garageRepository.saveAndFlush(garage);
    }

    public void removeCarFromGarage(int id, int carId) {
        Garage garage = garageRepository.findById(id).orElseThrow(GarageNotFoundException::new);
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
        garage.remove(car);
        garageRepository.saveAndFlush(garage);
    }



}

