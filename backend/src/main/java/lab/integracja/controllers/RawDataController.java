package lab.integracja.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lab.integracja.repositories.RawDataRepository;
import lab.integracja.utils.CsvUtils;
import lab.integracja.utils.XMLFileGenerator;
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
    private final XMLFileGenerator xmlFileGenerator;

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
        xmlFileGenerator.writeRawDataToXML(rawDataRepository.findAll().subList(0, 100), response.getWriter());
    }
}
