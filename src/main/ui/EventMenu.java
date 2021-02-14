package ui;

import model.Calendar;
import model.Event;

// Edit events, add or remove them from an activity or calendar
public class EventMenu {
    private Calendar calendar;

    // EFFECTS: Initializes calendar and event menu
    public EventMenu(Calendar calendar) {
        this.calendar = calendar;
        System.out.println("WORK");
    }
}
