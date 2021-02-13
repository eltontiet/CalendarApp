package model;

import model.date.Date;
import model.date.Time;

import java.util.ArrayList;
import java.util.List;

// Represents a recurring activity with a name, dates of the activity,
// time, duration, list of notes, and list of events
public class Activity {
    private final String name;
    private final List<Date> dates;
    private final Time time;
    private final int duration;
    private final List<Note> notes;
    private final List<Event> events;

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

    // REQUIRES: there is a note in notes with a title of title
    // EFFECTS: returns the note with title of title
    public Note getNote(String title) {
        return null;
    }

    // REQUIRES: there is an event in events with a name of name
    // EFFECTS: returns the event with name of name
    public Event getEvent(String name) {
        return null;
    }

    public void setName(String name) {

    }

    public void setDuration(int duration) {

    }

    public void setTime(Time time) {

    }

    // REQUIRES: date is not already in dates
    // MODIFIES: this
    // EFFECTS: adds the date to the list of this.dates
    public void addDate(Date date) {

    }

    // REQUIRES: date is in dates
    // MODIFIES: this
    // EFFECTS: removes the date from the list of this.dates
    public void removeDate(Date date) {

    }

    // REQUIRES: note does not have the same title as an element in the list
    // MODIFIES: this
    // EFFECTS: adds the note to the list of this.notes
    public void addNote(Note note) {

    }

    // REQUIRES: note is in notes
    // MODIFIES: this
    // EFFECTS: removes the note from the list of this.notes
    public void removeNote(Note note) {

    }

    // REQUIRES: event does not have the same name as one of the events in the list
    // MODIFIES: this
    // EFFECTS: adds the event to the list of this.events
    public void addEvent(Event event) {

    }

    // REQUIRES: event is in events
    // MODIFIES: this
    // EFFECTS: removes the event from the list of this.events
    public void removeEvent(Event event) {

    }
}
