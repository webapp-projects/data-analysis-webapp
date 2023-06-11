package lab.integracja.services;

import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import lab.integracja.repositories.SuicideRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuicideRateService {

    private final SuicideRateRepository suicideRateRepository;

    public List<SuicideRate> getBySubjectAndYear(int year, Subject subject) {
        return suicideRateRepository.findAllByTimeAndSubjectOrderByTime(year, subject);
    }

    public float avgSuicideRateForCountry(Long id) {
        return suicideRateRepository.avgSuicideRateForCountry(id);
    }

    public List<SuicideRate> getByCountryAndSubject(String countryCode, Subject subject) {
        return suicideRateRepository.findAllByCountry_CodeAndSubjectOrderByTime(countryCode, subject);
    }
}
