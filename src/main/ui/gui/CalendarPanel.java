package ui.gui;

import exceptions.BadDayOfWeekException;
import model.Activity;
import model.Calendar;
import model.Event;
import model.Schedule;
import model.date.Date;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

// TODO: add functionality to view by schedule
// Represents the calendar
public class CalendarPanel extends OrganizationAppPanel implements ActionListener {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 860;

    Calendar calendar;
    JLabel monthYearLabel;
    JPanel daysOfWeek;
    JPanel calendarRender;
    InformationPanel ip;
    Image image;
    Date today;
    int year;
    int month;

    // MODIFIES: this
    // EFFECTS: Creates all of the modules to visualize the calendar
    public CalendarPanel(Calendar calendar, InformationPanel ip) {
        super();
        this.calendar = calendar;
        today = getToday();
        year = today.getYear();
        month = today.getMonth();

        this.ip = ip;

        try {
            image = ImageIO.read(new URL("https://images.unsplash.com/photo-1484312152213-d713e8b7c053?"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scaleImage(image);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        renderCalendar();
        setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: renders the calendar
    private void renderCalendar() {
        monthYearLabel = new JLabel(getMonthString() + " " + year);
        monthYearLabel.setSize(new Dimension(50,20));

        JButton previousMonth = new JButton("<");
        JButton nextMonth = new JButton(">");

        previousMonth.setActionCommand("previous");
        previousMonth.addActionListener(this);

        nextMonth.setActionCommand("next");
        nextMonth.addActionListener(this);

        add(previousMonth);
        add(monthYearLabel);
        add(nextMonth);

        addDaysOfWeek();
        renderMonth(year, month);
    }

    // MODIFIES: this
    // EFFECTS: scales the image given to the size of the calendar, and sets
    //          this.image to it
    private void scaleImage(Image image) {
        if (WIDTH / HEIGHT >= image.getWidth(null) / image.getHeight(null)) {
            int newWidth = image.getWidth(null) * HEIGHT / image.getHeight(null);
            this.image = image.getScaledInstance(newWidth, HEIGHT, Image.SCALE_DEFAULT);
        } else {
            int newHeight = image.getHeight(null) * WIDTH / image.getWidth(null);
            this.image = image.getScaledInstance(WIDTH, newHeight, Image.SCALE_DEFAULT);
        }
    }

    // TODO: Should be put in Date class
    // REQUIRES: 1 <= today.getMonth() <= 12
    // EFFECTS: returns the month as a string
    private String getMonthString() {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            default:
                return lastSixMonths();
        }
    }

    // REQUIRES: 7 <= month <= 12
    // EFFECTS: returns the month as a string
    private String lastSixMonths() {
        switch (month) {
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            default:
                return "December";
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the sun to mon on the top of the calendar
    private void addDaysOfWeek() {
        daysOfWeek = new JPanel();
        daysOfWeek.setSize(new Dimension(1050, 20));
        daysOfWeek.add(createDayOfWeekRect("Sun"));
        daysOfWeek.add(createDayOfWeekRect("Mon"));
        daysOfWeek.add(createDayOfWeekRect("Tue"));
        daysOfWeek.add(createDayOfWeekRect("Wed"));
        daysOfWeek.add(createDayOfWeekRect("Thurs"));
        daysOfWeek.add(createDayOfWeekRect("Fri"));
        daysOfWeek.add(createDayOfWeekRect("Sat"));
        daysOfWeek.setOpaque(false);
        add(daysOfWeek);
    }

    // EFFECTS: creates a rectangle denoting the day of the week
    private JPanel createDayOfWeekRect(String day) {
        JPanel rect = new JPanel();
        rect.setPreferredSize(new Dimension(150,25));
        rect.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel text = new JLabel(day);
        text.setSize(new Dimension(150,25));
        rect.add(text);
        rect.setOpaque(false);
        return rect;
    }

    // MODIFIES: this
    // EFFECTS: creates a month of year and month
    //          creates the day boxes and fills it
    //          in based on the calendar
    private void renderMonth(int year, int month) {
        calendarRender = new JPanel();
        Date firstOfMonth = new Date(year, month, 1);
        calendarRender.setPreferredSize(new Dimension(1100, 150 * 5 + 150));

        int daysInMonth = firstOfMonth.getDaysInMonth();

        String dayOfWeek = firstOfMonth.getDayOfWeek();

        try {
            addEmptyDayBoxes(calendarRender, today.getDayCode(dayOfWeek));

        } catch (BadDayOfWeekException e) {
            System.out.println("Something unexpected happened... ");
        }

        for (int i = 1; i <= daysInMonth; i++) {
            calendarRender.add(createDayPanel(i));
        }

        Date lastDayOfMonth = new Date(year, month, firstOfMonth.getDaysInMonth());
        String lastDayOfWeek = lastDayOfMonth.getDayOfWeek();

        try {
            addEmptyDayBoxes(calendarRender, 6 - today.getDayCode(lastDayOfWeek));

        } catch (BadDayOfWeekException e) {
            System.out.println("Something unexpected happened... ");
        }

        calendarRender.setOpaque(false);

        add(calendarRender);
    }

    // MODIFIES: rect
    // EFFECTS: creates an empty square and adds it to rect
    private void addEmptyDayBoxes(JPanel rect, int numBoxes) {
        for (int i = 0; i < numBoxes; i++) {
            JPanel emptyRect = new JPanel();

            emptyRect.setPreferredSize(new Dimension(150,150));
            emptyRect.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            emptyRect.setOpaque(false);
            rect.add(emptyRect);
        }
    }

    // EFFECTS: creates the day panel
    private JPanel createDayPanel(int day) {
        JPanel rect = new JPanel();
        rect.setLayout(new GridBagLayout());
        rect.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GridBagConstraints gbc = setDayFormat();

        rect.setPreferredSize(new Dimension(150,150));

        JLabel dayString = new JLabel(Integer.toString(day));
        dayString.setSize(new Dimension(50,50));
        if (today.equals(new Date(year,month,day))) {
            dayString.setForeground(Color.RED);
        }
        rect.add(dayString, gbc);

        JPanel activityContainer = new JPanel();
        activityContainer.setLayout(new GridBagLayout());
        addDayEvents(activityContainer, new Date(year, month, day));
        gbc.gridy = 1;

        activityContainer.setOpaque(false);
        rect.setOpaque(false);
        rect.add(activityContainer, gbc);

        return rect;
    }

    // EFFECTS: returns formatting for the day number
    private GridBagConstraints setDayFormat() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0,5,0,0);
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        return gbc;
    }

    // MODIFIES: rect
    // EFFECTS: adds all events and activities onto the calendar
    private void addDayEvents(JPanel panel, Date date) {
        for (Schedule schedule: calendar.getSchedules()) {
            addActivitiesOnDate(panel, date, schedule);
        }
        for (Event event: calendar.getEvents()) {
            if (event.getDate().equals(date)) {
                addEventPreview(panel, event);
            }
        }
    }

    // MODIFIES: rect
    // EFFECTS: adds a small preview of the event onto rect, and a mouse handler
    public void addEventPreview(JPanel panel, Event event) {
        JPanel eventRect = new JPanel();

        String label = event.getTime().get12HTime() + " " + event.getName();

        JLabel eventLabel = new JLabel(label);
        eventLabel.setFont(eventLabel.getFont().deriveFont(9.0f));

        eventRect.setPreferredSize(new Dimension(140,16));
        FlowLayout layout = (FlowLayout) eventRect.getLayout();
        layout.setVgap(0);

        eventRect.add(eventLabel);

        eventRect.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        eventRect.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        eventRect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ip.setInformation(event);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;

        panel.add(eventRect, gbc);
    }

    // MODIFIES: rect
    // EFFECTS: adds all activities and events on the JPanel
    private void addActivitiesOnDate(JPanel panel, Date date, Schedule schedule) {
        for (Activity a:schedule.getActivities()) {
            if (a.getDates().contains(date)) {
                addActivityPreview(panel, a);
            }
            for (Event event: a.getEvents()) {
                if (event.getDate().equals(date)) {
                    addEventPreview(panel, event);
                }
            }
        }
    }

    // MODIFIES: rect
    // EFFECTS: adds a small activity preview onto the JPanel
    public void addActivityPreview(JPanel panel, Activity a) {
        JPanel activityRect = new JPanel();

        String label = a.getTime().get12HTime() + " " + a.getName();

        JLabel eventLabel = new JLabel(label);
        eventLabel.setFont(eventLabel.getFont().deriveFont(9.0f));

        activityRect.setPreferredSize(new Dimension(140,16));
        FlowLayout layout = (FlowLayout) activityRect.getLayout();
        layout.setVgap(0);

        activityRect.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        activityRect.add(eventLabel);

        activityRect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ip.setInformation(a);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;

        panel.add(activityRect, gbc);
    }

    // Taken from: https://stackoverflow.com/a/19125946
    // EFFECTS: sets the background image of the frame
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }

    // TODO: terrible cohesion
    // MODIFIES: this
    // EFFECTS: goes to the previous or next month
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("previous")) {
            remove(calendarRender);
            if (month == 1) {
                year -= 1;
                month = 12;
            } else {
                month -= 1;
            }
            monthYearLabel.setText(getMonthString() + " " + year);
            renderMonth(year,month);
            reload();
        } else {
            remove(calendarRender);

            if (month == 12) {
                year += 1;
                month = 1;
            } else {
                month += 1;
            }

            monthYearLabel.setText(getMonthString() + " " + year);
            renderMonth(year,month);
            reload();
        }
    }
}
