package pl.kurs.model.dto;

import pl.kurs.model.Author;

public record AuthorDto(int id, String name, String surname, Integer birthDate, Integer deathDate, Long amauntOfBooks) {

//    public static AuthorDto from(Author author) {
//        return new AuthorDto(author.getId(), author.getName(), author.getSurname(), author.getBirthYear(), author.getDeathYear(), author.getBooks().size());
//    }

}
