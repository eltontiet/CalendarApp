package persistence;

import model.Config;
import org.json.JSONObject;

// Based off of the JsonSerializationDemo
// Represents a writer for Config that writes the json representation of Config to file
public class ConfigWriter extends JsonWriter {

    // EFFECTS: constructs a writer to write to destination
    public ConfigWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: saves json representation of config to destination
    public void write(Config c) {
        JSONObject jsonObject = c.toJson();
        saveToFile(jsonObject.toString(TAB));
    }
}
