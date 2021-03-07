package persistence;

import model.Calendar;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Based off of the JsonSerializationDemo
// Represents a writer that writes the json representation of Calendar to a file
public class CalendarWriter {
    private static final int INDENT = 4;

    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer to write to destination
    public CalendarWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    //          cannot be accessed
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: saves calendar to destination file as json representation
    public void write(Calendar c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes json into file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
