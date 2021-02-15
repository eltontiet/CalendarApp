package ui;

import model.*;
import model.date.Date;

import java.util.List;
import java.util.Scanner;

/*
Based off of TellerApp to understand how to get user input, and set it up.
 */

// An organization app
public class OrganizationApp {
    private Calendar calendar;
    private Scanner input;

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
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                quit = true;
            } else {
                chooseNext(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes calendar
    private void init() {
        calendar = new Calendar("My Calendar");
        input = new Scanner(System.in);
    }

    // EFFECTS: decides what to do with the input
    private void chooseNext(String command) {
        switch (command) {
            case "c":
                new CalendarMenu(calendar);
                break;
            case "s":
                new ScheduleMenu(calendar);
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

    // TODO: Sort into chronological order, Add a date cap (Users don't have to see something 2 years down the line)
    // EFFECTS: outputs everything scheduled in
    //          current calendar based on activity.
    private void viewCalendar() {
        for (Schedule i: calendar.getSchedules()) {
            System.out.println("Schedule: " + i.getName());
            printActivities(i);
        }
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
        System.out.println("\tc to overview calendar");
        System.out.println("\ts to view schedule");
        System.out.println("\ta to view activities");
        System.out.println("\te to view events");
        System.out.println("\tq to quit");
    }
}
