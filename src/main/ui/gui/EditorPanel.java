package ui.gui;

import model.*;
import model.Event;
import model.date.Date;
import model.date.Time;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Represents an editor. Will overlap the CalendarPanel.
public class EditorPanel extends OrganizationAppPanel {
    private static final int COLUMNS = 50;
    private static final int ROWS = 10;

    private GraphicalOrganizationApp graphicalOrganizationApp;
    private List<Date> dates;
    private List<Note> notes;
    private State state;
    private Schedule selectedSchedule;

    private JPanel messagePanel;

    private JTextField nameField;
    private JTextField scheduleField;
    private JTextField noteTitle;
    private JTextArea noteBody;
    private JTextField durationField;
    private JTextField yearInput;
    private JTextField monthInput;
    private JTextField dayInput;
    private JTextField hourInput;
    private JTextField minuteInput;

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
        addNameField("Calendar");
        addScheduleField();

        addConfirmButtons();

        showPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets ep to visible, and creates the name field
    public void newSchedule() {
        resetPanel();
        state = State.NEW_SCHEDULE;
        addNameField("Schedule");

        addConfirmButtons();

        showPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets ep to visible, and creates the fields for activity and buttons
    private void newActivity() {
        resetPanel();
        state = State.NEW_ACTIVITY;
        addNameField("Activity");

        addTimeField();
        addDurationField();
        addDatesField();

        addNoteField();

        addConfirmButtons();

        showPanel();
    }

    // MODIFIES: this
    // EFFECTS: lists the schedules in calendar, and allows the user
    //          to choose from them, or cancel
    public void chooseSchedule() {
        resetPanel();

        JPanel scheduleChooser = new JPanel();
        scheduleChooser.setLayout(new BoxLayout(scheduleChooser, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Select which schedule you want to add the activity to: ");
        label.setAlignmentX(Panel.CENTER_ALIGNMENT);

        scheduleChooser.add(label, BorderLayout.NORTH);
        scheduleChooser.add(Box.createRigidArea(new Dimension(0, 10)));

        for (Schedule s: graphicalOrganizationApp.getCalendar().getSchedules()) {
            JButton button = new JButton(s.getName());

            button.addActionListener(e -> {
                selectedSchedule = s;
                newActivity();
            });

            button.setAlignmentX(Panel.CENTER_ALIGNMENT);

            scheduleChooser.add(button,BorderLayout.CENTER);
        }

        JButton cancel = makeCancelButton();
        cancel.setAlignmentX(Panel.CENTER_ALIGNMENT);

        scheduleChooser.add(Box.createRigidArea(new Dimension(0, 10)));
        scheduleChooser.add(cancel,BorderLayout.SOUTH);

        add(scheduleChooser,BorderLayout.CENTER);

        showPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets ep to visible, and creates the name field and buttons
    public void newEvent() {
    }

    // MODIFIES: this
    // EFFECTS: sets ep to visible, and creates the name field and buttons
    public void editActivity(Activity activity) {

    }

    // MODIFIES: this
    // EFFECTS: sets ep to visible, and creates the name field and buttons
    public void editEvent(Event event) {

    }

    // MODIFIES: this
    // EFFECTS: adds a name field to type onto this
    public void addNameField(String objectName) {
        JPanel namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 40));
        namePanel.setOpaque(false);

        JLabel text = new JLabel(objectName + " Name: ");
        nameField = new JTextField();

        nameField.setColumns(COLUMNS);

        namePanel.add(text);
        namePanel.add(nameField);

        add(namePanel,BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds a schedule name field onto this
    public void addScheduleField() {
        JPanel schedulePanel = new JPanel();
        schedulePanel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 40));
        schedulePanel.setOpaque(false);

        JLabel text = new JLabel("Schedule Name: ");
        scheduleField = new JTextField();

        scheduleField.setColumns(COLUMNS);

        schedulePanel.add(text);
        schedulePanel.add(scheduleField);
        add(schedulePanel,BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds the save and cancel buttons to the
    private void addConfirmButtons() {
        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 50));

        panel.add(makeCancelButton(), BorderLayout.WEST);
        panel.add(makeConfirmButton(), BorderLayout.EAST);

        add(panel,BorderLayout.CENTER);
    }

    // EFFECTS: returns a button that resets this
    private JButton makeCancelButton() {
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> cancelEdit());
        return cancel;
    }

    // EFFECTS: returns a button that calls processInputs()
    private JButton makeConfirmButton() {
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> processInputs());

        return confirm;
    }

