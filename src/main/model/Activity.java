package model;

import model.date.Date;
import model.date.Time;

import java.util.ArrayList;
import java.util.List;

// Represents a recurring activity with a name, dates of the activity,
// time, duration, list of notes, and list of events
public class Activity {
    private String name;
    private List<Date> dates;
    private Time time;
    private int duration;
    private List<Note> notes;
    private List<Event> events;

    // EFFECTS: constructs an activity with a name, time, duration,
    // and empty lists for dates, notes and events for this activity
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
        for (Note i:notes) {
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
        for (Event i:events) {
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
}
