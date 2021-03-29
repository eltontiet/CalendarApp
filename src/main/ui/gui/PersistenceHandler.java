package ui.gui;

import model.Calendar;
import model.Config;
import org.json.JSONException;
import persistence.CalendarReader;
import persistence.CalendarWriter;
import persistence.ConfigReader;
import persistence.ConfigWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Handles the saving and loading of calendars
public class PersistenceHandler {
    private CalendarReader calendarReader;
    private CalendarWriter calendarWriter;
    private ConfigReader configReader;
    private ConfigWriter configWriter;
    private String jsonStore;
    private Config config;
    private Calendar calendar;

    // EFFECTS: initializes all the fields, and loads the file
    public PersistenceHandler() {
        init();
    }

    // EFFECTS: returns the calendar
    public Calendar getCalendar() {
        return calendar;
    }

    // EFFECTS: returns the calendar
    public Config getConfig() {
        return config;
    }

    // EFFECTS: sets jsonStore
    public void setJsonStore(String fileLocation) {
        jsonStore = fileLocation;
    }

    // MODIFIES: this
    // EFFECTS: initializes calendar
    private void init() {
        initConfig();
        jsonStore = config.getCurrentFile();
        try {
            calendarReader = new CalendarReader(jsonStore);

            calendar = calendarReader.read();

            System.out.println("Started up with Calendar: " + calendar.getName());

        } catch (JSONException | IOException e) {
            System.out.println(jsonStore + " could not be read, trying " + Config.DEFAULT_SAVE_LOCATION);
            jsonStore = Config.DEFAULT_SAVE_LOCATION;

            try {
                calendarReader = new CalendarReader(jsonStore);
                calendar = calendarReader.read();

                System.out.println("Success!");

            } catch (IOException ioe) {
                System.out.println("Could not load files, quitting...");
                System.exit(0);
            }

        } finally {
            calendarWriter = new CalendarWriter(jsonStore);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes config file
    private void initConfig() {
        try {
            configReader = new ConfigReader(Config.CONFIG_FILE_LOCATION);
            config = configReader.read();
        } catch (IOException | JSONException e) {
            System.out.println("Config file could not be read, resetting to default values");
            config = new Config();
            config.setup();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads calendar at fileLocation
    public void load(String fileLocation) throws IOException {
        try {
            calendarReader = new CalendarReader(fileLocation);
            calendar = calendarReader.read();

            jsonStore = fileLocation;

            System.out.println("Successfully loaded calendar: " + calendar.getName());

        } catch (IOException e) {
            calendarReader = new CalendarReader(jsonStore);
            throw new IOException();
        }

        configWriter = new ConfigWriter(Config.CONFIG_FILE_LOCATION);

        config.setCurrentFile(fileLocation);

        try {
            configWriter.open();
            configWriter.write(config);
            configWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not update config file");
        }
    }

    // EFFECTS: saves the calendar to file
    public void saveCalendar() throws FileNotFoundException {
        calendarWriter = new CalendarWriter(jsonStore);
        calendarWriter.open();
        calendarWriter.write(calendar);
        calendarWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: creates a new calendar, and loads it
    public void makeCalendar(Calendar calendar, String jsonStore) throws FileNotFoundException {
        calendarWriter = new CalendarWriter(jsonStore);
        calendarWriter.open();
        calendarWriter.write(calendar);
        calendarWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: saves the calendar to file
    public void saveConfig() throws FileNotFoundException {
        configWriter = new ConfigWriter(Config.CONFIG_FILE_LOCATION);
        configWriter.open();
        configWriter.write(config);
        configWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: deletes the file with value
    public void deleteFile(String value) {
        try {
            config.removeFile(value);
            File file = new File(value);
            file.delete();
            saveConfig();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