    // MODIFIES: this
    // EFFECTS: adds a field to add multiple dates
    private void addDatesField() {
        JPanel datesPanel = new JPanel();

        datesPanel.add(makeDateField());
        datesPanel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 40));

        JButton add = new JButton("Add Date");
        add.addActionListener(e -> addDate());

        datesPanel.add(add);
        add(datesPanel, BorderLayout.CENTER);

        dates = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: returns a field to add a date
    private JPanel makeDateField() {
        JPanel datePanel = new JPanel();

        JLabel yearLabel = new JLabel("Year:");
        JLabel monthLabel = new JLabel("Month:");
        JLabel dayLabel = new JLabel("Day:");

        yearInput = new JTextField();
        monthInput = new JTextField();
        dayInput = new JTextField();

        yearInput.setColumns(5);
        monthInput.setColumns(3);
        dayInput.setColumns(3);

        datePanel.add(yearLabel);
        datePanel.add(yearInput);
        datePanel.add(monthLabel);
        datePanel.add(monthInput);
        datePanel.add(dayLabel);
        datePanel.add(dayInput);

        return datePanel;
    }

    // MODIFIES: this
    // EFFECTS: adds a field to change duration
    private void addDurationField() {
        JPanel durationPanel = new JPanel();
        durationPanel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 40));
        durationPanel.setOpaque(false);

        JLabel text = new JLabel("Duration (Minutes) : ");
        durationField = new JTextField();

        durationField.setColumns(3);

        durationPanel.add(text);
        durationPanel.add(durationField);

        add(durationPanel,BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds an hour and minute field
    private void addTimeField() {
        JPanel timePanel = new JPanel();
        timePanel.setPreferredSize(new Dimension(CalendarPanel.WIDTH - 50, 40));
        timePanel.setOpaque(false);

        JLabel hourText = new JLabel("Hour: ");
        hourInput = new JTextField();

        hourInput.setColumns(3);

        JLabel minuteText = new JLabel("Minute: ");
        minuteInput = new JTextField();

        minuteInput.setColumns(3);

        timePanel.add(hourText);
        timePanel.add(hourInput);
        timePanel.add(minuteText);
        timePanel.add(minuteInput);

        add(timePanel,BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds a field to add multiple notes
    private void addNoteField() {
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel,BoxLayout.Y_AXIS));

        notesPanel.add(makeTitlePanel());
        notesPanel.add(makeBodyPanel());

        JButton add = new JButton("Add Note");
        add.addActionListener(e -> addNote());
        add.setAlignmentX(Panel.CENTER_ALIGNMENT);

        notesPanel.add(add);
        add(notesPanel, BorderLayout.CENTER);

        add(Box.createRigidArea(new Dimension(0,100)));

        notes = new ArrayList<>();
    }

    // EFFECTS: returns a panel to accept a note body
    private JPanel makeBodyPanel() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setAlignmentX(Panel.CENTER_ALIGNMENT);

        JLabel bodyLabel = new JLabel("Body:");

        noteBody = new JTextArea(ROWS,COLUMNS);
        noteBody.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(noteBody);

        bodyPanel.add(bodyLabel);
        bodyPanel.add(scrollPane);

        return bodyPanel;
    }

    // EFFECTS: returns a panel to accept a note title
    private JPanel makeTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setAlignmentX(Panel.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Title:");

        noteTitle = new JTextField(COLUMNS);

        titlePanel.add(titleLabel);
        titlePanel.add(noteTitle);

        return titlePanel;
    }

    // MODIFIES: this
    // EFFECTS: processes the input, and hides the panel
    private void processInputs() {
        switch (state) {
            case NEW_CALENDAR:
                makeCalendar();
                break;
            case NEW_SCHEDULE:
                makeSchedule();
                break;
            case NEW_ACTIVITY:
                makeActivity();
                break;
        }
    }

    // MODIFIES: this, graphicalOrganizationApp.getCalendar()
    // EFFECTS: adds activity to currentSchedule
    private void makeActivity() {
        if (checkActivity()) {
            return;
        }

        String name = nameField.getText();
        int duration = Integer.parseInt(durationField.getText());
        int hour = Integer.parseInt(hourInput.getText());
        int minute = Integer.parseInt(minuteInput.getText());

        Time time = new Time(hour,minute);

        Activity activity = new Activity(name, time, duration);

        for (Date d: dates) {
            activity.addDate(d);
        }

        for (Note n: notes) {
            activity.addNote(n);
        }

        selectedSchedule.addActivity(activity);

        resetPanel();
        graphicalOrganizationApp.reloadCalendar();
    }

    // MODIFIES: this
    // EFFECTS: returns true and generates an error label
    //          if schedule already exists, else returns false
    private boolean checkActivity() {

        if (selectedSchedule.getActivity(nameField.getText()) != null) {
            broadcast("Activity already exists");
            return true;
        }

        try {
            if (Integer.parseInt(durationField.getText()) < 0) {
                broadcast("Duration must be positive");
                return true;
            }
        } catch (NumberFormatException e) {
            broadcast("Duration must be a whole number");
            return true;
        }

        return checkTime();
    }

    // MODIFIES: this, graphicalOrganizationApp.getCalendar()
    // EFFECTS: adds schedule to calendar
    private void makeSchedule() {
        String scheduleName = nameField.getText();

        if (checkSchedule()) {
            return;
        }

        Schedule schedule = new Schedule(scheduleName);

        graphicalOrganizationApp.getCalendar().addSchedule(schedule);

        graphicalOrganizationApp.getCalendarPanel().setVisible(true);
        resetPanel();
    }

    // MODIFIES: this
    // EFFECTS: returns true and generates an error label
    //          if schedule already exists, else returns false
    private boolean checkSchedule() {
        String scheduleName = nameField.getText();
        Calendar calendar = graphicalOrganizationApp.getCalendar();

        if (calendar.getSchedule(scheduleName) != null) {
            broadcast("Schedule already exists");
            return true;
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds date to the list dates
    private void addDate() {
        if (checkDates()) {
            return;
        }

        int year = Integer.parseInt(yearInput.getText());
        int month = Integer.parseInt(monthInput.getText());
        int day = Integer.parseInt(dayInput.getText());

        dates.add(new Date(year, month, day));

        broadcast("Date successfully added!");
    }

    // EFFECTS: returns true and broadcasts a message if date already exists
    //          or if there is a formatting error, otherwise return false
    private boolean checkDates() {
        if (checkDate()) {
            return true;
        }

        int year = Integer.parseInt(yearInput.getText());
        int month = Integer.parseInt(monthInput.getText());
        int day = Integer.parseInt(dayInput.getText());

        Date date = new Date(year,month,day);

        if (dates.contains(date)) {
            broadcast("Date already exists in activity");
            return true;
        }

        return false;
    }

    // EFFECTS: returns true and broadcasts a message if there is a
    //          formatting error, otherwise return false
    private boolean checkDate() {
        int year;
        int month;
        int day;

        try {
            year = Integer.parseInt(yearInput.getText());
            month = Integer.parseInt(monthInput.getText());
            day = Integer.parseInt(dayInput.getText());
        } catch (NumberFormatException e) {
            broadcast("Make sure all numbers are whole numbers");
            return true;
        }

        return isValidDate(year,month,day);
    }

    // EFFECTS: returns true and broadcasts a message if the date is invalid,
    //          otherwise returns false
    private boolean isValidDate(int year, int month, int day) {
        Date date = new Date(year,month,day);

        if (year < 0 || year > 9999) {
            broadcast("Year must be between 0-9999");
            return true;
        }

        if (month < 1 || month > 12) {
            broadcast("Month must be between 1-12");
            return true;
        }

        if (day < 1 || day > date.getDaysInMonth()) {
            broadcast("Day must be between 1-" + date.getDaysInMonth() + " in " + date.getMonthString(month));
            return true;
        }

        return false;
    }

    // EFFECTS: returns true and broadcasts a message if time is
    //          formatted poorly, else returns false
    private boolean checkTime() {
        int hour;
        int minute;

        try {
            hour = Integer.parseInt(hourInput.getText());
            minute = Integer.parseInt(minuteInput.getText());
        } catch (NumberFormatException e) {
            broadcast("Time must be in whole numbers");
            return true;
        }

        if (hour < 0 || hour > 23) {
            broadcast("Hour must be in 24H time (Between 0 and 23)");
            return true;
        }

        if (minute < 0 || minute > 59) {
            broadcast("Minute must be between 0 and 59");
            return true;
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds note into the list notes
    private void addNote() {
        if (checkNote()) {
            return;
        }

        Note note = new Note(noteTitle.getText(), noteBody.getText());

        notes.add(note);

        broadcast("Note successfully added!");
    }

    // MODIFIES: this
    // EFFECTS: returns true and broadcasts an error if note title
    //          already exists, otherwise returns false
    private boolean checkNote() {
        String title = noteTitle.getText();
        for (Note n: notes) {
            if (n.getTitle().equals(title)) {
                broadcast("Note with same title already exists");
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: broadcasts a message on the top of the panel
    private void broadcast(String message) {
        messagePanel.removeAll();
        messagePanel.add(new JLabel(message));
        reload();
    }

    // MODIFIES: this
    // EFFECTS: saves current calendar, and
    //          creates an empty calendar based on inputs
    private void makeCalendar() {
        PersistenceHandler persistenceHandler = graphicalOrganizationApp.getPersistenceHandler();

        if (calendarError()) {
            return;
        }

        String calendarName = nameField.getText();
        String scheduleName = scheduleField.getText();

        if (scheduleName.equals("")) {
            scheduleName = "Schedule";
        }

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

    // MODIFIES: this
    // EFFECTS: returns true and generates an error label
    //          if calendar already exists, else returns false
    private boolean calendarError() {
        boolean error = false;
        String calendarName = nameField.getText();
        Config config = graphicalOrganizationApp.getPersistenceHandler().getConfig();

        if (config.getFiles().containsKey(calendarName)) {
            broadcast("Calendar already exists");
            error = true;
        }

        return error;
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

        messagePanel = new JPanel();

        add(messagePanel,BorderLayout.SOUTH);

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
}
