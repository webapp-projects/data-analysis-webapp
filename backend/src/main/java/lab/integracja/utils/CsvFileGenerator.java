package lab.integracja.utils;

import lab.integracja.entities.Country;
import lab.integracja.entities.RawData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvFileGenerator {
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
}
