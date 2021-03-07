package model;

import model.date.Date;
import model.date.Time;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a recurring activity with a name, dates of the activity,
// time, duration, list of notes, and list of events
public class Activity implements Writable {
    private String name;
    private List<Date> dates;
    private Time time;
    private int duration;
    private List<Note> notes;
    private List<Event> events;

    // EFFECTS: constructs an activity with a name, time, duration,
    // and empty lists for dates, notes and events for this activity
    // If the duration is 0, there is no time for the activity
    public Activity(String name, Time time, int duration) {
        this.name = name;
        this.time = time;
        this.duration = duration;
        dates = new ArrayList<>();
        notes = new ArrayList<>();
        events = new ArrayList<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public List<Date> getDates() {
        return dates;
    }

    public int getDuration() {
        return duration;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Event> getEvents() {
        return events;
    }

    // EFFECTS: returns the note with title of title, or null
    public Note getNote(String title) {
        for (Note i : notes) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    // EFFECTS: returns the event with name of name, or null
    public Event getEvent(String name) {
        for (Event i : events) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    // REQUIRES: date is not already in dates
    // MODIFIES: this
    // EFFECTS: adds the date to the list of this.dates
    public void addDate(Date date) {
        dates.add(date);
    }

    // REQUIRES: date is in dates
    // MODIFIES: this
    // EFFECTS: removes the date from the list of this.dates
    public void removeDate(Date date) {
        dates.remove(date);
    }

    // REQUIRES: note does not have the same title as an element in the list
    // MODIFIES: this
    // EFFECTS: adds the note to the list of this.notes
    public void addNote(Note note) {
        notes.add(note);
    }

    // REQUIRES: note is in notes
    // MODIFIES: this
    // EFFECTS: removes the note from the list of this.notes
    public void removeNote(Note note) {
        notes.remove(note);
    }

    // REQUIRES: event does not have the same name as one of the events in the list
    // MODIFIES: this
    // EFFECTS: adds the event to the list of this.events
    public void addEvent(Event event) {
        events.add(event);
    }

    // REQUIRES: event is in events
    // MODIFIES: this
    // EFFECTS: removes the event from the list of this.events
    public void removeEvent(Event event) {
        events.remove(event);
    }

    // MODIFIES: this
    // EFFECTS: sets this activity to be the same as newActivity
    public void setActivity(Activity newActivity) {
        this.name = newActivity.getName();
        this.dates = duplicateDates(newActivity);
        this.time = new Time(newActivity.getTime().getHour(), newActivity.getTime().getMinute());
        this.duration = newActivity.getDuration();
        this.notes = duplicateNotes(newActivity);
        this.events = duplicateEvents(newActivity);
    }

    // EFFECTS: returns an identical list of dates
    private List<Date> duplicateDates(Activity newActivity) {
        List<Date> newDates = new ArrayList<>();
        Date newDate;

        for (Date d : newActivity.getDates()) {
            newDate = new Date(d.getYear(), d.getMonth(), d.getDay());
            newDates.add(newDate);
        }
        return newDates;
    }

    // EFFECTS: returns an identical list of notes
    private List<Note> duplicateNotes(Activity newActivity) {
        List<Note> newDates = new ArrayList<>();
        Note newNote;

        for (Note n : newActivity.getNotes()) {
            newNote = new Note(n.getTitle(), n.getBody());
            newDates.add(newNote);
        }
        return newDates;
    }

    // EFFECTS: returns an identical list of events
    private List<Event> duplicateEvents(Activity newActivity) {
        List<Event> newEvents = new ArrayList<>();
        Event newEvent;

        for (Event e : newActivity.getEvents()) {
            newEvent = new Event(e.getName(), e.getDate(), e.getTime(), e.getDuration());
            newEvents.add(newEvent);
        }
        return newEvents;
    }

//    private String name;
//    private List<Date> dates;
//    private Time time;
//    private int duration;
//    private List<Note> notes;
//    private List<Event> events;

    // EFFECTS: returns this calendar as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("dates", datesToJson());
        json.put("time", time.get24HTime());
        json.put("duration", duration);
        json.put("notes", notesToJson());
        json.put("events", eventsToJson());

        return json;
    }

    // EFFECTS: returns events as a JSONArray
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event e : events) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns notes as a JSONArray
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Note n : notes) {
            jsonArray.put(n.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns dates as a JSONArray
    private JSONArray datesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Date d : dates) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}
