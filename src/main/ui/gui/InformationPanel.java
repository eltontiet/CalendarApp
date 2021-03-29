package ui.gui;

import model.*;
import model.Event;
import model.date.Date;

import javax.swing.*;
import java.awt.*;

// Represents the information of individual activities.
public class InformationPanel extends OrganizationAppPanel {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 850;
    private static final String indent = "    ";

    GraphicalOrganizationApp graphicalOrganizationApp;
    CalendarPanel cp;
    EditorPanel ep;
    JPanel information;

    // Initializes an empty InformationPanel with a set size
    public InformationPanel() {
        super();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        information = new JPanel();
        add(information);
    }

    // MODIFIES: this
    // EFFECTS: sets graphicalOrganizationApp, cp, and ep
    public void setGraphicalOrganizationApp(GraphicalOrganizationApp graphicalOrganizationApp) {
        this.graphicalOrganizationApp = graphicalOrganizationApp;
        this.cp = graphicalOrganizationApp.getCalendarPanel();
        this.ep = graphicalOrganizationApp.getEditorPanel();
    }


    // MODIFIES: this
    // EFFECTS: sets the information being represented to the event information
    public void setInformation(Event event) {
        newEmptyInformation();

        JLabel name = new JLabel(event.getName());
        JLabel date = new JLabel("Date: " + event.getDate().getDate());
        JLabel time = new JLabel("Time: " + event.getTime().get12HTime());
        JLabel duration = new JLabel("Duration: " + event.getDuration() + " minutes");

        name.setSize(new Dimension(WIDTH, 50));
        date.setSize(new Dimension(WIDTH, 50));
        time.setSize(new Dimension(WIDTH, 50));
        duration.setSize(new Dimension(WIDTH, 50));

        information.add(name);
        information.add(date);
        information.add(time);
        if (event.getDuration() != 0) {
            information.add(duration);
        }

        addEdit(event);

        reload();
    }

    // MODIFIES: this
    // EFFECTS: sets the information being represented to the activity information
    public void setInformation(Activity activity) {
        newEmptyInformation();

        JLabel name = new JLabel(activity.getName());
        JLabel time = new JLabel("Time: " + activity.getTime().get12HTime());
        JLabel duration = new JLabel("Duration: " + activity.getDuration() + " minutes");

        name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        time.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        duration.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        information.add(name);
        information.add(time);
        addNotes(activity);

        if (activity.getDuration() != 0) {
            information.add(duration);
        }

        addDates(activity);


        for (Event event: activity.getEvents()) {
            cp.addEventPreview(information,event);
        }

        information.add(Box.createRigidArea(new Dimension(0,20)));

        addEdit(activity);

        reload();
    }

