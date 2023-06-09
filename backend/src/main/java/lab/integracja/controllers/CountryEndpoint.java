package lab.integracja.controllers;

import jakarta.xml.ws.WebServiceException;
import lab.integracja.entities.Country;
import lab.integracja.repositories.CountryRepository;
import lab.integracja.repositories.SuicideRateRepository;
import lab.integracja.services.AlcoholConsumptionService;
import lab.integracja.services.SuicideRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.GetCountryRequest;
import soap.GetCountryResponse;

import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://localhost/integration";

    private final CountryRepository countryRepository;
    private final SuicideRateService suicideRateService;
    private final AlcoholConsumptionService alcoholConsumptionService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        Optional<Country> country = countryRepository.findByCode(request.getName());
        if (country.isEmpty()) {
            throw new WebServiceException("Country not found");
        }

        GetCountryResponse response = new GetCountryResponse();
        soap.Country c = new soap.Country();
        c.setCode(country.get().getCode());

        c.setAvgSuicideRate(suicideRateService.avgSuicideRateForCountry(country.get().getId()));
        c.setAvgAlcoholConsumption(alcoholConsumptionService.avgSuicideRateForCountry(country.get().getId()));

        response.setCountry(c);

        return response;
    }
}
