package lab.integracja.api;

import lab.integracja.config.JwtAuthenticationFilter;
import lab.integracja.controllers.SuicideRateController;
import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import lab.integracja.services.CountryService;
import lab.integracja.services.SuicideRateService;
import lab.integracja.utils.EnumUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SuicideRateControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private SuicideRateController suicideRateController;

    @Mock
    private SuicideRateService suicideRateService;

    @Mock
    private CountryService countryService;

    @Mock
    EnumUtils enumUtils;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(suicideRateController)
                .build();
    }

    @Test
    public void testGetByYearAndSubject() throws Exception {
        // Arrange
        int year = 2022;
        String subject = "TOT";
        List<SuicideRate> expectedRates = Arrays.asList(new SuicideRate(), new SuicideRate());
        when(suicideRateService.getBySubjectAndYear(year, Subject.TOT)).thenReturn(expectedRates);

        // Act & Assert
        mockMvc.perform(get("/api/suicide/year/{year}/subject/{subject}", year, subject))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // Assuming the expected result has two elements
    }

    @Test
    public void testGetByCountry() throws Exception {
        // Arrange
        String countryCode = "US";
        String subject = "TOT";
        List<SuicideRate> expectedRates = Arrays.asList(new SuicideRate(), new SuicideRate());
        when(countryService.isValidCountryCode(countryCode)).thenReturn(true);
        try (MockedStatic<EnumUtils> utilities = Mockito.mockStatic(EnumUtils.class)) {
            utilities.when(() -> EnumUtils.isStringInEnum(any(), any()))
                    .thenReturn(true);
        }
        when(suicideRateService.getByCountryAndSubject(countryCode, Subject.valueOf(subject))).thenReturn(expectedRates);

        // Act & Assert
        mockMvc.perform(get("/api/suicide/country/{countryCode}", countryCode)
                        .param("subject", subject))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))); // Assuming the expected result has two elements
    }

    @Test
    public void testGetByCountryInvalidInput() throws Exception {
        // Arrange
        String countryCode = "INVALID_CODE";
        String subject = "INVALID_SUBJECT";
        when(countryService.isValidCountryCode(countryCode)).thenReturn(false);
        try (MockedStatic<EnumUtils> utilities = Mockito.mockStatic(EnumUtils.class)) {
            utilities.when(() -> EnumUtils.isStringInEnum(any(), any()))
                    .thenReturn(true);
        }
        // Act & Assert
        mockMvc.perform(get("/api/suicide/country/{countryCode}", countryCode)
                        .param("subject", subject))
                .andExpect(status().isNotFound());
    }
}
