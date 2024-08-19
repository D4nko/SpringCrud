package pl.kurs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.controller.CarController;
import pl.kurs.model.Car;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullCarDto extends RepresentationModel<FullCarDto> {
    private int id;
    private String brand;
    private String model;
    private String fuelType;
    private long version;

    public static FullCarDto from(Car car) {
        FullCarDto fullCarDto = new FullCarDto(car.getId(), car.getBrand(), car.getModel(), car.getFuelType(), car.getVersion());
        fullCarDto.add(linkTo(methodOn(CarController.class).findCar(car.getId())).withSelfRel());
        if (car.getGarage() != null) {
            fullCarDto.add(linkTo(methodOn(CarController.class).getCarsByGarage(car.getGarage().getId(), null)).withRel("Cars in garage"));
        }
        return fullCarDto;
    }
}
