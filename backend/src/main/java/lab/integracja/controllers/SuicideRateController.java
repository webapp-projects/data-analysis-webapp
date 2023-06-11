package lab.integracja.controllers;

import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import lab.integracja.services.CountryService;
import lab.integracja.services.SuicideRateService;
import lab.integracja.utils.EnumUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/suicide")
@RequiredArgsConstructor
public class SuicideRateController {

    private final SuicideRateService suicideRateService;
    private final CountryService countryService;

    @GetMapping("/year/{year}/subject/{subject}")
    public ResponseEntity<List<SuicideRate>> getByYearAndSubject(@PathVariable Integer year,
                                                                 @PathVariable String subject) {
        return ResponseEntity.ok(suicideRateService.getBySubjectAndYear(year, Subject.valueOf(subject)));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<List<SuicideRate>> getByCountry(@PathVariable String countryCode,
                                                          @RequestParam(defaultValue = "TOT") String subject) {

        if (countryService.isValidCountryCode(countryCode) && EnumUtils.isStringInEnum(subject, Subject.class))
            return ResponseEntity.ok(suicideRateService.getByCountryAndSubject(countryCode, Subject.valueOf(subject)));

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid country or subject");
    }

}
