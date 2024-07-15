package pl.kurs.dictionary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.Main;
import pl.kurs.dictionary.model.Dictionary;
import pl.kurs.dictionary.model.DictionaryValue;
import pl.kurs.dictionary.model.command.CreateDictionaryCommand;
import pl.kurs.dictionary.model.command.CreateValueForDictionaryCommand;
import pl.kurs.dictionary.model.dto.DictionaryDto;
import pl.kurs.dictionary.repository.DictionaryRepository;
import pl.kurs.dictionary.repository.DictionaryValueRepository;
import pl.kurs.dictionary.service.DictionaryService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DictionaryControllerTest {

    @Autowired
    private MockMvc postman;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private DictionaryValueRepository dictionaryValueRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldSaveDictionary() throws Exception {

        Set<String> testValues = Set.of("value1", "value2");

        CreateDictionaryCommand testCommand = CreateDictionaryCommand.builder()
                .name("dictionary 1")
                .initialValues(testValues)
                .build();

        String json = objectMapper.writeValueAsString(testCommand);

        String responseJson = postman.perform(post("/api/v1/dictionaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("dictionary 1"))
                .andExpect(jsonPath("$.values", hasSize(2)))
                .andExpect(jsonPath("$.values[0]").isNotEmpty())
                .andExpect(jsonPath("$.values[1]").isNotEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andReturn()
                .getResponse()
                .getContentAsString();

        DictionaryDto saved = objectMapper.readValue(responseJson, DictionaryDto.class);
        Dictionary recentlyAdded = dictionaryRepository.findByIdWithValues(saved.id()).get();

        Assertions.assertEquals("dictionary 1", recentlyAdded.getName());
        Assertions.assertFalse(recentlyAdded.isDeleted());

        Set<String> values = new HashSet<>();
        for (DictionaryValue dictionaryValue : recentlyAdded.getValues()) {
            values.add(dictionaryValue.getValue());
        }

        Assertions.assertTrue(values.contains("value1"));
        Assertions.assertTrue(values.contains("value2"));
    }

    // napisac podobny test na usuwanie slownika razem z wartosciami

    @Test
    public void shouldDeleteDictionaryWithValues() throws Exception {
        int id = dictionaryRepository.saveAndFlush(new Dictionary("toDelete")).getId();

        Set<String> testValues = Set.of("value1", "value2");

        CreateValueForDictionaryCommand testCommand = CreateValueForDictionaryCommand.builder()
                .dictionaryId(id)
                .values(testValues)
                .build();
        System.out.println("------------------ sekcja 0 ------------------------");

        String json = objectMapper.writeValueAsString(testCommand);

        postman.perform(post("/api/v1/dictionaries/" + id + "/values")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        System.out.println("------------------ sekcja 1 ------------------------");
        Dictionary beforeDeleted = dictionaryRepository.findByIdWithValues(id).get();
        System.out.println("------------------ sekcja 2 ------------------------");
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        // sprawdzcie czy sekcja 2 ma dokladnie 2 zapytania do bazy
        postman.perform(delete("/api/v1/dictionaries/" + id))
                .andExpect(status().isNoContent());
        System.out.println("------------------ sekcja 3 ------------------------");
        Assertions.assertFalse(dictionaryRepository.existsById(id));
        beforeDeleted.getValues().stream().forEach(value -> Assertions.assertFalse(dictionaryValueRepository.existsById(value.getId())));
    }

    // dodac test na edycje


    @Test
    public void shouldDeleteValueFromDictionary() throws Exception {

        Set<String> testValues = Set.of("value1", "value2");

        CreateDictionaryCommand testCommand = CreateDictionaryCommand.builder()
                .name("dictionary with values")
                .initialValues(testValues)
                .build();

        DictionaryDto savedDictionary = dictionaryService.save(testCommand);
        Dictionary recentlyAdded = dictionaryRepository.findByIdWithValues(savedDictionary.id()).get();

        DictionaryValue valueToDelete = recentlyAdded.getValues().iterator().next();

        postman.perform(delete("/api/v1/dictionaries/" + recentlyAdded.getId() + "/values/" + valueToDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Dictionary> dictionaryAfterDeletionOpt = dictionaryRepository.findByIdWithValues(recentlyAdded.getId());
        Assertions.assertTrue(dictionaryAfterDeletionOpt.isPresent(), "Dictionary should still be present after value deletion");

        Dictionary dictionaryAfterDeletion = dictionaryAfterDeletionOpt.get();
        Assertions.assertFalse(dictionaryAfterDeletion.getValues().contains(valueToDelete), "Value should be removed from the dictionary");
    }
}

