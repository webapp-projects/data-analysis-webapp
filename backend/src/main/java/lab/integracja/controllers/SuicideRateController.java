package lab.integracja.controllers;

import lab.integracja.entities.Subject;
import lab.integracja.entities.SuicideRate;
import lab.integracja.services.SuicideRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/suicide")
@RequiredArgsConstructor
public class SuicideRateController {

    private final SuicideRateService suicideRateService;

    @GetMapping("/year/{year}/subject/{subject}")
    public ResponseEntity<List<SuicideRate>> getByYearAndSubject(@PathVariable Integer year,
                                                                 @PathVariable String subject) {
        return ResponseEntity.ok(suicideRateService.getBySubjectAndYear(year, Subject.valueOf(subject)));
    }


}
