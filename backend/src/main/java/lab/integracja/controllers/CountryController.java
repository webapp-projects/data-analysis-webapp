package lab.integracja.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lab.integracja.services.CountryService;
import lab.integracja.utils.CsvFileGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;
    private final CsvFileGenerator csvFileGenerator;

    @GetMapping("/csv")
    public void exportIntoCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"countries.csv\"");
        csvFileGenerator.writeCountriesToCsv(countryService.getAll(), response.getWriter());
    }
}
