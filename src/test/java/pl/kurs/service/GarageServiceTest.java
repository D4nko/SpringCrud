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

}
