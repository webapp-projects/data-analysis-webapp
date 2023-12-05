package lab.integracja.services;

import lab.integracja.controllers.CountryData;
import lab.integracja.entities.Country;
import lab.integracja.entities.Measure;
import lab.integracja.repositories.AlcoholConsumptionRepository;
import lab.integracja.repositories.CountryRepository;
import lab.integracja.repositories.MeasureRepository;
import lab.integracja.repositories.SuicideRateRepository;
import lab.integracja.utils.CsvUtils;
import lab.integracja.utils.JSONUtils;
import lab.integracja.utils.XMLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class CountryService {

    private final CountryRepository countryRepository;
    private final SuicideRateRepository suicideRateRepository;
    private final AlcoholConsumptionRepository alcoholConsumptionRepository;
    private final MeasureRepository measureRepository;
    private final CsvUtils csvUtils;
    private final XMLUtils xmlUtils;
    private final JSONUtils jsonUtils;

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

    public void saveJsonToDatabase(MultipartFile file) {
        try {
            List<Country> countries = jsonUtils.jsonToCountries(file.getInputStream());
            countryRepository.saveAll(countries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addData(CountryData data) {
        Country country = countryRepository.findByCode(data.countryCode())
                .orElse(
                        countryRepository.save(new Country(null, data.countryCode()))
                );
        Measure measureAlcohol = measureRepository
                .findByName(data.alcoholConsumptionData().measure())
                .orElse(measureRepository.save(
                        new Measure(null, data.alcoholConsumptionData().measure()))
                );
        Measure measureSuicide = measureRepository
                .findByName(data.suicideRateData().measure())
                .orElse(measureRepository.save(
                        new Measure(null, data.suicideRateData().measure()))
                );
        suicideRateRepository.save(data.suicideRateData().toEntity(country, measureSuicide));
        alcoholConsumptionRepository.save(data.alcoholConsumptionData().toEntity(country, measureAlcohol));
    }

}
