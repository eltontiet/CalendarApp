package ui;

import model.*;
import model.date.Date;
import persistence.CalendarReader;
import persistence.CalendarWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/*
Based off of TellerApp to understand how to get user input, and set it up.
 */

// An organization app
public class OrganizationApp {
    private static final String DEFAULT_JSON_LOCATION = "./data/calendarSave.json";

    private Calendar calendar;
    private Scanner input;
    private CalendarReader calendarReader;
    private CalendarWriter calendarWriter;
    private String jsonStore;

    // EFFECTS: starts the application
    public OrganizationApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: gets the input from the user, and uses it
    private void runApp() {
        boolean quit = false;
        String command;

        init();

        while (!quit) {
            displayMenu();

            command = getInput();

            switch (command) {
                case "q":
                    quit = true;
                    break;
                case "v":
                    viewCalendar();
                    break;
                case "save":
                    saveCalendar();
                    break;
                default:
                    chooseNext(command);
            }
        }
    }

    // EFFECTS: returns the next input that the user inputs
    private String getInput() {

        if (input.hasNext()) {
            return input.nextLine();
        }

        return getInput();
    }

    // MODIFIES: this
    // EFFECTS: initializes calendar
    private void init() {
        // TODO: get jsonStore from config
        jsonStore = DEFAULT_JSON_LOCATION;
        try {
            calendarReader = new CalendarReader(jsonStore);

            calendar = calendarReader.read();

            System.out.println("Started up with Calendar: " + calendar.getName());

        } catch (IOException e) {
            System.out.println(jsonStore + " could not be read, trying " + DEFAULT_JSON_LOCATION);
            jsonStore = DEFAULT_JSON_LOCATION;

            try {
                calendarReader = new CalendarReader(jsonStore);
                calendar = calendarReader.read();

                System.out.println("Success!");

            } catch (IOException ioe) {
                System.out.println("Could not load files, quitting...");
                System.exit(0);
            }
        } finally {
            calendarWriter = new CalendarWriter(jsonStore);
            input = new Scanner(System.in);
        }
    }

    // EFFECTS: decides what to do with the input
    private void chooseNext(String command) {
        switch (command) {
            case "s":
                new ScheduleMenu(calendar);
                break;
            case "c":
                new CalendarMenu(calendar);
                break;
            case "a":
                new ActivityMenu(calendar);
                break;
            case "e":
                new EventMenu(calendar);
                break;
            case "load":
                loadCalendar();
                break;
            default:
                System.out.println("Please select a valid option.");
                break;
        }
    }

    // EFFECTS: asks user for a calendar to load, and loads it
    private void loadCalendar() {
        // System.out.println("Choose a calendar to load: ");
        // getInput()

        try {
            calendar = calendarReader.read();

            System.out.println("Successfully loaded file: " + jsonStore);

        } catch (IOException e) {
            System.out.println("Could not load file, reverting back");

            // TODO: do something else here
            CalendarWriter writer = new CalendarWriter("./data/calendarSave.json");
            writer.write(new Calendar("Starting Calendar"));
            writer.close();
        }

        // TODO: load input here...
    }

    // EFFECTS: saves the calendar to file
    private void saveCalendar() {
        try {
            calendarWriter.open();
            calendarWriter.write(calendar);
            calendarWriter.close();

            System.out.println("Successfully saved file to: " + jsonStore);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file at: " + jsonStore);
        }
    }

    // TODO: Sort into chronological order, Add a date cap (Users don't have to see something 2 years down the line)
    // EFFECTS: outputs everything scheduled in
    //          current calendar based on activity.
    private void viewCalendar() {
        for (Schedule i: calendar.getSchedules()) {
            System.out.println("Schedule: " + i.getName());
            printActivities(i);
        }

        printEvents(calendar.getEvents());
    }

    // EFFECTS: outputs activities in schedule, and all notes
    //          and events related to the activities
    private void printActivities(Schedule schedule) {
        for (Activity i: schedule.getActivities()) {
            System.out.println("\nActivity: " + i.getName());

            System.out.println("Duration: " + i.getDuration());

            printDates(i.getDates());

            printEvents(i.getEvents());

            printNotes(i.getNotes());
        }
    }

    // EFFECTS: prints dates in a list
    private void printDates(List<Date> dates) {
        System.out.println("On the days: ");
        for (Date d: dates) {
            System.out.println("\t" + d.getDate());
        }
    }

    // EFFECTS: prints out event name, date, time, and duration
    private void printEvents(List<Event> events) {
        System.out.println("\n Events:");
        for (Event e: events) {
            System.out.println("\t" + e.getName() + ":");
            System.out.println("\t" + e.getDate().toString());
            System.out.println("\t" + e.getTime().get12HTime());
            if (e.getDuration() != 0) {
                System.out.println("\t" + e.getDuration());
            }
            System.out.println();
        }
    }

    // EFFECTS: prints out all notes
    private void printNotes(List<Note> notes) {
        System.out.println("\n Notes:");
        for (Note n: notes) {
            System.out.println("\t" + n.getTitle());
            System.out.println("\t" + n.getBody());
            System.out.println();
        }
    }

    // EFFECTS: outputs options to the user
    private void displayMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("\tv to view calendar");
        System.out.println("\tc to edit calendars, or make a new one (Not yet implemented)");
        System.out.println("\ts to view schedule");
        System.out.println("\ta to view activities");
        System.out.println("\te to view events");
        System.out.println("\tsave to save");
        System.out.println("\tload to load");
        System.out.println("\tq to quit");
    }
}
