package pl.kurs.inheritance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.Main;
import pl.kurs.inheritance.model.command.CreatePersonCommand;
import pl.kurs.model.PersonParameter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PersonControllerTest {

    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void shouldNotCreatePerson_UnknownType() throws Exception {
        CreatePersonCommand command = new CreatePersonCommand();
        command.setClassType("OLABOGA");
        List<PersonParameter> parameters = new ArrayList<>();
        parameters.add(new PersonParameter("name", "Sample name"));
        parameters.add(new PersonParameter("surname", "Sample surName"));
        parameters.add(new PersonParameter("age", "21"));

        command.setParameters(parameters);

        String json = objectMapper.writeValueAsString(command);

        postman.perform(MockMvcRequestBuilders.post("/api/v1/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]").value("UNKNOWN_ENTITY_TYPE"));
    }

    @Test
    public void shouldNotCreatePerson_MissingRequiredFields() throws Exception {
        CreatePersonCommand command = new CreatePersonCommand();
        command.setClassType("employee");
        List<PersonParameter> parameters = new ArrayList<>();
        parameters.add(new PersonParameter("name", "Jan Krystyna"));
        parameters.add(new PersonParameter("age", "30"));

        command.setParameters(parameters);

        String json = objectMapper.writeValueAsString(command);

        postman.perform(MockMvcRequestBuilders.post("/api/v1/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]").value("NO_REQUIRED_ATTRIBUTES"));
    }
}