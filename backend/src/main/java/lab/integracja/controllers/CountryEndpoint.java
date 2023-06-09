package lab.integracja.controllers;

import lab.integracja.entities.Country;
import lab.integracja.repositories.CountryRepository;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        Optional<Country> country = countryRepository.findByCode(request.getName());
        if (country.isEmpty()) {
            return null;
        }
        soap.Country c = new soap.Country();
        c.setCode(country.get().getCode());
        response.setCountry(c);

        return response;
    }
}
