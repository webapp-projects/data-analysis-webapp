package lab.integracja.controllers;

import lab.integracja.entities.AlcoholConsumption;
import lab.integracja.entities.Country;
import lab.integracja.entities.Measure;
import lab.integracja.entities.Subject;

public record AlcoholConsumptionData(String subj,
                                     String measure,
                                     Integer year,
                                     Float value) {
    public AlcoholConsumption toEntity(Country country) {
        return new AlcoholConsumption(
                null,
                country,
                Subject.valueOf(subj),
                new Measure(null, measure),
                year,
                value
        );
    }

    public AlcoholConsumption toEntity(Country country, Measure measure) {
        return new AlcoholConsumption(
                null,
                country,
                Subject.valueOf(subj),
                measure,
                year,
                value
        );
    }
}
