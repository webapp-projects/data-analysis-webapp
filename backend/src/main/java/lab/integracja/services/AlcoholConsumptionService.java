package lab.integracja.services;

import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import lab.integracja.repositories.AlcoholConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlcoholConsumptionService {

    private final AlcoholConsumptionRepository alcoholRepository;

    public float avgSuicideRateForCountry(Long id) {
        return alcoholRepository.avgAlcoholConsumptionForCountry(id);
    }

    public List<SuicideRate> getBySubjectAndYear(int year, Subject subject) {
        return alcoholRepository.findAllByTimeAndSubjectOrderByTime(year, subject);
    }

    public List<SuicideRate> getByCountryAndSubject(String countryCode, Subject subject) {
        return alcoholRepository.findAllByCountry_CodeAndSubjectOrderByTime(countryCode, subject);
    }

}
