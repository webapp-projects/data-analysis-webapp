package lab.integracja.controllers;

public record CountryData(String countryCode,
                          SuicideRateData suicideRateData,
                          AlcoholConsumptionData alcoholConsumptionData) {
}
