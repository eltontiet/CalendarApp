package persistence;

import exceptions.CalendarNotFoundException;
import model.Config;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

// Based off of the JsonSerializationDemo
// Represents a reader that reads Config from JSON data stored in a file
public class ConfigReader extends JsonReader {

    // EFFECTS: constructs a reader to read from a source file
    public ConfigReader(String source) {
        super(source);
    }

    // EFFECTS: reads config from file and returns it;
    //          throws IOException if an error occurs while reading from file
    public Config read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseConfig(jsonObject);
    }


    // EFFECTS: parses config from json data and returns it
    private Config parseConfig(JSONObject jsonObject) {
        String currentFile = jsonObject.getString("currentFile");

        Config c = new Config();

        c.setCurrentFile(currentFile);
        addFiles(c, jsonObject);

        return c;
    }

    // EFFECTS: parses files from jsonObject, and puts it into c
    private void addFiles(Config c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("files");
        for (Object json: jsonArray) {
            JSONObject file = (JSONObject) json;

            c.addFile(file.getString("name"), file.getString("fileLocation"));
        }
    }
}
