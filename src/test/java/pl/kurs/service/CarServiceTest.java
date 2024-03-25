package pl.kurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.exceptions.CarNotFoundException;
import pl.kurs.model.Car;
import pl.kurs.model.command.CreatCarCommand;
import pl.kurs.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
//public class CarServiceTest {
//
//    @Mock
//    private CarRepository carRepository;
//
//    @InjectMocks
//    private CarService carService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    public void shouldFindAllCars() {
//        List<Car> carList = new ArrayList<>();
//        carList.add(new Car("BMW", "M2", "PB"));
//        carList.add(new Car("Ferrari", "F8", "PB"));
//        when(carRepository.findAll()).thenReturn(carList);
//
//        List<Car> foundCars = carService.findAll();
//
//        assertEquals(carList.size(), foundCars.size());
//        verify(carRepository).findAll();
//    }
//
//    @Test
//    public void shouldSaveNewCar() {
//        CreatCarCommand command = new CreatCarCommand("Audi", "A4", "ON");
//        Car expectedCar = new Car("Audi", "A4", "ON");
//        when(carRepository.saveAndFlush(any())).thenReturn(expectedCar);
//
//        Car savedCar = carService.save(command);
//
//        assertNotNull(savedCar);
//        assertEquals(expectedCar, savedCar);
//        verify(carRepository).saveAndFlush(any());
//    }
//
//    @Test
//    public void shouldThrowCarNotFoundExceptionWhenCarNotFound() {
//        int carId = 1;
//        when(carRepository.findById(carId)).thenReturn(Optional.empty());
//
//        assertThrows(CarNotFoundException.class, () -> carService.findById(carId));
//    }
//
//    @Test
//    public void shouldFindCarById() {
//        int carId = 1;
//        Car expectedCar = new Car("BMW", "M2", "PB");
//        when(carRepository.findById(carId)).thenReturn(Optional.of(expectedCar));
//
//        Optional<Car> foundCar = carService.findById(carId);
//
//        assertNotNull(foundCar);
//        assertEquals(expectedCar, foundCar);
//        verify(carRepository).findById(carId);
//    }
//
//}
