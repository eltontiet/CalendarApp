package ui.gui;

import model.Calendar;

import java.awt.*;

// Represents an editor. Will overlap the CalendarPanel.
public class EditorPanel extends OrganizationAppPanel {
    private Calendar calendar;

    public EditorPanel(Calendar calendar) {
        super();
        this.calendar = calendar;
        setVisible(false);
        setPreferredSize(new Dimension(CalendarPanel.WIDTH,CalendarPanel.HEIGHT));
    }

    public void newCalendar() {
    }

    public void newSchedule() {
    }

    public void newActivity() {
    }

    public void newEvent() {
    }
}
