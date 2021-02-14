package ui;

import model.Activity;
import model.Calendar;

// Change current activities, add or remove new activities to the calendar
public class ActivityMenu {
    Calendar calendar;

    // EFFECTS: Initializes calendar and activity menu
    public ActivityMenu(Calendar calendar) {
        this.calendar = calendar;
        System.out.println("WORK");
    }
}
