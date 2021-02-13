package model;

import model.date.Date;
import model.date.Time;

// Represents an Event with its name, date, time, and duration
public class Event {
    private String name;
    private Date date;
    private Time time;
    private int duration;

    // EFFECTS: constructs an event with name, date, time, and duration in minutes
    public Event(String name, Date date, Time time, int duration) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the event
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: changes the date of the event
    public void setDate(Date date) {
        this.date = date;
    }

    // MODIFIES: this
    // EFFECTS: changes the time of the event
    public void setTime(Time time) {
        this.time = time;
    }

    // MODIFIES: this
    // EFFECTS: changes the duration of the event
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