    // TODO: Lots of copy pasta here
    // MODIFIES: this
    // EFFECTS: creates the edit and delete buttons, and
    //          their functionality
    private void addEdit(Activity activity) {
        JPanel editPanel = new JPanel();

        editPanel.setAlignmentX(Panel.LEFT_ALIGNMENT);
        editPanel.setSize(new Dimension(WIDTH - 50, 50));
        editPanel.setOpaque(false);

        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        edit.addActionListener(e -> ep.editActivity(activity));

        delete.addActionListener(e -> {
            information.remove(editPanel);
            askDelete(activity);
        });

        editPanel.add(edit,BorderLayout.WEST);
        editPanel.add(delete,BorderLayout.EAST);

        information.add(editPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates the edit and delete buttons, and
    //          their functionality
    private void addEdit(Event event) {
        JPanel editPanel = new JPanel();

        editPanel.setAlignmentX(Panel.LEFT_ALIGNMENT);
        editPanel.setSize(new Dimension(WIDTH - 50, 50));
        editPanel.setOpaque(false);

        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        edit.addActionListener(e -> ep.editEvent(event));

        delete.addActionListener(e -> {
            information.remove(editPanel);
            askDelete(event);
        });

        editPanel.add(edit,BorderLayout.WEST);
        editPanel.add(delete,BorderLayout.EAST);

        information.add(editPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a delete verification, and adds functionality
    private void askDelete(Event event) {
        JPanel deleteConfirmation = new JPanel();

        deleteConfirmation.setAlignmentX(Panel.LEFT_ALIGNMENT);
        deleteConfirmation.setPreferredSize(new Dimension(WIDTH - 50, 100));
        deleteConfirmation.setOpaque(false);

        JLabel text1 = new JLabel("Are you sure you");
        JLabel text2 = new JLabel("want to delete this?");

        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        yes.addActionListener(e -> deleteEvent(event));

        no.addActionListener(e -> {
            information.remove(deleteConfirmation);
            addEdit(event);
            reload();
        });

        deleteConfirmation.add(text1, BorderLayout.NORTH);
        deleteConfirmation.add(text2, BorderLayout.NORTH);
        deleteConfirmation.add(yes, BorderLayout.WEST);
        deleteConfirmation.add(no,BorderLayout.EAST);

        information.add(deleteConfirmation);

        reload();
    }

    // MODIFIES: this
    // EFFECTS: creates a delete verification, and adds functionality
    private void askDelete(Activity activity) {
        JPanel deleteConfirmation = new JPanel();

        deleteConfirmation.setAlignmentX(Panel.LEFT_ALIGNMENT);
        deleteConfirmation.setPreferredSize(new Dimension(WIDTH - 50, 100));
        deleteConfirmation.setOpaque(false);

        JLabel text1 = new JLabel("Are you sure you");
        JLabel text2 = new JLabel("want to delete this?");

        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        yes.addActionListener(e -> deleteActivity(activity));

        no.addActionListener(e -> {
            information.remove(deleteConfirmation);
            addEdit(activity);
            reload();
        });

        deleteConfirmation.add(text1, BorderLayout.NORTH);
        deleteConfirmation.add(text2, BorderLayout.NORTH);
        deleteConfirmation.add(yes, BorderLayout.WEST);
        deleteConfirmation.add(no,BorderLayout.EAST);

        information.add(deleteConfirmation);

        reload();
    }

    // MODIFIES: this
    // EFFECTS: deletes this event, and reloads the calendar
    private void deleteEvent(Event event) {
        Calendar calendar = graphicalOrganizationApp.getCalendar();

        if (calendar.getEvents().contains(event)) {
            calendar.removeEvent(event);
            graphicalOrganizationApp.reloadCalendar();

        } else {
            for (Schedule s:calendar.getSchedules()) {

                for (Activity a:s.getActivities()) {

                    if (a.getEvents().contains(event)) {
                        a.removeEvent(event);

                        graphicalOrganizationApp.reloadCalendar();
                        return;
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes this activity, and reloads the calendar
    private void deleteActivity(Activity activity) {
        graphicalOrganizationApp.getCalendar().findActivitySchedule(activity).removeActivity(activity);
        graphicalOrganizationApp.reloadCalendar();
    }

    // MODIFIES: this
    // EFFECTS: adds the dates of the activity to information
    private void addDates(Activity activity) {
        JLabel dates = new JLabel("Dates: ");
        dates.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        information.add(dates);
        for (Date date: activity.getDates()) {
            JLabel dateLabel = new JLabel(indent + date.getDate());
            dateLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            information.add(dateLabel);
        }
        information.add(new JLabel(" "));
    }

    // MODIFIES: this
    // EFFECTS: adds all activity notes to information
    private void addNotes(Activity activity) {
        information.add(new JLabel(" "));
        for (Note note: activity.getNotes()) {
            JLabel title = new JLabel(note.getTitle());
            title.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            information.add(title);

            JTextArea body = new JTextArea(indent + note.getBody());

            body.setMinimumSize(new Dimension(WIDTH, 50));

            body.setWrapStyleWord(true);
            body.setLineWrap(true);
            body.setAlignmentX(JLabel.LEFT_ALIGNMENT);

            information.add(body);
            information.add(new JLabel(" "));
        }
    }

    // MODIFIES: this
    // EFFECTS: empties out information
    public void newEmptyInformation() {
        remove(information);

        information = new JPanel();
        information.setLayout(new BoxLayout(information,BoxLayout.Y_AXIS));
        information.setBackground(OrganizationAppPanel.BACKGROUNDCOLOUR);
        information.setMinimumSize(new Dimension(WIDTH - 30,50));
        information.setMaximumSize(new Dimension(WIDTH - 25,HEIGHT));

        add(information);
    }
}
