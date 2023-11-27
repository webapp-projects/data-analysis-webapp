package lab.integracja.services;

import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import lab.integracja.repositories.SuicideRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuicideRateServiceTests {
    @Mock
    private SuicideRateRepository suicideRateRepository;

    @InjectMocks
    private SuicideRateService suicideRateService;

    @Test
    void getBySubjectAndYear_ReturnsListOfSuicideRates() {
        // Arrange
        int year = 2022;
        Subject subject = Subject.MEN;
        List<SuicideRate> mockSuicideRates = Arrays.asList(new SuicideRate(), new SuicideRate());
        when(suicideRateRepository.findAllByTimeAndSubjectOrderByTime(year, subject)).thenReturn(mockSuicideRates);

        // Act
        List<SuicideRate> result = suicideRateService.getBySubjectAndYear(year, subject);

        // Assert
        assertEquals(mockSuicideRates, result);
    }

    @Test
    void avgSuicideRateForCountry_ReturnsAverageSuicideRate() {
        // Arrange
        Long countryId = 1L;
        float mockAverageRate = 5.0f;
        when(suicideRateRepository.avgSuicideRateForCountry(countryId)).thenReturn(mockAverageRate);

        // Act
        float result = suicideRateService.avgSuicideRateForCountry(countryId);

        // Assert
        assertEquals(mockAverageRate, result);
    }

    @Test
    void getByCountryAndSubject_ReturnsListOfSuicideRates() {
        // Arrange
        String countryCode = "US";
        Subject subject = Subject.WOMEN;
        List<SuicideRate> mockSuicideRates = Arrays.asList(new SuicideRate(), new SuicideRate());
        when(suicideRateRepository.findAllByCountry_CodeAndSubjectOrderByTime(countryCode, subject))
                .thenReturn(mockSuicideRates);

        // Act
        List<SuicideRate> result = suicideRateService.getByCountryAndSubject(countryCode, subject);

        // Assert
        assertEquals(mockSuicideRates, result);
    }
}
