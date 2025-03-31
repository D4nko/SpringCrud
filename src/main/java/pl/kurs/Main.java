package pl.kurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

@SpringBootApplication
@EnableAsync
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        // todo do wykonania zadanie z messengera(screen)
        // api do uzytkownikow


      /*
        - mikroserwisy 1-2
        - teoria
       */

//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("book.csv"))) {
//            for (int i = 0; i < 20_000_000; i++) {
//                int randomId = (int) (Math.random() * 2) + 1;
//                bw.write("title_" + i + ",LEKTURA," + randomId);
//                bw.newLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}