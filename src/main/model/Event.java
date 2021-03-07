package model;

import model.date.Date;
import model.date.Time;
import org.json.JSONObject;
import persistence.Writable;

// Represents an Event with its name, date, time, and duration
public class Event implements Writable {
    private String name;
    private Date date;
    private Time time;
    private int duration;

    // EFFECTS: constructs an event with name, date, time, and duration in minutes
    //          a duration of 0 means that there is no duration. Ex. a due date
    public Event(String name, Date date, Time time, int duration) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    // getters
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

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // MODIFIES: this
    // EFFECTS: sets this event to be equal to new event
    public void setEvent(Event newEvent) {
        this.name = newEvent.getName();
        this.date = newEvent.getDate();
        this.time = newEvent.getTime();
        this.duration = newEvent.getDuration();
    }

    // EFFECTS: returns this event into a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("date", date.getDate());
        json.put("time", time.get24HTime());
        json.put("duration", duration);

        return json;
    }
}
