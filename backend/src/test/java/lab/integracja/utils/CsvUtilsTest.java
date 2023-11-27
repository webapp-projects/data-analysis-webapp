package lab.integracja.utils;

import lab.integracja.entities.Country;
import lab.integracja.entities.RawData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CsvUtilsTest {

    @InjectMocks
    private CsvUtils csvUtils;

    @Test
    void hasCSVFormat_ValidCsvFile_ReturnsTrue() {
        // Arrange
        byte[] content = "test,csv,content".getBytes();
        MockMultipartFile csvFile = new MockMultipartFile("file", "test.csv", CsvUtils.TYPE, content);

        // Act
        boolean result = csvUtils.hasCSVFormat(csvFile);

        // Assert
        assertTrue(result);
    }

    @Test
    void hasCSVFormat_InvalidFileType_ReturnsFalse() {
        // Arrange
        byte[] content = "test,txt,content".getBytes();
        MockMultipartFile txtFile = new MockMultipartFile("file", "test.txt", "text/plain", content);

        // Act
        boolean result = csvUtils.hasCSVFormat(txtFile);

        // Assert
        assertFalse(result);
    }

    @Test
    void writeCountriesToCsv_WritesCorrectData() throws IOException {
        // Arrange
        List<Country> countries = Arrays.asList(new Country(1L, "US"), new Country(2L, "CA"));
        Writer writer = new StringWriter();

        // Act
        csvUtils.writeCountriesToCsv(countries, writer);

        // Assert
        String expectedCsvContent = "1,US\n2,CA\n";
        assertEquals(expectedCsvContent, writer.toString().replaceAll("\\r\\n", "\n"));
    }

    @Test
    void writeRawDataToCsv_WritesCorrectData() throws IOException {
        // Arrange
        List<RawData> rawDataList = Arrays.asList(
                new RawData(1L, "Location1", "2", "Sub1", "Measure1", "Freq1", 1, 2.0f, "Code1"),
                new RawData(2L, "Location2", "2", "Sub2", "Measure2", "Freq2", 2, 4.0f, "Code2")
        );
        Writer writer = new StringWriter();

        // Act
        csvUtils.writeRawDataToCsv(rawDataList, writer);

        // Assert
        String expectedCsvContent = "1,2,2.0,Sub1\n2,2,4.0,Sub2\n";
        assertEquals(expectedCsvContent, writer.toString().replaceAll("\\r\\n", "\n"));
    }

    @Test
    void csvToCountries_ParsesCsvFile_ReturnsListOfCountries() {
        // Arrange
        String csvContent = "id,code\n1,US\n2,CA\n";
        ByteArrayInputStream is = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        // Act
        List<Country> result = csvUtils.csvToCountries(is);

        // Assert
        List<Country> expectedCountries = Arrays.asList(new Country(1L, "US"), new Country(2L, "CA"));
        assertEquals(expectedCountries, result);
    }
}
