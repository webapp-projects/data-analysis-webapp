package lab.integracja.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lab.integracja.entities.Country;
import lab.integracja.services.CountryService;
import lab.integracja.utils.CsvUtils;
import lab.integracja.utils.JSONUtils;
import lab.integracja.utils.XMLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;
    private final CsvUtils csvUtils;
    private final XMLUtils xmlUtils;
    private final JSONUtils jsonUtils;

    @GetMapping("")
    public ResponseEntity<List<Country>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @GetMapping("/csv")
    public void exportIntoCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"countries.csv\"");
        csvUtils.writeCountriesToCsv(countryService.getAll(), response.getWriter());
    }

    @GetMapping("/xml")
    public void exportToXML(HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");
        response.addHeader("Content-Disposition", "attachment; filename=\"countries.xml\"");
        xmlUtils.writeCountriesToXML(countryService.getAll(), response.getWriter());
    }

    @GetMapping("/json")
    public void exportToJson(HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        response.addHeader("Content-Disposition", "attachment; filename=\"countries.json\"");
        jsonUtils.writeCountriesToJson(countryService.getAll(), response.getWriter());
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (csvUtils.hasCSVFormat(file)) {
            try {
                countryService.saveCsvToDatabase(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PostMapping("/upload-xml")
    public ResponseEntity<String> uploadXMLFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (xmlUtils.hasXMLFormat(file)) {
            try {
                countryService.saveXMLToDatabase(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload a XML file!";
        System.out.println(file.getContentType());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PostMapping("/upload-json")
    public ResponseEntity<String> uploadJSONFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (jsonUtils.hasJsonFormat(file)) {
            try {
                countryService.saveJsonToDatabase(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload a JSON file!";
        System.out.println(file.getContentType());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
