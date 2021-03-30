package model;

import exceptions.CalendarNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

// Represents a config file with a path to the current file, and a map of calendar names, and file locations
// The first string in files is the calendar name, and the second string is the file location
public class Config {
    public static final String DEFAULT_SAVE_LOCATION = "./data/Default Calendar.json";
    public static final String CONFIG_FILE_LOCATION = "./data/cfg.json";

    String currentFile;
    Map<String, String> files;

    // EFFECTS: initializes a new hashmap for files with the default file location in it.
    public Config() {
        currentFile = DEFAULT_SAVE_LOCATION;
        files = new HashMap<>();
    }

    // REQUIRES: fileLocation exists in files
    // EFFECTS: sets currentFile to fileLocation, throws FileNotFoundException if
    //          fileLocation is not in files
    public void setCurrentFile(String fileLocation) {
        this.currentFile = fileLocation;
    }

    // getter
    public String getCurrentFile() {
        return currentFile;
    }

    // getter
    public Map<String, String> getFiles() {
        return files;
    }

    // MODIFIES: this
    // EFFECTS: adds the calendar name and file location to files
    public void addFile(String name, String fileLocation) {
        files.put(name, fileLocation);
    }


    // EFFECTS: returns name of calendar from file location if it exists, otherwise
    //          throws CalendarNotFoundException
    public String getName(String fileLocation) throws CalendarNotFoundException {
        String name = getKey(fileLocation);
        if (name == null) {
            throw new CalendarNotFoundException();
        }

        return name;
    }

    // EFFECTS: gets corresponding key for value fileLocation,
    //          returns null if key does not exist
    private String getKey(String fileLocation) {
        for (Map.Entry<String, String> f:files.entrySet()) {
            if (f.getValue().equals(fileLocation)) {
                return f.getKey();
            }
        }

        return null;
    }

    // EFFECTS: returns file location from calendar name if it exists, otherwise
    //          throws FileNotFoundException
    public String getFileLocation(String name) throws FileNotFoundException {
        String fileLocation = files.get(name);

        if (fileLocation == null) {
            throw new FileNotFoundException();
        }

        return fileLocation;
    }

    // MODIFIES: this
    // EFFECTS: removes a file with calendar name, name, from files if it exists, otherwise
    //          throws CalendarNotFoundException
    public void removeCalendar(String name) throws CalendarNotFoundException {
        if (files.remove(name) == null) {
            throw new CalendarNotFoundException();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a file with file location, fileLocation, from files if it exists, otherwise
    //          throws FileNotFoundException
    public void removeFile(String fileLocation) throws FileNotFoundException {
        if (files.remove(getKey(fileLocation)) == null) {
            throw new FileNotFoundException();
        }
    }

    // EFFECTS: returns the json representation of Config
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("currentFile", currentFile);
        jsonObject.put("files",filesToJson());

        return jsonObject;
    }

    // EFFECTS: returns the json representation of files
    private Object filesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Map.Entry<String,String> f: files.entrySet()) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", f.getKey());
            jsonObject.put("fileLocation", f.getValue());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: creates a default calendar in files
    public void setup() {
        files.put("Default Calendar", DEFAULT_SAVE_LOCATION);
    }
}
