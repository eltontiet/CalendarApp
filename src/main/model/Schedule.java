package model;

import model.date.Date;

import java.util.ArrayList;
import java.util.List;

// Represents a schedule that has a list of activities, and a name
public class Schedule {
    private String name;
    private List<Activity> activities;

    // EFFECTS: The schedule's name is set to name
    //          activities is set to an empty arraylist
    public Schedule(String name) {
        this.name = name;
        activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    // EFFECTS: returns the activity with name of name, or false
    public Activity getActivity(String name) {
        for (Activity i:activities) {
            if (i.getName() == name) {
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
}
