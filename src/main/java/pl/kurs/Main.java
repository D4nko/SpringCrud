package pl.kurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        // https://spring.io/guides/gs/rest-hateoas
        // zrobic metode w kontrolerze, ktora pobiera samochody z konkretnego garazu, czyli powinna zwraca page<CarDto>
        // co w przypadku edycji obiektu jesli znajduje sie w cache


    }
}