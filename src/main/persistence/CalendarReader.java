package persistence;

import exceptions.DateException;
import exceptions.DateFormatException;
import exceptions.BadTimeFormattingException;
import model.*;
import model.date.Date;
import model.date.Time;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Based off of the JsonSerializationDemo
// Represents a reader that reads Calendar from JSON data stored in a file
public class CalendarReader extends JsonReader {

    // EFFECTS: constructs a reader to read from a source file
    public CalendarReader(String source) {
        super(source);
    }

    // EFFECTS: reads calendar from file and returns it;
    //          throws IOException if an error occurs while reading from file
    public Calendar read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalendar(jsonObject);
    }

    // EFFECTS: parses calendar from JSON object and returns it
    private Calendar parseCalendar(JSONObject jsonObject) {
        String name = jsonObject.getString("name");

        Calendar calendar = new Calendar(name);
        addSchedules(calendar, jsonObject);
        addEvents(calendar, jsonObject);

        return calendar;
    }

    // MODIFIES: calendar
    // EFFECTS: parses schedules from JSONObject and adds them to calendar
    private void addSchedules(Calendar calendar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("schedules");
        for (Object json: jsonArray) {
            JSONObject nextSchedule = (JSONObject) json;
            addSchedule(calendar, nextSchedule);
        }
    }

    // MODIFIES: calendar
    // EFFECTS: parses schedule from JSONObject and adds it to calendar
    private void addSchedule(Calendar calendar, JSONObject jsonObject) {
        String name = jsonObject.getString("name");

        Schedule schedule = new Schedule(name);
        addActivities(schedule, jsonObject);

        calendar.addSchedule(schedule);
    }

    // MODIFIES: schedule
    // EFFECTS: parses activities from JSONObject and adds them to schedule
    private void addActivities(Schedule schedule, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json: jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            addActivity(schedule, nextActivity);
        }
    }

    // MODIFIES: schedule
    // EFFECTS: parses activity from JSONObject and adds it to schedule
    private void addActivity(Schedule schedule, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Time time = getTime(jsonObject);

        int duration = jsonObject.getInt("duration");

        Activity activity = new Activity(name, time, duration);
        addDates(activity, jsonObject);
        addNotes(activity, jsonObject);
        addEvents(activity, jsonObject);

        schedule.addActivity(activity);
    }

    // TODO: move location of print?
    // EFFECTS: parses time from JSONObject and returns it
    private Time getTime(JSONObject jsonObject) {
        String timeString = jsonObject.getString("time");
        try {
            return new Time(timeString);
        } catch (BadTimeFormattingException bfe) {
            bfe.printStackTrace();
            System.out.println("Error reading Time object in: " + jsonObject);
            System.out.println("Setting time to 00:00");
            return new Time(0,0);
        }
    }

    // MODIFIES: activity
    // EFFECTS: parses dates from JSONObject and adds it to activity
    private void addDates(Activity activity, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("dates");
        for (Object json: jsonArray) {
            JSONObject nextDate = (JSONObject) json;
            addDate(activity, nextDate);
        }
    }

    // MODIFIES: activity
    // EFFECTS: parses date from JSONObject and adds it to activity
    private void addDate(Activity activity, JSONObject jsonObject) {
        Date date = getDate(jsonObject);

        activity.addDate(date);
    }

    // TODO: move location of print?
    // EFFECTS: parses date from JSONObject and returns it
    private Date getDate(JSONObject jsonObject) {
        String dateString = jsonObject.getString("date");
        try {
            return new Date(dateString);
        } catch (DateException e) {
            e.printStackTrace();
            System.out.println("Could not read date at object: " + jsonObject);
            System.out.println("Setting date to epoch time: January 1 1970");

            return new Date(1970, 1, 1);
        }
    }

    // MODIFIES: activity
    // EFFECTS: parses notes from JSONObject and adds it to activity
    private void addNotes(Activity activity, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json: jsonArray) {
            JSONObject nextNote = (JSONObject) json;
            addNote(activity, nextNote);
        }
    }

    // MODIFIES: activity
    // EFFECTS: parses note from JSONObject and adds it to activity
    private void addNote(Activity activity, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String body = jsonObject.getString("body");

        activity.addNote(new Note(title, body));
    }

    // MODIFIES: calendar
    // EFFECTS: parses events from JSONObject and adds it to calendar
    private void addEvents(Calendar calendar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json: jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(calendar, nextEvent);
        }
    }

    // MODIFIES: activity
    // EFFECTS: parses events from JSONObject and adds it to activity
    private void addEvents(Activity activity, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json: jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(activity, nextEvent);
        }
    }

    // MODIFIES: calendar
    // EFFECTS: parses event from JSONObject and adds it to calendar
    private void addEvent(Calendar calendar, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Date date = getDate(jsonObject);
        Time time = getTime(jsonObject);
        int duration = jsonObject.getInt("duration");


        calendar.addEvent(new Event(name, date, time, duration));
    }

    // MODIFIES: activity
    // EFFECTS: parses event from JSONObject and adds it to activity
    private void addEvent(Activity activity, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Date date = getDate(jsonObject);
        Time time = getTime(jsonObject);
        int duration = jsonObject.getInt("duration");

        activity.addEvent(new Event(name, date, time, duration));
    }


}
