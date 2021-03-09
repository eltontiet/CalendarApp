package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Based off of the JsonSerializationDemo
// Represents a writer that writes json data to file
public abstract class JsonWriter {

    protected static final int TAB = 4;

    protected PrintWriter writer;
    protected String destination;

    // EFFECTS: constructs a writer to write to destination
    protected JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if file
    //          cannot be written to
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes json into file
    protected void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer;
    public void close() {
        writer.close();
    }
}
