package lab.integracja.services;

import lab.integracja.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public boolean isValidCountryCode(String countryCode) {
        return countryRepository.findByCode(countryCode).isPresent();
    }

}
