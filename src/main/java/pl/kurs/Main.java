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

//        @CachePut(value = "articles", key = "#article.id")
//         public Article updateArticle(Article article) {
//         Article updatedArticle = articleRepository.save(article);
//          return updatedArticle;
//}
        //// @Cacheable jest idealna do stosowania w metodach, które zwracają dane rzadko ulegające zmianom, ale są często odczytywane.
        // Dzięki przechowywaniu tych danych w pamięci podręcznej (cache), możemy znacząco zwiększyć wydajność aplikacji,
        // unikając zbędnych operacji na bazie danych lub innych kosztownych obliczeń.
        //
        //// @CachePut służy do aktualizowania pamięci podręcznej po każdorazowym wywołaniu metody. W odróżnieniu od @Cacheable,
        // @CachePut zawsze wykonuje metodę, a następnie zapisuje wynik do pamięci podręcznej, niezależnie od tego,
        // czy wartość już istniała w cache, czy nie. Jest to przydatne, gdy chcemy mieć pewność, że pamięć podręczna zawsze zawiera aktualne dane po modyfikacji.
        //@CachePut jest szczególnie użyteczna w metodach modyfikujących dane, takich jak operacje zapisu (create, update). Gwarantuje to,
        // że cache zostanie zaktualizowany natychmiast po dokonaniu zmiany, co zapobiega sytuacjom, w których cache mógłby przechowywać nieaktualne informacje.
        // Warto jednak pamiętać, że @CachePut nie zwraca danych z cache,
        // ale aktualizuje je na podstawie wyniku działania metody, co może wpływać na wydajność, jeśli metoda jest wywoływana bardzo często.


    }
}