package lab.integracja.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.integracja.config.JwtAuthenticationFilter;
import lab.integracja.controllers.AlcoholConsumptionController;
import lab.integracja.entities.AlcoholConsumption;
import lab.integracja.services.AlcoholConsumptionService;
import lab.integracja.services.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlcoholConsumptionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AlcoholConsumptionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlcoholConsumptionService alcoholConsumptionService;

    @MockBean
    private CountryService countryService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldReturnCountry() throws Exception {
        AlcoholConsumption alcoholConsumption = new AlcoholConsumption(1L, null, null, null, 2, 2.5f);
        when(alcoholConsumptionService.getByCountryAndSubject(any(), any())).thenReturn(List.of(alcoholConsumption));

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(List.of(alcoholConsumption));

        mockMvc.perform(get("/api/suicide/country/POL?subject=MEN"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(jsonBody));
    }

}
