package pl.kurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.kurs.controller.CarController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootApplication
@EnableAsync
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

//        try (BufferedWriter out = new BufferedWriter(new FileWriter("books.csv"))) {
//            for (int i = 0; i < 30_000_000; i++) {
//                int randomAuthro = (int) (Math.random() * 2) + 1;
//                out.write("title_" + i + ",ROMANCE," + randomAuthro);
//                out.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // dodac brakujace testy do metod, sprawdzic cvzy wszystko jest
        // dodac metode w importService na updateToFail()



    }
}