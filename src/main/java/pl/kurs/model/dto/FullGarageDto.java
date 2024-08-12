package pl.kurs.model.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import pl.kurs.controller.GarageController;
import pl.kurs.model.Garage;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@NoArgsConstructor
public class FullGarageDto extends RepresentationModel<FullGarageDto> {

    private int id;
    private int places;
    private String address;
    private Boolean lpgAllowed;

    private int amountOfCars;

    public static FullGarageDto from(Garage garage) {
        FullGarageDto fullGarageDto = new FullGarageDto(garage.getId(), garage.getPlaces(), garage.getAddress(), garage.getLpgAllowed(), garage.getCars().size());

        fullGarageDto.add(linkTo(methodOn(GarageController.class).findGarage(fullGarageDto.id)).withRel("Link to Garage"));

        return fullGarageDto;
    }
}
