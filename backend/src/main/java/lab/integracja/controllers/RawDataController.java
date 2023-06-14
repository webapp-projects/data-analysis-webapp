package lab.integracja.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lab.integracja.repositories.RawDataRepository;
import lab.integracja.utils.CsvUtils;
import lab.integracja.utils.JSONUtils;
import lab.integracja.utils.XMLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/raw-data")
public class RawDataController {

    private final RawDataRepository rawDataRepository;
    private final CsvUtils csvUtils;
    private final XMLUtils xmlUtils;
    private final JSONUtils jsonUtils;

    @GetMapping("/csv")
    public void exportIntoCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"raw_data.csv\"");
        csvUtils.writeRawDataToCsv(rawDataRepository.findAll().subList(0, 100), response.getWriter());
    }

    @GetMapping("/xml")
    public void exportToXML(HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");
        response.addHeader("Content-Disposition", "attachment; filename=\"raw_data.xml\"");
        xmlUtils.writeRawDataToXML(rawDataRepository.findAll().subList(0, 100), response.getWriter());
    }

    @GetMapping("/json")
    public void exportToJson(HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        response.addHeader("Content-Disposition", "attachment; filename=\"raw_data.json\"");
        jsonUtils.writeRawDataToJson(rawDataRepository.findAll(), response.getWriter());
    }
}
