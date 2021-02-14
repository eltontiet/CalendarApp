package ui;

import com.sun.xml.internal.bind.v2.TODO;
import model.*;

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
        String command = null;

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
        if (command.equals("c")) {
            new CalendarMenu(calendar);

        } else if (command.equals("s")) {
            new ScheduleMenu(calendar);

        } else if (command.equals("a")) {
            new ActivityMenu(calendar);

        } else if (command.equals("e")) {
            new EventMenu(calendar);

        } else if (command.equals("v")) {
            viewCalendar();

        } else {
            System.out.println("Please select a valid option.");
        }
    }

    // TODO: Sort into chronological order
    // EFFECTS: outputs everything scheduled in the next month based on activity,
    //          and asks if user wants to see the month after.
    private void viewCalendar() {

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
