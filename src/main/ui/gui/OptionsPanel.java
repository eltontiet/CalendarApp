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
            calendarsMenu();
        }
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
    private void calendarsMenu() {
        remove(panel);
        setNewPanel();

        for (Map.Entry<String,String> entry:config.getFiles().entrySet()) {
            JButton button = new JButton(entry.getKey());
            button.addActionListener(e -> load(entry.getValue()));
            button.setPreferredSize(new Dimension(WIDTH - 50, 30));
            panel.add(button);
        }

        JLabel empty = new JLabel(" ");
        empty.setOpaque(false);
        empty.setPreferredSize(new Dimension(WIDTH - 50, 10));
        panel.add(empty);

        JButton newCalendarButton = new JButton("New Calendar");
        newCalendarButton.setActionCommand("newCalendar");
        newCalendarButton.addActionListener(this);
        newCalendarButton.setPreferredSize(new Dimension(WIDTH - 50, 30));
        panel.add(newCalendarButton);

        add(panel);

        reload(panel);
    }

    // MODIFIES: this, graphicalOrganizationApp
    // EFFECTS: loads a new calendar
    private void load(String fileName) {
        graphicalOrganizationApp.loadCalendar(fileName);
        remove(panel);
        renderOptions();
    }
}
