package lab.integracja.services;

import lab.integracja.repositories.AlcoholConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlcoholConsumptionService {

    private final AlcoholConsumptionRepository alcoholRepository;

    public float avgSuicideRateForCountry(Long id) {
        return alcoholRepository.avgAlcoholConsumptionForCountry(id);
    }

}
