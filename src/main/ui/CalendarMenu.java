package ui;

import model.Calendar;

// Change the name of the calendar
public class CalendarMenu extends Menu {
    Calendar calendar;

    // EFFECTS: Initializes calendar and menu
    public CalendarMenu(Calendar calendar) {
        super(calendar);
        this.calendar = calendar;
    }

    @Override
    protected void removeItem() {

    }

    @Override
    protected void editItem() {

    }

    @Override
    protected void makeItem() {

    }
}
