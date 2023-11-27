package lab.integracja.services;

import lab.integracja.entities.Country;
import lab.integracja.repositories.CountryRepository;
import lab.integracja.utils.CsvUtils;
import lab.integracja.utils.JSONUtils;
import lab.integracja.utils.XMLUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTests {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CsvUtils csvUtils;

    @Mock
    private XMLUtils xmlUtils;

    @Mock
    private JSONUtils jsonUtils;

    @InjectMocks
    private CountryService countryService;

    @Test
    void isValidCountryCode_ValidCode_ReturnsTrue() {
        // Arrange
        String validCountryCode = "US";
        when(countryRepository.findByCode(validCountryCode)).thenReturn(Optional.of(new Country()));

        // Act
        boolean isValid = countryService.isValidCountryCode(validCountryCode);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isValidCountryCode_InvalidCode_ReturnsFalse() {
        // Arrange
        String invalidCountryCode = "XX";
        when(countryRepository.findByCode(invalidCountryCode)).thenReturn(Optional.empty());

        // Act
        boolean isValid = countryService.isValidCountryCode(invalidCountryCode);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void getAll_ReturnsListOfCountries() {
        // Arrange
        List<Country> mockCountries = Arrays.asList(new Country(), new Country());
        when(countryRepository.findAll()).thenReturn(mockCountries);

        // Act
        List<Country> result = countryService.getAll();

        // Assert
        assertEquals(mockCountries, result);
    }

    @Test
    void saveCsvToDatabase_ValidCsvFile_CallsCsvUtilsAndSavesToDatabase() throws IOException {
        // Arrange
        MockMultipartFile csvFile = new MockMultipartFile("file", "test.csv", "text/csv", "mock,csv".getBytes());
        List<Country> mockCountries = Arrays.asList(new Country(), new Country());
        when(csvUtils.csvToCountries(Mockito.any(ByteArrayInputStream.class))).thenReturn(mockCountries);

        // Act
        countryService.saveCsvToDatabase(csvFile);

        // Assert
        Mockito.verify(csvUtils).csvToCountries(Mockito.any(ByteArrayInputStream.class));
        Mockito.verify(countryRepository).saveAll(mockCountries);
    }

}
