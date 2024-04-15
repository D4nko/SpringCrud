package pl.kurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kurs.exceptions.CarNotFoundException;
import pl.kurs.exceptions.GarageNotFoundException;
import pl.kurs.model.Car;
import pl.kurs.model.Garage;
import pl.kurs.model.command.CreateGarageCommand;
import pl.kurs.model.command.EditGarageCommand;
import pl.kurs.repository.CarRepository;
import pl.kurs.repository.GarageRepository;
import pl.kurs.service.GarageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GarageServiceTest {

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private GarageService garageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAll() {
        List<Garage> garageList = new ArrayList<>();
        garageList.add(new Garage(1, "ul. Testowa 1, Testowo", true));
        garageList.add(new Garage(2, "ul. Testowa 2, Testowo", false));
        when(garageRepository.findAllWithCars()).thenReturn(garageList);

        List<Garage> foundGarages = garageService.findAll();

        assertEquals(garageList.size(), foundGarages.size());
        verify(garageRepository).findAllWithCars();
    }

    @Test
    public void shouldSaveNewGarage() {
        CreateGarageCommand command = new CreateGarageCommand(50, "ul. Nowa 10, Testowo", true);
        Garage expectedGarage = new Garage(50, "ul. Nowa 10, Testowo", true);
        when(garageRepository.saveAndFlush(any())).thenReturn(expectedGarage);

        Garage savedGarage = garageService.save(command);

        assertNotNull(savedGarage);
        assertEquals(expectedGarage, savedGarage);
        verify(garageRepository).saveAndFlush(any());
    }
    @Test
    public void shouldFindGarageById() {
        int id = 1;
        Garage garage = new Garage(1, "ul. Testowa 1, Testowo", true);
        when(garageRepository.findByIdWithCars(id)).thenReturn(Optional.of(garage));

        Optional<Garage> foundGarage = garageService.findById(id);

        assertTrue(foundGarage.isPresent());
        assertEquals(garage, foundGarage.get());
        verify(garageRepository).findByIdWithCars(id);
    }

    @Test
    public void shouldThrowExceptionWhenAddingCarToNonexistentGarage() {
        int garageId = 99;
        int carId = 1;
        when(garageRepository.findById(garageId)).thenReturn(Optional.empty());

        assertThrows(GarageNotFoundException.class, () -> garageService.addCarToGarage(garageId, carId));
        verify(garageRepository).findById(garageId);
        verify(carRepository, never()).findById(carId);
    }

    @Test
    public void shouldDeleteGarageById() {
        int garageId = 1;
        garageService.deleteById(garageId);
        verify(garageRepository).deleteById(garageId);
    }

    @Test
    public void shouldRemoveCarFromGarage() {
        int garageId = 1;
        int carId = 1;
        Garage garage = new Garage(1, "ul. Testowa 1, Testowo", true);
        Car car = new Car("BMW", "M2", "PB");
        garage.addCar(car);
        when(garageRepository.findById(garageId)).thenReturn(Optional.of(garage));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        garageService.removeCarFromGarage(garageId, carId);

        assertFalse(garage.getCars().contains(car));
        verify(garageRepository).findById(garageId);
        verify(carRepository).findById(carId);
        verify(garageRepository).saveAndFlush(any());
    }

    @Test
    public void shouldAddCarToGarage() {
        int garageId = 1;
        int carId = 1;
        Garage garage = new Garage(1, "ul. Testowa 1, Testowo", true);
        Car car = new Car("BMW", "M2", "PB");
        when(garageRepository.findById(garageId)).thenReturn(Optional.of(garage));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        garageService.addCarToGarage(garageId, carId);

        assertTrue(garage.getCars().contains(car));
        verify(garageRepository).findById(garageId);
        verify(carRepository).findById(carId);
        verify(garageRepository).saveAndFlush(any());
    }

    @Test
    public void shouldEditGarage() {
        int garageId = 1;
        EditGarageCommand command = new EditGarageCommand();
        command.setPlaces(50);
        Garage garage = new Garage(1, "ul. Testowa 1, Testowo", true);
        when(garageRepository.findByIdWithCars(garageId)).thenReturn(Optional.of(garage));
        when(garageRepository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Garage editedGarage = garageService.edit(garageId, command);

        assertEquals(command.getAddress(), editedGarage.getAddress());
        assertEquals(command.getPlaces(), editedGarage.getPlaces());
        assertEquals(command.getIsLpgAllowed(), editedGarage.getLpgAllowed());
        verify(garageRepository).findByIdWithCars(garageId);
        verify(garageRepository).saveAndFlush(any());
    }


}
