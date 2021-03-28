package ui;

import model.*;
import model.date.Date;
import org.json.JSONException;
import persistence.CalendarReader;
import persistence.CalendarWriter;
import persistence.ConfigReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Scanner;

/*
Based off of TellerApp to understand how to get user input, and set it up.
 */

// An organization app
public class OrganizationApp {
    private Calendar calendar;
    private Scanner input;
    private CalendarReader calendarReader;
    private CalendarWriter calendarWriter;
    private ConfigReader configReader;
    private String jsonStore;
    private Config config;
    private Date today;

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
                    quit = askQuit();
                    break;
                case "load":
                    loadCalendar();
                    break;
                case "save":
                    saveCalendar();
                    break;
                default:
                    chooseNext(command);
            }
        }
    }

    // EFFECTS: prompts user to confirm save and quit, and returns
    //          true if user wants to quit
    private boolean askQuit() {
        System.out.println("Are you sure you want to quit? (y/n)");
        String command = getInput();

        if (command.equals("y")) {
            askSave();
            return true;
        } else if (command.equals("n")) {
            return false;
        } else {
            System.out.println("Please input a valid command");
            return askQuit();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to confirm save, and save if user wants to save
    private void askSave() {
        System.out.println("Do you want to save? (y/n)");
        String command = getInput();

        if (command.equals("y")) {
            saveCalendar();
        } else if (command.equals("n")) {
            System.out.println("Are you sure? (y/n)");

            command = getInput();

            if (command.equals("y")) {
                System.out.println(calendar.getName() + " was not saved");

            } else if (command.equals("n")) {
                askSave();

            } else {
                System.out.println("Please input a valid command");

                askSave();
            }
        } else {
            System.out.println("Please input a valid command");

            askSave();
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
        initConfig();
        jsonStore = config.getCurrentFile();
        try {
            calendarReader = new CalendarReader(jsonStore);

            calendar = calendarReader.read();

            System.out.println("Started up with Calendar: " + calendar.getName());

        } catch (JSONException | IOException e) {
            System.out.println(jsonStore + " could not be read, trying " + Config.DEFAULT_SAVE_LOCATION);
            jsonStore = Config.DEFAULT_SAVE_LOCATION;

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
            getToday();
        }
    }

    // Used https://stackoverflow.com/questions/9474121/i-want-to-get-year-month-day-etc-from-java-date-to-compare-with-gregorian-cal/32363174#32363174
    // to understand Date and LocalDate
    // EFFECTS: sets today to the system's current date, and prints the date
    private void getToday() {
        java.util.Date todayDate = java.util.Calendar.getInstance().getTime();
        java.time.LocalDate todayLocalDate = todayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = todayLocalDate.getYear();
        int month = todayLocalDate.getMonthValue();
        int day = todayLocalDate.getDayOfMonth();
        today = new Date(year,month,day);
        System.out.println("\nThe date today is: " + today.getDate());
    }

    // MODIFIES: this
    // EFFECTS: initializes config file
    private void initConfig() {
        try {
            configReader = new ConfigReader(Config.CONFIG_FILE_LOCATION);
            config = configReader.read();
        } catch (IOException | JSONException e) {
            System.out.println("Config file could not be read, resetting to default values");
            config = new Config();
            config.setup();
        }
    }

    // EFFECTS: decides what to do with the input
    private void chooseNext(String command) {
        switch (command) {
            case "s":
                new ScheduleMenu(calendar);
                break;
            case "c":
                new CalendarMenu(calendar, config);
                load(config.getCurrentFile());
                break;
            case "a":
                new ActivityMenu(calendar);
                break;
            case "e":
                new EventMenu(calendar);
                break;
            case "v":
                viewCalendar();
                break;
            default:
                System.out.println("Please select a valid option.");
                break;
        }
    }

    // EFFECTS: asks user for a calendar to load, and loads it
    private void loadCalendar() {
        System.out.println("Choose a calendar to load: ");

        listCalendars();

        String command = getInput();

        String fileLocation = parseLoadCommand(command);

        load(fileLocation);
    }

    // MODIFIES: this
    // EFFECTS: loads calendar at fileLocation
    private void load(String fileLocation) {
        try {
            calendarReader = new CalendarReader(fileLocation);
            calendar = calendarReader.read();

            jsonStore = fileLocation;

            System.out.println("Successfully loaded calendar: " + calendar.getName());

        } catch (IOException e) {
            System.out.println("Could not load file, reverting back");
        }
    }

    // EFFECTS: returns the calendar of command, or prompts user for a new input
    private String parseLoadCommand(String command) {
        try {
            return config.getFileLocation(command);
        } catch (FileNotFoundException e) {
            System.out.println("Please input a valid calendar");
            return parseLoadCommand(getInput());
        }
    }

    // EFFECTS: lists all calendars known to the app
    private void listCalendars() {
        System.out.println("Calendars: ");
        for (String string: config.getFiles().keySet()) {
            System.out.println("\t" + string + "\n");
        }
    }

    // EFFECTS: saves the calendar to file
    private void saveCalendar() {
        try {
            calendarWriter = new CalendarWriter(jsonStore);
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
        System.out.println("\n" + calendar.getName() + ":");

        for (Schedule i: calendar.getSchedules()) {
            System.out.println("\nSchedule: " + i.getName());
            printActivities(i);
        }

        printEvents(calendar.getEvents());
    }

    // EFFECTS: outputs activities in schedule, and all notes
    //          and events related to the activities
    private void printActivities(Schedule schedule) {
        for (Activity i: schedule.getActivities()) {
            System.out.println("\n Activity: " + i.getName());

            System.out.println(" Duration: " + i.getDuration() + " minutes");

            printDates(i.getDates());

            printEvents(i.getEvents());

            printNotes(i.getNotes());
        }
    }

    // EFFECTS: prints dates in a list
    private void printDates(List<Date> dates) {
        System.out.println(" On the days: ");
        for (Date d: dates) {
            System.out.println("\t" + d.getDate());
        }
    }

    // EFFECTS: prints out event name, date, time, and duration
    private void printEvents(List<Event> events) {
        System.out.println("\n Events:");
        for (Event e: events) {
            System.out.println("\t" + e.getName() + ":");
            System.out.println("\t\t" + e.getDate().getDate());
            System.out.println("\t\t" + e.getTime().get12HTime());
            if (e.getDuration() != 0) {
                System.out.println("\t\t" + e.getDuration() + " minutes");
            }
            System.out.println();
        }
    }

    // EFFECTS: prints out all notes
    private void printNotes(List<Note> notes) {
        System.out.println(" Notes:");
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
        System.out.println("\tc to edit calendars, or make a new one");
        System.out.println("\ts to view schedule");
        System.out.println("\ta to view activities");
        System.out.println("\te to view events");
        System.out.println("\tsave to save");
        System.out.println("\tload to load");
        System.out.println("\tq to quit");
    }
}
