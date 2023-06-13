package lab.integracja.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.integracja.entities.Country;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class JSONUtils {
    public static String TYPE = "application/json";

    public boolean hasJsonFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public void writeCountriesToJson(List<Country> countries, Writer writer) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            writer.append("{");
            for (int i = 0; i < countries.size() - 1; i++ ) {
                writer.append(objectMapper.writeValueAsString(countries.get(i)));
                writer.append(",");
            }
            writer.append(objectMapper.writeValueAsString(countries.get(countries.size()-1)));
            writer.append("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
