package ui.gui;

import model.date.Date;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;

// Represents a panel for the OrganizationApp
public abstract class OrganizationAppPanel extends JPanel {
    public static final Color BACKGROUNDCOLOUR = Color.WHITE;
    public static final boolean OPAQUE = true;

    // EFFECTS: initializes the panel with the set background colour
    public OrganizationAppPanel() {
        setBackground(BACKGROUNDCOLOUR);
        setOpaque(OPAQUE);
    }

    // MODIFIES: this
    // EFFECTS: adds the new information to the panel, and reloads it
    protected void reload(Component component) {
        add(component);
        validate();
        repaint();
    }

    // EFFECTS: reloads the panel
    protected void reload() {
        validate();
        repaint();
    }

    // Used https://stackoverflow.com/questions/9474121/i-want-to-get-year-month-day-etc-from-java-date-to-compare-with-gregorian-cal/32363174#32363174
    // to understand Date and LocalDate
    // EFFECTS: sets today to the system's current date, and prints the date
    public Date getToday() {
        java.util.Date todayDate = java.util.Calendar.getInstance().getTime();
        java.time.LocalDate todayLocalDate = todayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = todayLocalDate.getYear();
        int month = todayLocalDate.getMonthValue();
        int day = todayLocalDate.getDayOfMonth();
        return new Date(year,month,day);
    }
}
