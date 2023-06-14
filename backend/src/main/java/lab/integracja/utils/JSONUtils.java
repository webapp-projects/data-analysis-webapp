package lab.integracja.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.integracja.entities.Country;
import lab.integracja.entities.RawData;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public List<Country> jsonToCountries(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        JSONObject object = new JSONObject(result);
        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < object.getJSONArray("countries").length(); i++) {
            countries.add(new Country(
                    null, object.getJSONArray("countries").getJSONObject(i).getString("code"))
            );
        }

        return countries;
    }

    public void writeRawDataToJson(List<RawData> rawDataList, Writer writer) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            writer.append("{");
            for (int i = 0; i < rawDataList.size() - 1; i++ ) {
                writer.append(objectMapper.writeValueAsString(rawDataList.get(i)));
                writer.append(",\n");
            }
            writer.append(objectMapper.writeValueAsString(rawDataList.get(rawDataList.size()-1)));
            writer.append("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
