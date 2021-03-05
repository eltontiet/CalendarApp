package ui;

import model.Calendar;
import model.Schedule;

import java.util.List;

// Change schedule name, add or remove schedules
public class ScheduleMenu extends Menu {
    List<Schedule> scheduleList;

    // MODIFIES: calendar
    // EFFECTS: Initializes calendar and schedule menu
    public ScheduleMenu(Calendar calendar) {
        super(calendar);
        scheduleList = calendar.getSchedules();
        printMenu();
        processInput();
    }

    // EFFECTS: Processes user input
    private void processInput() {
        String command = getInput();

        switch (command.toLowerCase()) {
            case "l":
                listSchedules(calendar);
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
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: lists out all schedules, then prompts user
    //          to choose what to remove from the list
    @Override
    protected void removeItem() {
        System.out.println("Choose the number of a schedule, or write the name of it");

        listSchedules(calendar);
        String command = getInput();
        removeSchedule(command);
    }

    // EFFECTS: removes the schedule from the list
    private void removeSchedule(String command) {
        Schedule schedule = getSchedule(command, calendar);

        if ((schedule == null)) {
            goBack();
        } else {
            scheduleList.remove(schedule);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays a list of items to choose from, then
    //          prompts user to edit it
    @Override
    protected void editItem() {
        System.out.println("Choose the number of a schedule, or write the name of it");
        listSchedules(calendar);
        String command = getInput();
        editSchedule(command);
    }

    // EFFECTS: prompts user to edit the schedule
    private void editSchedule(String command) {
        Schedule schedule = getSchedule(command, calendar);

        if (schedule == null) {
            goBack();
        } else {
            System.out.println("Select a new name for this schedule: ");
            confirmEdit(schedule, getInput());
        }
    }

    // EFFECTS: prompts user for confirmation on edit
    private void confirmEdit(Schedule schedule, String input) {
        System.out.println("Do you want to change the schedule to: " + input + "? (y/n)");
        String command = getInput();

        if (command.equals("y")) {
            schedule.setName(input);
            System.out.println("Success");
        } else if (command.equals("n")) {
            System.out.println("Aborting");
        } else {
            System.out.println("please input a valid command");
            confirmEdit(schedule, input);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to make item, and adds it to list
    @Override
    protected void makeItem() {
        Schedule schedule = makeSchedule();
        confirmSchedule(schedule);
    }

    // EFFECTS: prompts for a confirmation from the user
    private void confirmSchedule(Schedule schedule) {
        System.out.println("Do you want to make a new schedule: " + schedule.getName() + "? (y/n)");
        String command = getInput();

        if (command.equals("y")) {
            calendar.addSchedule(schedule);
            System.out.println("Success");
        } else if (command.equals("n")) {
            System.out.println("Aborting");
        } else {
            System.out.println("please input a valid command");
            confirmSchedule(schedule);
        }
    }

    // EFFECTS: prompts user to input name for schedule and
    //          add it to calendar.schedules
    private Schedule makeSchedule() {
        String name;
        System.out.println("Enter a name: ");
        name = getInput();
        return new Schedule(name);
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
        System.out.println("\tl to get a list of schedules");
        System.out.println("\tn to make a new schedule");
        System.out.println("\tr to remove a schedule from the calendar");
        System.out.println("\te to edit the name of a schedule");
        System.out.println("\tq to get back to main menu");
    }
}
