package lab.integracja.repositories;

import lab.integracja.entities.AlcoholConsumption;
import lab.integracja.entities.Country;
import lab.integracja.entities.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AlcoholConsumptionRepositoryTest {

    @Autowired
    private AlcoholConsumptionRepository alcoholConsumptionRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void testAvgAlcoholConsumptionForCountry() {
        // Arrange
        Country country = new Country(null, "ENW"); // Create a Country entity or use a mock if needed
        country = countryRepository.save(country);
        AlcoholConsumption alcoholConsumption = new AlcoholConsumption();
        alcoholConsumption.setCountry(country);
        alcoholConsumption.setSubject(Subject.TOT);
        alcoholConsumption.setValue(10.5f);
        alcoholConsumption = alcoholConsumptionRepository.save(alcoholConsumption);

        // Act
        float avgConsumption = alcoholConsumptionRepository.avgAlcoholConsumptionForCountry(alcoholConsumption.getCountry().getId());

        // Assert
        assertThat(avgConsumption).isEqualTo(10.5f);
    }

    @Test
    public void testFindAllByTimeAndSubjectOrderByTime() {
        // Arrange
        AlcoholConsumption alcoholConsumption1 = new AlcoholConsumption();
        alcoholConsumption1.setTime(2022);
        alcoholConsumption1.setSubject(Subject.TOT);
        alcoholConsumptionRepository.save(alcoholConsumption1);

        AlcoholConsumption alcoholConsumption2 = new AlcoholConsumption();
        alcoholConsumption2.setTime(2022);
        alcoholConsumption2.setSubject(Subject.TOT);
        alcoholConsumptionRepository.save(alcoholConsumption2);

        // Act
        List<AlcoholConsumption> result = alcoholConsumptionRepository.findAllByTimeAndSubjectOrderByTime(2022, Subject.TOT);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTime()).isEqualTo(2022);
        assertThat(result.get(1).getTime()).isEqualTo(2022);
    }

    @Test
    public void testFindAllByCountry_CodeAndSubjectOrderByTime() {
        // Arrange
        Country country = new Country(null, "US");
        country = countryRepository.save(country);
        AlcoholConsumption alcoholConsumption1 = new AlcoholConsumption();
        alcoholConsumption1.setCountry(country);
        alcoholConsumption1.setSubject(Subject.TOT);
        alcoholConsumptionRepository.save(alcoholConsumption1);

        AlcoholConsumption alcoholConsumption2 = new AlcoholConsumption();
        alcoholConsumption2.setCountry(country);
        alcoholConsumption2.setSubject(Subject.TOT);
        alcoholConsumptionRepository.save(alcoholConsumption2);

        // Act
        List<AlcoholConsumption> result = alcoholConsumptionRepository.findAllByCountry_CodeAndSubjectOrderByTime("US", Subject.TOT);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCountry().getCode()).isEqualTo("US");
        assertThat(result.get(1).getCountry().getCode()).isEqualTo("US");
    }
}
