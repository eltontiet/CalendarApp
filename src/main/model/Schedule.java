package model;

import model.date.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a schedule that has a list of activities, and a name
public class Schedule implements Writable {
    private String name;
    private List<Activity> activities;

    // EFFECTS: The schedule's name is set to name
    //          activities is set to an empty arraylist
    public Schedule(String name) {
        this.name = name;
        activities = new ArrayList<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the activity with name of name, or false
    public Activity getActivity(String name) {
        for (Activity i:activities) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    // REQUIRES: activity is not already in activities
    // MODIFIES: this
    // EFFECTS: adds the activity to the list of this.activities
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    // REQUIRES: activity is in activities
    // MODIFIES: this
    // EFFECTS: removes the activity from the list of this.activities
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // EFFECTS: returns this schedule as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("activities", activitiesToJson());

        return json;
    }

    // EFFECTS: returns activities as a JSONArray
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity a: activities) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
