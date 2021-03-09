package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Make sure no duplicates are possible yourself, not by the user

// Represents a calendar with a name, a list of schedules, and a list of events
public class Calendar implements Writable {
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

    // getters
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
        for (Schedule i: schedules) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    // REQUIRES: there is an event in events with a name of name
    // EFFECTS: returns the event with name of name
    public Event getEvent(String name) {
        for (Event i: events) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    // REQUIRES: schedule is not already in schedules
    // MODIFIES: this
    // EFFECTS: adds the schedule to the list of this.schedules
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    // REQUIRES: schedule is in schedules
    // MODIFIES: this
    // EFFECTS: removes the schedule from the list of this.schedules
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
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

    // EFFECTS: returns this calendar as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("schedules", schedulesToJson());
        json.put("events", eventsToJson());

        return json;
    }

    // TODO: duplication in activity and calendar
    // EFFECTS: returns events as a JSONArray
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event e:events) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns schedules as a JSONArray
    private JSONArray schedulesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Schedule s:schedules) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
