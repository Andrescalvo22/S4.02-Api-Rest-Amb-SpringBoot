package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateFruit() throws Exception {
        FruitDTO dto = new FruitDTO(null, "Apple", 3);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(3));
    }

    @Test
    void testGetAllFruits() throws Exception {
        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetFruitById() throws Exception {
        FruitDTO dto = new FruitDTO(null, "Banana", 2);
        String json = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse().getContentAsString();

        FruitDTO created = objectMapper.readValue(response, FruitDTO.class);
        mockMvc.perform(get("/fruits/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banana"));
    }

    @Test
    void testUpdateFruit() throws Exception {
        FruitDTO dto = new FruitDTO(null, "Orange", 4);
        String json = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse().getContentAsString();

        FruitDTO created = objectMapper.readValue(response, FruitDTO.class);

        FruitDTO updated = new FruitDTO(null, "Big Orange", 5);

        mockMvc.perform(put("/fruits/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Big Orange"))
                .andExpect(jsonPath("$.weightInKilos").value(5));
    }

    @Test
    void testDeleteFruit() throws Exception {
        FruitDTO dto = new FruitDTO(null, "Grapes", 1);

        String response = mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        FruitDTO created = objectMapper.readValue(response, FruitDTO.class);

        mockMvc.perform(delete("/fruits/" + created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/fruits/" + created.getId()))
                .andExpect(status().isNotFound());
    }
}
