package lab.integracja.services;

import lab.integracja.entities.Country;
import lab.integracja.repositories.CountryRepository;
import lab.integracja.utils.CsvUtils;
import lab.integracja.utils.XMLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class CountryService {

    private final CountryRepository countryRepository;
    private final CsvUtils csvUtils;
    private final XMLUtils xmlUtils;

    public boolean isValidCountryCode(String countryCode) {
        return countryRepository.findByCode(countryCode).isPresent();
    }

    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    public void saveCsvToDatabase(MultipartFile file) {
        try {
            List<Country> countries = csvUtils.csvToCountries(file.getInputStream());
            countryRepository.saveAll(countries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveXMLToDatabase(MultipartFile file) {
        try {
            List<Country> countries = xmlUtils.xmlToCountries(file.getInputStream());
            countryRepository.saveAll(countries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
