package pl.kurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kurs.model.Car;
import pl.kurs.model.command.CreatCarCommand;
import pl.kurs.model.command.EditCarCommand;
import pl.kurs.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAllCars() {
        List<Car> carList = new ArrayList<>();
        carList.add(new Car("BMW", "M2", "PB"));
        carList.add(new Car("Ferrari", "F8", "PB"));
        when(carRepository.findAll()).thenReturn(carList);

        List<Car> foundCars = carService.findAll();

        assertEquals(carList.size(), foundCars.size());
        verify(carRepository).findAll();
    }

    @Test
    public void shouldSaveNewCar() {
        CreatCarCommand command = new CreatCarCommand("Audi", "A4", "ON");
        Car expectedCar = new Car("Audi", "A4", "ON");
        when(carRepository.saveAndFlush(any())).thenReturn(expectedCar);

        Car savedCar = carService.save(command);

        assertNotNull(savedCar);
        assertEquals(expectedCar, savedCar);
        verify(carRepository).saveAndFlush(any());
    }


    @Test
    public void shouldFindCarById() {
        int carId = 1;
        Car expectedCar = new Car("BMW", "M2", "PB");
        when(carRepository.findById(carId)).thenReturn(Optional.of(expectedCar));

        Optional<Car> foundCar = carService.findById(carId);

        assertTrue(foundCar.isPresent());
        assertEquals(expectedCar, foundCar.get());
        verify(carRepository).findById(carId);
    }

    @Test
    public void shouldEditCar() {
        int carId = 1;
        EditCarCommand command = new EditCarCommand();
        Car car = new Car("BMW", "M2", "PB");
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carRepository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Car editedCar = carService.edit(carId, command);

        assertEquals(command.getBrand(), editedCar.getBrand());
        assertEquals(command.getModel(), editedCar.getModel());
        assertEquals(command.getFuelType(), editedCar.getFuelType());
        verify(carRepository).findById(carId);
        verify(carRepository).saveAndFlush(any());
    }

    @Test
    public void shouldPartiallyEditCar() {
        int carId = 1;
        EditCarCommand command = new EditCarCommand();
        command.setModel("A4");
        Car car = new Car("BMW", "M2", "PB");
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carRepository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Car partiallyEditedCar = carService.partiallyEdit(carId, command);

        assertEquals(car.getBrand(), partiallyEditedCar.getBrand());
        assertEquals(command.getModel(), partiallyEditedCar.getModel());
        assertEquals(car.getFuelType(), partiallyEditedCar.getFuelType());
        verify(carRepository).findById(carId);
        verify(carRepository).saveAndFlush(any());
    }

//    @Test
//    public void shouldDeleteCar(){
//        int carId = 1;
//        carService.deleById(carId);
//        verify(carRepository).deleteById(carId);
//
//    }
}
