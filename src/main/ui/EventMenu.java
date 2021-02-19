package ui;

import model.Activity;
import model.Calendar;
import model.Event;
import model.Schedule;
import model.date.Date;
import model.date.Time;

import java.util.ArrayList;
import java.util.List;

// Edit events, add or remove them from an activity or calendar
public class EventMenu extends Menu {
    private List<Event> eventList;
    private Activity activity;

    // EFFECTS: Initializes calendar and event menu
    public EventMenu(Calendar calendar) {
        super(calendar);
        getEventList();
        printMenu();
        processInput();
    }

    // MODIFIES: this
    // EFFECTS: initializes eventList on calendar, or chooses a schedule
    private void getEventList() {
        String input;
        System.out.println("Input 0 for the calendar events, or choose a schedule");
        listSchedules(calendar);
        input = getInput();

        if (input.equals("0")) {
            eventList = calendar.getEvents();
        } else {
            chooseSchedule(input);
        }
    }

    // EFFECTS: gets the schedule corresponding to input
    //          and goes to chooseActivity()
    private void chooseSchedule(String input) {
        Schedule schedule = getSchedule(input, calendar);
        if (schedule == null) {
            getEventList();
        } else {
            chooseActivity(schedule);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to choose an activity,
    //          then initializes eventList
    private void chooseActivity(Schedule schedule) {
        Activity activity;
        System.out.println("Choose an activity: ");
        listActivities(schedule);

        activity = getActivity(getInput(), schedule);
        if (activity == null) {
            chooseActivity(schedule);
        } else {
            eventList = activity.getEvents();
            this.activity = activity;
        }
    }

    // EFFECTS: Processes user input
    private void processInput() {
        String command = getInput();
        switch (command.toLowerCase()) {
            case "v":
                processViewEvent();
                break;
            case "l":
                listEventList();
                break;
            case "n":
                makeItem();
                break;
            case "r":
                removeItem();
                break;
            case "e":
                editItem();
                break;
            case "q":
                break;
            default:
                System.out.println("Please choose a valid input");
                goBack();
        }
    }

    // EFFECTS: prompts user to select an event, and processes it
    private void processViewEvent() {
        Event event;
        System.out.println("Choose the number of an event, or write the name of it");
        listEventList();
        String command = getInput();

        if (activity == null) {
            event = getEvent(command, calendar);
        } else {
            event = getEvent(command, activity);
        }
        if (event == null) {
            goBack();
        } else {
            printEvent(event);
        }
    }

    // EFFECTS: prints out all events in a list
    private void listEventList() {
        List<String> list = new ArrayList<>();
        for (Event event : eventList) {
            list.add(event.getName());
        }
        listItem(list);
    }

    // MODIFIES: this
    // EFFECTS: lists out all events, then prompts user
    //          to choose what to remove from the list
    @Override
    protected void removeItem() {
        System.out.println("Choose the number of an event, or write the name of it");
        listEventList();
        String command = getInput();
        removeEvent(command);
    }

    // MODIFIES: this
    // EFFECTS: removes the event from the list
    private void removeEvent(String command) {
        Event event;
        if (activity == null) {
            event = getEvent(command, calendar);
        } else {
            event = getEvent(command, activity);
        }
        if ((event == null)) {
            goBack();
        } else {
            eventList.remove(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays a list of items to choose from, then
    //          prompts user to edit it
    @Override
    protected void editItem() {
        System.out.println("Choose the number of an event, or write the name of it");
        listEventList();
        String command = getInput();
        editEvent(command);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to edit the event
    private void editEvent(String command) {
        Event event;
        if (activity == null) {
            event = getEvent(command, calendar);
        } else {
            event = getEvent(command, activity);
        }
        if (event == null) {
            goBack();
        } else {
            chooseEdit(event);
        }
    }

    // EFFECTS: prompts user for what to edit, and allows editing
    private void chooseEdit(Event event) {
        printEditMenu();
        getEditCommand(event);
    }

    // EFFECTS: processes input and chooses what to edit
    private void getEditCommand(Event event) {
        String input = getInput();
        Event newEvent = new Event(event.getName(),event.getDate(),event.getTime(),event.getDuration());
        switch (input) {
            case "n":
                editName(newEvent);
                confirmEdit(event,newEvent);
                break;
            case "d":
                editDate(newEvent);
                confirmEdit(event,newEvent);
                break;
            case "t":
                editTime(newEvent);
                confirmEdit(event,newEvent);
                break;
            case "l":
                editLength(newEvent);
                confirmEdit(event,newEvent);
                break;
            default:
                System.out.println("Please input a valid command");
                getEditCommand(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets duration in newEvent to a new inputted value
    private void editLength(Event newEvent) {
        newEvent.setDuration(inputDuration());
    }

    // MODIFIES: this
    // EFFECTS: sets time in newEvent to a new inputted value
    private void editTime(Event newEvent) {
        newEvent.setTime(inputTime());
    }

    // MODIFIES: this
    // EFFECTS: sets date in newEvent to a new inputted value
    private void editDate(Event newEvent) {
        newEvent.setDate(inputDate());
    }

    // MODIFIES: this
    // EFFECTS: sets name in newEvent to a new inputted value
    private void editName(Event newEvent) {
        newEvent.setName(inputName());
    }

    // EFFECTS: outputs options for editing
    private void printEditMenu() {
        System.out.println("\nWhat do you want to edit?");
        System.out.println("\tn for name");
        System.out.println("\td for date");
        System.out.println("\tt for time");
        System.out.println("\tl for length of the event (duration)");
    }

    // EFFECTS: confirmation for editing an event
    private void confirmEdit(Event event, Event newEvent) {
        System.out.println("\nDo you want to change the event from: ");
        printEvent(event);

        System.out.println("to: ");
        printEvent(newEvent);

        System.out.println("(y/n)");
        String command = getInput();

        if (command.equals("y")) {
            event.setEvent(newEvent);
            System.out.println("Success");
        } else if (command.equals("n")) {
            System.out.println("Aborting");
        } else {
            System.out.println("please input a valid command");
            confirmEdit(event, newEvent);
        }
    }

    // EFFECTS: Asks user for confirmation about the new event
    private void confirmEvent(Event event) {
        System.out.println("\nDo you want to make a new event: ");
        System.out.println("\t" + event.getName());
        System.out.println("(y/n)");

        String command = getInput();

        if (command.equals("y")) {
            eventList.add(event);
            System.out.println("Success");
        } else if (command.equals("n")) {
            System.out.println("Aborting");
        } else {
            System.out.println("please input a valid command");
            confirmEvent(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to make item, and adds it to list
    @Override
    protected void makeItem() {
        Event event = makeEvent();
        confirmEvent(event);
    }

    // EFFECTS: prompts user to input name for name, date
    //          time and duration, and returns the event
    private Event makeEvent() {
        String name;
        Date date;
        Time time;
        int duration;

        name = inputName();

        date = inputDate();

        time = inputTime();

        duration = inputDuration();
        return new Event(name, date, time, duration);
    }

    // EFFECTS: prompts the user for the duration of the event
    //          and returns it
    private int inputDuration() {
        System.out.println("How long is the event in minutes? (0 for no time)");

        String input = getInput();
        int duration = inputToInt(input);

        if (duration < 0) {
            System.out.println("Please input a positive value ");
            return inputDuration();
        } else {
            return duration;
        }
    }

    // EFFECTS: prompts the user for the time of the event
    //          and returns it
    private Time inputTime() {
        int hour = getHour();
        int minute = getMinute();
        return new Time(hour,minute);
    }

    // EFFECTS: prompts the user for the minute that
    //          the event takes place
    private int getMinute() {
        System.out.println("At what minute does the event take place? ");

        String input = getInput();
        int i = inputToInt(input);

        if (i < 0 || i > 60) {
            System.out.println("Please input an integer between 0 and 60 ");
            return getMinute();
        } else {
            return i;
        }
    }

    // EFFECTS: prompts the user for the hour that
    //          the event takes place
    private int getHour() {
        System.out.println("At what hour does the event take place? (24H time)");

        String input = getInput();
        int i = inputToInt(input);

        if (i < 0 || i > 23) {
            System.out.println("Please input an integer between 0 and 23 ");
            return getHour();
        } else {
            return i;
        }
    }

    // EFFECTS: prompts the user for the date of the event
    private Date inputDate() {
        int year = getYear();
        int month = getMonth();
        int day = getDay(month);
        return new Date(year, month, day);
    }

    // EFFECTS: prompts the user for the day that
    //          the event takes place
    private int getDay(int month) {
        Date infoDate = new Date(2002, month,1);
        int maxDays = infoDate.getDaysInMonth();

        System.out.println("What day is the event? ");

        String input = getInput();
        int i = inputToInt(input);

        if (i < 0 || i > maxDays) {
            System.out.println("Please input an integer between 0 and " + maxDays);
            return getDay(month);
        } else {
            return i;
        }
    }

    // EFFECTS: prompts the user for the month that
    //          the event takes place
    private int getMonth() {
        System.out.println("What month is the event? ");

        String input = getInput();
        int i = inputToInt(input);

        if (i < 0 || i > 12) {
            System.out.println("Please input an integer between 0 and 12 ");
            return getMonth();
        } else {
            return i;
        }
    }

    // EFFECTS: prompts the user for the year that
    //          the event takes place
    private int getYear() {
        System.out.println("What year is the event? ");

        String input = getInput();
        int i = inputToInt(input);

        if (i < 0) {
            System.out.println("Please input a positive integer ");
            return getYear();
        } else {
            return i;
        }
    }

    // EFFECTS: prompts the user for the name of the event
    private String inputName() {
        String name;

        System.out.println("Enter a name: ");
        name = getInput();

        return name;
    }

    // EFFECTS: shows menu and processes input again
    private void goBack() {
        printMenu();
        processInput();
    }

    // EFFECTS: prints out options that the user can choose
    @Override
    protected void printMenu() {
        super.printMenu();
        System.out.println("\tv to view details of a specific event");
        System.out.println("\tl to get a list of events");
        System.out.println("\tn to make a new event");
        System.out.println("\tr to remove an event from the calendar/activity");
        System.out.println("\te to edit an event");
        System.out.println("\tq to get back to main menu");
    }
}
