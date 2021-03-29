package ui.gui;

import model.*;
import model.Event;
import model.date.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

// Represents an editor. Will overlap the CalendarPanel.
public class EditorPanel extends OrganizationAppPanel implements ActionListener {
    private GraphicalOrganizationApp graphicalOrganizationApp;
    private List<Date> dates;
    private List<Note> notes;
    private State state;

    private JTextField nameField;
    private JTextField scheduleField;

    private enum State {
        NEW_CALENDAR,
        NEW_SCHEDULE,
        NEW_ACTIVITY,
        NEW_EVENT,
        EDIT_ACTIVITY,
        EDIT_EVENT
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields, and creates an invisible layer
    public EditorPanel(GraphicalOrganizationApp graphicalOrganizationApp) {
        super();
        this.graphicalOrganizationApp = graphicalOrganizationApp;
        setVisible(false);
        setOpaque(false);
        setPreferredSize(new Dimension(CalendarPanel.WIDTH,CalendarPanel.HEIGHT));
    }

    // MODIFIES: this
    // EFFECTS: sets ep to visible, and creates the name field and buttons
    public void newCalendar() {
        resetPanel();
        state = State.NEW_CALENDAR;
        addNameField();
        addScheduleField();

        addConfirmButtons();

        showPanel();
    }

    public void newSchedule() {
    }

    public void newActivity() {
    }

    public void newEvent() {
    }

    public void editActivity(Activity activity) {

    }

    public void editEvent(Event event) {

    }

    // MODIFIES: this
    // EFFECTS: adds a name field to type into
    public void addNameField() {
        JPanel namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 100));
        namePanel.setOpaque(false);

        JLabel text = new JLabel("Calendar Name: ");
        nameField = new JTextField();

        nameField.setColumns(50);

        namePanel.add(text);
        namePanel.add(nameField);

        add(namePanel);
    }

    // MODIFIES: this
    // EFFECTS:
    public void addScheduleField() {
        JPanel schedulePanel = new JPanel();
        schedulePanel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 100));
        schedulePanel.setOpaque(false);

        JLabel text = new JLabel("Schedule Name: ");
        scheduleField = new JTextField();

        scheduleField.setColumns(50);

        schedulePanel.add(text);
        schedulePanel.add(scheduleField);
        add(schedulePanel);
    }

    // MODIFIES: this
    // EFFECTS: adds the save and cancel buttons to the
    private void addConfirmButtons() {
        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 50));

        JButton confirm = new JButton("Confirm");
        JButton cancel = new JButton("Cancel");

        confirm.addActionListener(e -> processInputs());
        cancel.addActionListener(e -> cancelEdit());

        panel.add(cancel, BorderLayout.WEST);
        panel.add(confirm, BorderLayout.EAST);

        add(panel);

    }

    // MODIFIES: this
    // EFFECTS: processes the input, and hides the panel
    private void processInputs() {
        switch (state) {
            case NEW_CALENDAR:
                makeCalendar();
            case NEW_SCHEDULE:
                makeSchedule();
        }
    }

    // MODIFIES: this, graphicalOrganizationApp.getCalendar()
    // EFFECTS: adds schedule to calendar
    private void makeSchedule() {
        String scheduleName = nameField.getText();
        Schedule schedule = new Schedule(scheduleName);

        graphicalOrganizationApp.getCalendar().addSchedule(schedule);

        graphicalOrganizationApp.getCalendarPanel().setVisible(true);
        resetPanel();
    }

    // MODIFIES: this
    // EFFECTS: saves current calendar, and
    //          creates an empty calendar based on inputs
    private void makeCalendar() {
        PersistenceHandler persistenceHandler = graphicalOrganizationApp.getPersistenceHandler();

        String calendarName = nameField.getText();
        String scheduleName = scheduleField.getText();
        String jsonStore = "./data/" + calendarName + ".json";
        Config config =  persistenceHandler.getConfig();

        Calendar calendar = new Calendar(calendarName);
        calendar.addSchedule(new Schedule(scheduleName));

        try {
            persistenceHandler.saveCalendar();
            persistenceHandler.makeCalendar(calendar,jsonStore);

            config.addFile(calendarName, jsonStore);

            persistenceHandler.saveConfig();

            graphicalOrganizationApp.getOptionsPanel().load(jsonStore);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this, graphicalOrganizationApp.getCalendarPanel()
    // EFFECTS: sets the panel as visible, and refreshes the app
    private void showPanel() {
        graphicalOrganizationApp.getCalendarPanel().setVisible(false);
        setVisible(true);
        reload();
    }

    // MODIFIES: this, graphicalOrganizationApp.getCalendarPanel()
    // EFFECTS: clears the panel, sets it as invisible, and
    //          refreshes the calendar
    private void resetPanel() {
        graphicalOrganizationApp.getCalendarPanel().setVisible(true);
        removeAll();
        setVisible(false);
        reload();
    }

    // MODIFIES: this, op
    // EFFECTS: resets the panel, and resets the options menu
    private void cancelEdit() {
        OptionsPanel op = graphicalOrganizationApp.getOptionsPanel();
        op.reset();
        resetPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
