package pl.kurs;

import com.querydsl.apt.jpa.JPAAnnotationProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);


        // nalezy zaimplementowac mechanizm istnienia minimalnych atrybnutow do stworzenia konkretnego typu encji

        // nowy typ = brak modyfikacji
        // open-closed principle = otwarte na rozszerzenia, ale zamknięte na modyfikacje.

        // next-> query DSL
    }
}