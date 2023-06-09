package lab.integracja;

import lab.integracja.entities.*;
import lab.integracja.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final CountryRepository countryRepository;
    private final MeasureRepository measureRepository;
    private final AlcoholConsumptionRepository alcoholConsumptionRepository;
    private final SuicideRateRepository suicideRateRepository;
    private final RawDataRepository rawDataRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Started database initialization...");
        for (RawData data : rawDataRepository.findAll()) {
            if (data.getIndicator().equals("SUICIDE")) {
                SuicideRate suicideRate = new SuicideRate();
                Optional<Country> country = countryRepository.findByCode(data.getLocation());
                if (country.isEmpty()) {
                    suicideRate.setCountry(countryRepository.save(new Country(null, data.getLocation())));
                } else {
                    suicideRate.setCountry(country.get());
                }

                Optional<Measure> measure = measureRepository.findByName(data.getMeasure());
                if (measure.isEmpty()) {
                    suicideRate.setMeasure(measureRepository.save(new Measure(null, data.getMeasure())));
                } else {
                    suicideRate.setMeasure(measure.get());
                }

                suicideRate.setTime(data.getTime());
                suicideRate.setValue(data.getValue());
                suicideRate.setSubject(Subject.valueOf(data.getSubject()));
                suicideRateRepository.save(suicideRate);
            }
            else {
                AlcoholConsumption alcoholConsumption = new AlcoholConsumption();
                Optional<Country> country = countryRepository.findByCode(data.getLocation());
                if (country.isEmpty()) {
                    alcoholConsumption.setCountry(countryRepository.save(new Country(null, data.getLocation())));
                } else {
                    alcoholConsumption.setCountry(country.get());
                }

                Optional<Measure> measure = measureRepository.findByName(data.getMeasure());
                if (measure.isEmpty()) {
                    alcoholConsumption.setMeasure(measureRepository.save(new Measure(null, data.getMeasure())));
                } else {
                    alcoholConsumption.setMeasure(measure.get());
                }

                alcoholConsumption.setTime(data.getTime());
                alcoholConsumption.setValue(data.getValue());
                alcoholConsumption.setSubject(Subject.valueOf(data.getSubject()));
                alcoholConsumptionRepository.save(alcoholConsumption);
            }
        }
        System.out.println("Database initialized!");
    }
}
