package persistence;

import model.Calendar;
import org.json.JSONObject;

// Based off of the JsonSerializationDemo
// Represents a writer that writes the json representation of Calendar to a file
public class CalendarWriter extends JsonWriter {

    // EFFECTS: constructs a writer to write to destination
    public CalendarWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: saves calendar to destination file as json representation
    public void write(Calendar c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(TAB));
    }
}
