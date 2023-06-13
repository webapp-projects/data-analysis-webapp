package lab.integracja.utils;

import lab.integracja.entities.Country;
import lab.integracja.entities.RawData;
import org.apache.catalina.util.XMLWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class XMLFileGenerator {

    public void writeCountriesToXML(List<Country> countries, Writer writer) {
        XMLWriter printer = new XMLWriter(writer);
        printer.writeXMLHeader();
        for (Country country : countries) {
            printer.writeElement("", "country", XMLWriter.OPENING);
            printer.writeProperty("", "id", country.getId().toString());
            printer.writeProperty("", "code", country.getCode());
            printer.writeElement("", "country", XMLWriter.CLOSING);
        }
        try {
            printer.sendData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRawDataToXML(List<RawData> rawDataList, Writer writer) {
        XMLWriter printer = new XMLWriter(writer);
        printer.writeXMLHeader();
        for (RawData rawData : rawDataList) {
            printer.writeElement("", "raw-data", XMLWriter.OPENING);
            printer.writeProperty("", "year", rawData.getTime().toString());
            printer.writeProperty("", "indicator", rawData.getIndicator());
            printer.writeProperty("", "value", rawData.getValue().toString());
            printer.writeProperty("", "subject", rawData.getSubject());
            printer.writeElement("", "raw-data", XMLWriter.CLOSING);
        }
        try {
            printer.sendData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
