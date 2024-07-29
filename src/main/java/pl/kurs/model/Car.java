package pl.kurs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "brand_name", length = 500)
    private String brand;
    private String model;
    private String fuelType;

    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;

    @Version
    private long version;

    public Car(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    public Car(Car car) {
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.fuelType = car.getFuelType();
        this.garage = car.getGarage();
        this.version = car.getVersion();
    }
}
