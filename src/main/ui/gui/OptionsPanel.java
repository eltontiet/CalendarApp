package ui.gui;

import model.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Map;

// TODO: Add new activity, schedule, event, and edit. New Calendar https://www.w3schools.com/java/java_files_delete.asp https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html

// Represents the options panel
public class OptionsPanel extends OrganizationAppPanel implements ActionListener {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 850;

    private Config config;
    private PersistenceHandler persistenceHandler;
    private GraphicalOrganizationApp graphicalOrganizationApp;
    private JPanel panel;

    // MODIFIES: this
    // EFFECTS: initializes the options panel
    public OptionsPanel(GraphicalOrganizationApp graphicalOrganizationApp) {
        super();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.graphicalOrganizationApp = graphicalOrganizationApp;
        this.persistenceHandler = graphicalOrganizationApp.getPersistenceHandler();
        config = persistenceHandler.getConfig();

        renderOptions();
    }

    // MODIFIES: this
    // EFFECTS: creates an empty panel
    private void setNewPanel() {
        panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    // REQUIRES: panel was just reset
    // MODIFIES: this
    // EFFECTS: renders the buttons for the different options
    private void renderOptions() {
        setNewPanel();

        initializeAddButtons();
        initializePersistence();

        add(panel);
        reload(panel);
    }

    // MODIFIES: this
    // EFFECTS: adds the add buttons onto the
    private void initializeAddButtons() {
        JButton newScheduleButton = new JButton("New Schedule");
        newScheduleButton.setActionCommand("newSchedule");
        newScheduleButton.addActionListener(this);
        newScheduleButton.setPreferredSize(new Dimension(WIDTH - 50, 50));

        panel.add(newScheduleButton,BorderLayout.CENTER);

        JButton newActivityButton = new JButton("New Activity");
        newActivityButton.setActionCommand("newActivity");
        newActivityButton.addActionListener(this);
        newActivityButton.setPreferredSize(new Dimension(WIDTH - 50, 50));

        panel.add(newActivityButton,BorderLayout.CENTER);

        JButton newEventButton = new JButton("New Event");
        newEventButton.setActionCommand("newEvent");
        newEventButton.addActionListener(this);
        newEventButton.setPreferredSize(new Dimension(WIDTH - 50, 50));

        panel.add(newEventButton,BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds the save and calendars buttons onto the panel
    private void initializePersistence() {
        JButton deleteCalendarButton = new JButton("Delete Calendar");
        deleteCalendarButton.setActionCommand("deleteCalendar");
        deleteCalendarButton.addActionListener(this);

        panel.add(deleteCalendarButton,BorderLayout.NORTH);

        JButton calendarsButton = new JButton("Calendars");
        calendarsButton.setActionCommand("calendars");
        calendarsButton.addActionListener(this);

        panel.add(calendarsButton,BorderLayout.WEST);

        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);

        panel.add(saveButton,BorderLayout.CENTER);
    }

    // EFFECTS: does an action based on the button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            save();
        }
        if (e.getActionCommand().equals("calendars")) {
            remove(panel);
            setNewPanel();
            newCalendarButton();
            calendarsMenu("load");
        }
        if (e.getActionCommand().equals("deleteCalendar")) {
            remove(panel);
            setNewPanel();
            calendarsMenu("delete");
        }
        newActionPerformed(e);
    }

    // EFFECTS: does an action based on the button pressed
    private void newActionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("newCalendar")) {
            graphicalOrganizationApp.getEditorPanel().newCalendar();
        }
        if (e.getActionCommand().equals("newSchedule")) {
            graphicalOrganizationApp.getEditorPanel().newSchedule();
        }
        if (e.getActionCommand().equals("newActivity")) {
            graphicalOrganizationApp.getEditorPanel().newActivity();
        }
        if (e.getActionCommand().equals("newEvent")) {
            graphicalOrganizationApp.getEditorPanel().newEvent();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new calendar button
    private void newCalendarButton() {
        JButton newCalendarButton = new JButton("New Calendar");
        newCalendarButton.setActionCommand("newCalendar");
        newCalendarButton.addActionListener(this);
        newCalendarButton.setPreferredSize(new Dimension(WIDTH - 50, 30));

        JLabel empty = new JLabel(" ");
        empty.setOpaque(false);
        empty.setPreferredSize(new Dimension(WIDTH - 50, 10));

        panel.add(newCalendarButton);
        panel.add(empty);

        reload();
    }

    // EFFECTS: saves the file; if the file cannot be found, places an error on the screen
    private void save() {
        try {
            persistenceHandler.saveCalendar();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            JLabel error = new JLabel("The file could not be saved");
            error.setFont(error.getFont().deriveFont(25.0f));
            add(error,BorderLayout.SOUTH);
        }
    }

    // MODIFIES: this
    // EFFECTS: renders the calendar menu
    private void calendarsMenu(String action) {

        for (Map.Entry<String,String> entry:config.getFiles().entrySet()) {
            String name = entry.getKey();

            if (!graphicalOrganizationApp.getCalendar().getName().equals(name)) {
                JButton button = new JButton(name);
                button.addActionListener(e -> calendarAction(action, entry.getValue()));
                button.setPreferredSize(new Dimension(WIDTH - 50, 30));
                panel.add(button);
            }
        }

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> reset());
        panel.add(cancel);

        add(panel);

        reload(panel);
    }

    // MODIFIES: this
    // EFFECTS: decides whether to load or delete the calendar
    private void calendarAction(String action, String value) {
        if (action.equals("load")) {
            load(value);
        } else if (action.equals("delete")) {
            persistenceHandler.deleteFile(value);
            reset();
        }
    }

    // MODIFIES: this, graphicalOrganizationApp
    // EFFECTS: loads a new calendar
    public void load(String fileName) {
        graphicalOrganizationApp.loadCalendar(fileName);
        reset();
    }

    // MODIFIES: this
    // EFFECTS: resets the options panel
    public void reset() {
        remove(panel);
        renderOptions();
    }
}
