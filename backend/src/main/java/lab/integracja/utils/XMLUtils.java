package lab.integracja.utils;

import lab.integracja.entities.Country;
import lab.integracja.entities.RawData;
import org.apache.catalina.util.XMLWriter;
import org.aspectj.weaver.loadtime.definition.LightXMLParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class XMLUtils {
    public static final Set<String> TYPES = Set.of("text/xml", "application/xml");

    public boolean hasXMLFormat(MultipartFile file) {
        return TYPES.contains(file.getContentType());
    }

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

    public List<Country> xmlToCountries(InputStream is) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(fileReader);

            List<Country> countries = new ArrayList<>();

            while (reader.hasNext()) {
                Country country = new Country();
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("code")) {
                        nextEvent = reader.nextEvent();
                        country.setCode(nextEvent.asCharacters().getData());
                        countries.add(country);
                    }
                }
            }

            return countries;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
