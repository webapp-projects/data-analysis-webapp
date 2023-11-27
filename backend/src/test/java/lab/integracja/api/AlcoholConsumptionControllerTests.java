package lab.integracja.api;

import lab.integracja.config.JwtAuthenticationFilter;
import lab.integracja.controllers.AlcoholConsumptionController;
import lab.integracja.entities.AlcoholConsumption;
import lab.integracja.services.AlcoholConsumptionService;
import lab.integracja.services.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AlcoholConsumptionControllerTests {

    private MockMvc mockMvc;

    @InjectMocks
    private AlcoholConsumptionController alcoholConsumptionController;

    @Mock
    private AlcoholConsumptionService alcoholConsumptionService;

    @Mock
    private CountryService countryService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(alcoholConsumptionController)
                .build();
    }

    @Test
    void shouldReturnCountry() throws Exception {
        AlcoholConsumption alcoholConsumption = new AlcoholConsumption(1L, null, null, null, 2, 2.5f);
        when(alcoholConsumptionService.getByCountryAndSubject(any(), any())).thenReturn(List.of(alcoholConsumption));
        when(countryService.isValidCountryCode(any())).thenReturn(true);

        mockMvc.perform(get("/api/alcohol/country/POL"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void checkParameters() throws Exception {
        AlcoholConsumption alcoholConsumption = new AlcoholConsumption(1L, null, null, null, 2, 2.5f);
        lenient().when(alcoholConsumptionService.getByCountryAndSubject(any(), any())).thenReturn(List.of(alcoholConsumption));
        lenient().when(countryService.isValidCountryCode(any())).thenReturn(false);
        mockMvc.perform(get("/api/alcohol/country/"))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnConsumptionRate() throws Exception {
        AlcoholConsumption alcoholConsumption = new AlcoholConsumption(1L, null, null, null, 2, 2.5f);
        when(alcoholConsumptionService.getBySubjectAndYear(anyInt(), any())).thenReturn(List.of(alcoholConsumption));

        mockMvc.perform(get("/api/alcohol/year/1/subject/TOT"))
                .andExpect(status().is2xxSuccessful());
    }

}
