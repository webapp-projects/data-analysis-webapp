package lab.integracja.controllers;

import lab.integracja.entities.*;

public record SuicideRateData(String subj,
                              String measure,
                              Integer year,
                              Float value) {
    public SuicideRate toEntity(Country country) {
        return new SuicideRate(
                null,
                country,
                Subject.valueOf(subj),
                new Measure(null, measure),
                year,
                value
        );
    }

    public SuicideRate toEntity(Country country, Measure measure) {
        return new SuicideRate(
                null,
                country,
                Subject.valueOf(subj),
                measure,
                year,
                value
        );
    }
}
