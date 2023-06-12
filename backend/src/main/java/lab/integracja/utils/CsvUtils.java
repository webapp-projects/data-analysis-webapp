package lab.integracja.utils;

import lab.integracja.entities.Country;
import lab.integracja.entities.RawData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvUtils {
    public static String TYPE = "text/csv";

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public void writeCountriesToCsv(List<Country> countries, Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            for (Country country : countries) {
                printer.printRecord(country.getId(), country.getCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRawDataToCsv(List<RawData> rawDataList, Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            for (RawData rawData : rawDataList) {
                printer.printRecord(rawData.getTime(), rawData.getIndicator(), rawData.getValue(), rawData.getSubject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Country> csvToCountries(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Country> countries = new ArrayList<Country>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                countries.add(new Country(Long.parseLong(csvRecord.get("id")), csvRecord.get("code")));
            }

            return countries;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
