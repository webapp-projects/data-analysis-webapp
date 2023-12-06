package lab.integracja;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.integracja.controllers.AlcoholConsumptionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class IntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private AlcoholConsumptionController alcoholConsumptionController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(alcoholConsumptionController)
                .build();
    }

    @Test
    public void testGetByYearAndSubject() throws Exception {
        // Arrange
        int year = 2022;
        String subject = "TOT";

        // Act & Assert
        mockMvc.perform(get("/api/alcohol/year/{year}/subject/{subject}", year, subject))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetByCountry() throws Exception {
        // Arrange
        String countryCode = "AUS";
        String subject = "TOT";

        // Act & Assert
        mockMvc.perform(get("/api/alcohol/country/{countryCode}", countryCode)
                        .param("subject", subject))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetByCountryInvalidInput() throws Exception {
        // Arrange
        String countryCode = "INVALID_CODE";
        String subject = "INVALID_SUBJECT";

        // Act & Assert
        mockMvc.perform(get("/api/alcohol/country/{countryCode}", countryCode)
                        .param("subject", subject))
                .andExpect(status().isNotFound());
    }
}

