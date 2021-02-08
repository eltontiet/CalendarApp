package model;

import java.util.ArrayList;
import java.util.List;

// Represents a calendar with a name, a list of schedules, and a list of events
public class Calendar {
    private String name;
    private List<Schedule> schedules;
    private List<Event> events;

    // EFFECTS: The schedule's name is set to name
    //          activities is set to an empty arraylist
    public Calendar(String name) {
        this.name = name;
        schedules = new ArrayList<>();
        events = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<Event> getEvents() {
        return events;
    }

    // REQUIRES: there is a schedule with a name of name
    // EFFECTS: returns the schedule with name of name
    public Schedule getSchedule(String name) {
        return null;
    }

    // REQUIRES: there is an event in events with a name of name
    // EFFECTS: returns the event with name of name
    public Event getEvent(String name) {
        return null;
    }

    // REQUIRES: schedule is not already in schedules
    // MODIFIES: this
    // EFFECTS: adds the schedule to the list of this.schedules
    public void addSchedule(Schedule schedule) {

    }

    // REQUIRES: schedule is in schedules
    // MODIFIES: this
    // EFFECTS: removes the schedule from the list of this.schedules
    public void removeSchedule(Schedule schedule) {

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
