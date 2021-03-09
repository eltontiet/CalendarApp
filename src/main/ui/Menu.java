package ui;

import model.*;
import model.date.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// abstract class for menus
public abstract class Menu {
    protected Scanner input;
    protected Calendar calendar;

    // EFFECTS: initializes input and calendar
    public Menu(Calendar calendar) {
        this.calendar = calendar;
        input = new Scanner(System.in);
    }

    // EFFECTS: Processes user input
    protected String getInput() {
        if (input.hasNext()) {
            return input.nextLine();
        }
        return getInput();
    }

    // MODIFIES: this
    // EFFECTS: prints out the list of items
    //          and prompts user to choose which to remove
    protected abstract void removeItem();

    // MODIFIES: this
    // EFFECTS: prints out the list of items
    //          and prompts user to choose which to edit
    protected abstract void editItem();

    // MODIFIES: this
    // EFFECTS: prompts user to create an item, and
    //          adds it to the list
    protected abstract void makeItem();

    // EFFECTS: prints out schedules with a number based on
    //          the index in list
    protected void listItem(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + " " + list.get(i));
        }
    }

    // EFFECTS: prints out options that the user can choose
    protected void printMenu() {
        System.out.println("\nChoose an option:");
    }

    // TODO: use inputToInt in other methods to reduce duplicated code
    // EFFECTS: returns the integer value of an input, or throws number format exception
    protected Integer inputToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please input a number");
            return inputToInt(getInput());
        }
    }

    // TODO After learning overriding hashmaps and equals, is there an easier way to implement this?
    // EFFECTS: gets a schedule based on index or name, or null if doesn't exist
    protected Schedule getSchedule(String command, Calendar calendar) {
        List<Schedule> scheduleList = calendar.getSchedules();
        int index;

        try {
            index = Integer.parseInt(command) - 1;
            if (index < scheduleList.size() && !(index < 0)) {
                return scheduleList.get(index);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        } catch (NumberFormatException e) {
            if (command.equals("q")) {
                return null;
            } else if (calendar.getSchedule(command) != null) {
                return calendar.getSchedule(command);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        }
    }

    // EFFECTS: prints out schedule names with a number
    //          based on the index in list
    protected void listSchedules(Calendar calendar) {
        List<Schedule> schedules = calendar.getSchedules();
        List<String> list = new ArrayList<>();

        for (Schedule schedule : schedules) {
            list.add(schedule.getName());
        }
        listItem(list);
    }

    // EFFECTS: gets a schedule based on index or name, or null if doesn't exist
    protected Activity getActivity(String command, Schedule schedule) {
        List<Activity> activityList = schedule.getActivities();
        int index;

        try {
            index = Integer.parseInt(command) - 1;
            if (index < activityList.size()) {
                return activityList.get(index);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        } catch (NumberFormatException e) {
            if (command.equals("q")) {
                return null;
            } else if (schedule.getActivity(command) != null) {
                return schedule.getActivity(command);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        }
    }

    // EFFECTS: prints out activity names with a number
    //          based on the index in list from a schedule
    protected void listActivities(Schedule schedule) {
        List<Activity> activities = schedule.getActivities();
        List<String> list = new ArrayList<>();

        for (Activity activity : activities) {
            list.add(activity.getName());
        }
        listItem(list);
    }

    // EFFECTS: gets an event based on index or name from an activity,
    //          or null if doesn't exist
    protected Event getEvent(String command, Activity activity) {
        List<Event> eventList = activity.getEvents();
        int index;

        try {
            index = Integer.parseInt(command) - 1;
            if (index < eventList.size()) {
                return eventList.get(index);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        } catch (NumberFormatException e) {
            if (command.equals("q")) {
                return null;
            } else if (activity.getEvent(command) != null) {
                return activity.getEvent(command);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        }
    }

    // EFFECTS: gets an event based on index or name from a calender,
    //          or null if doesn't exist
    protected Event getEvent(String command, Calendar calendar) {
        List<Event> eventList = calendar.getEvents();
        int index;

        try {
            index = Integer.parseInt(command) - 1;
            if (index < eventList.size()) {
                return eventList.get(index);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        } catch (NumberFormatException e) {
            if (command.equals("q")) {
                return null;
            } else if (calendar.getEvent(command) != null) {
                return calendar.getEvent(command);
            } else {
                System.out.println("please choose a valid input");
                return null;
            }
        }
    }

    // EFFECTS: prints out event names with a number
    //          based on the index in list from a calendar
    protected void listEvents(Calendar calendar) {
        List<Event> events = calendar.getEvents();
        List<String> list = new ArrayList<>();

        for (Event event : events) {
            list.add(event.getName());
        }

        listItem(list);
    }

    // EFFECTS: prints out event names with a number
    //          based on the index in list from an activity
    protected void listEvents(Activity activity) {
        List<Event> events = activity.getEvents();
        List<String> list = new ArrayList<>();

        for (Event event : events) {
            list.add(event.getName());
        }

        listItem(list);
    }

    // EFFECTS: prints out event name, date, time, and duration
    protected void printEvent(Event event) {
        System.out.println("\t" + event.getName());
        System.out.println("\t" + event.getDate().getDate());
        System.out.println("\t" + event.getTime().get12HTime());

        if (event.getDuration() != 0) {
            System.out.println("\tFor " + event.getDuration() + " minutes");
        }
    }

    // EFFECTS: prints out event name, date, time, and duration
    protected void printActivity(Activity activity) {
        System.out.println("\t" + activity.getName());

        System.out.println("\tDates:");
        for (Date d: activity.getDates()) {
            System.out.println("\t\t" + d.getDate());
        }

        System.out.println("\n\t" + activity.getTime().get12HTime());

        if (activity.getDuration() != 0) {
            System.out.println("\tFor " + activity.getDuration() + " minutes");
        }

        System.out.println("\tNotes:");
        for (Note n: activity.getNotes()) {
            System.out.println("\t  Title: " + n.getTitle());
            System.out.println("\t  Body: " + n.getBody() + "\n");
        }
    }
}
