package pl.kurs.model.dto;

import pl.kurs.model.Car;

public record CarDto(int id, String brand, String model, String fuelType, long version) {

    public static CarDto from(Car car) {
        return new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getFuelType(), car.getVersion());
    }
}
