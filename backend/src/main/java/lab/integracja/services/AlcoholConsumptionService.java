package lab.integracja.services;

import lab.integracja.entities.AlcoholConsumption;
import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import lab.integracja.repositories.AlcoholConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class AlcoholConsumptionService {

    private final AlcoholConsumptionRepository alcoholRepository;

    public float avgSuicideRateForCountry(Long id) {
        return alcoholRepository.avgAlcoholConsumptionForCountry(id);
    }

    public List<AlcoholConsumption> getBySubjectAndYear(int year, Subject subject) {
        return alcoholRepository.findAllByTimeAndSubjectOrderByTime(year, subject);
    }

    public List<AlcoholConsumption> getByCountryAndSubject(String countryCode, Subject subject) {
        return alcoholRepository.findAllByCountry_CodeAndSubjectOrderByTime(countryCode, subject);
    }

}
