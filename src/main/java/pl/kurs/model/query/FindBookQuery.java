package pl.kurs.model.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.kurs.generated.sources.model.QBook;

import java.util.Arrays;

@Getter
@Setter
@ToString
public class FindBookQuery {

    private String title;
    private String category;
    private Boolean available;
    private String author;

    public Predicate toPredicate() {
        BooleanBuilder conditions = new BooleanBuilder();
        if (title != null) conditions.and(QBook.book.title.containsIgnoreCase(title));
        if (category != null) conditions.and(QBook.book.category.eq(category));
        if (available != null) conditions.and(QBook.book.available.eq(available));

        if (author != null) {
            String[] authorParts = author.split(" ");
            BooleanBuilder authorConditions = new BooleanBuilder();
            Arrays.stream(authorParts).forEach(part -> authorConditions.or(QBook.book.author.surname.containsIgnoreCase(part))
                    .or(QBook.book.author.name.containsIgnoreCase(part)));
            conditions.and(authorConditions);
        }
        return conditions;
    }

    //todo dodac drugiego boolean buildera authorCondition tutaj porownanie podobnie jak pozostale elementy do ksaizki
    // i nastepnie ten authorConditions dodac do conditions
    // powinno działac tak ze wyszuka nam wszystkie ksiazki danych autorow kazimierz janina kowalski wielka itd
}
