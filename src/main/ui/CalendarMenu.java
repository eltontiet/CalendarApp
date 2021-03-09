package ui;

import model.Calendar;
import model.Config;
import persistence.CalendarWriter;
import persistence.ConfigWriter;

import java.io.FileNotFoundException;
import java.util.Map;

// Change the name of the calendar
public class CalendarMenu extends Menu {

    ConfigWriter configWriter;
    CalendarWriter calendarWriter;
    Config config;

    // EFFECTS: Initializes calendar and menu
    public CalendarMenu(Calendar calendar, Config config) {
        super(calendar);
        this.config = config;
        configWriter = new ConfigWriter(Config.CONFIG_FILE_LOCATION);
        printMenu();
        processInput();
    }

    // EFFECTS: Processes user input
    private void processInput() {
        String command = getInput();

        switch (command.toLowerCase()) {
            case "l":
                listCalendars();
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

    // EFFECTS: prints out the names of all the calendars
    private void listCalendars() {
        for (Map.Entry<String, String> f: config.getFiles().entrySet()) {
            System.out.println(f.getKey());
        }
    }

    // EFFECTS: prompts user for input again
    private void goBack() {
        printMenu();
        processInput();
    }

    // EFFECTS: prints out options that the user can choose
    @Override
    protected void printMenu() {
        super.printMenu();
        System.out.println("\tn to make a new calendar");
        System.out.println("\te to change the name of a calendar");
        System.out.println("\tr to delete a calendar");
        System.out.println("\tl to list all calendars");
        System.out.println("\tq to get back to main menu");
    }

    //TODO
    @Override
    protected void removeItem() {

    }

    @Override
    protected void editItem() {

    }

    @Override
    protected void makeItem() {
        Calendar newCalendar = makeCalendar();
        confirmCalendar(newCalendar);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to confirm the new calendar
    private void confirmCalendar(Calendar newCalendar) {
        System.out.println("Do you want to make this calendar: (y/n)");
        System.out.println("    name: " + newCalendar.getName());
        String command = getInput();

        if (command.equals("y")) {

            String fileName = getFileName(newCalendar);

            try {
                saveNewCalendar(newCalendar, fileName);

                config.addFile(newCalendar.getName(), fileName);

                System.out.println("Saving config and current calendar...");

                save();

                useNow(newCalendar, fileName);

                System.out.println("Success");

            } catch (FileNotFoundException e) {
                System.out.println("Could not create new calendar... Aborting");
            }
        } else if (command.equals("n")) {
            System.out.println("Aborting");
        } else {
            System.out.println("please input a valid command");
            confirmCalendar(newCalendar);
        }
    }

    // EFFECTS: asks user if the calendar should be edited now
    private void useNow(Calendar calendar, String fileName) {
        System.out.println("Do you want to edit the new calendar? (y/n)");
        String command = getInput();

        if (command.equals("y")) {
            config.setCurrentFile(fileName);
            try {
                configWriter.open();
                configWriter.write(config);
                configWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Could not update config file");
            }

            System.out.println("Now editing: " + calendar.getName());
        } else if (command.equals("n")) {
            System.out.println("Editing cancelled");
        } else {
            System.out.println("Please input a valid command.");
            useNow(calendar, fileName);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves new calendar to file
    private void saveNewCalendar(Calendar newCalendar, String fileName) throws FileNotFoundException {
        calendarWriter = new CalendarWriter(fileName);

        calendarWriter.open();
        calendarWriter.write(newCalendar);
        calendarWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: saves calendar and config to files
    private void save() {
        configWriter = new ConfigWriter(Config.CONFIG_FILE_LOCATION);
        calendarWriter = new CalendarWriter(config.getCurrentFile());

        try {
            configWriter.open();
            configWriter.write(config);
            configWriter.close();

            calendarWriter.open();
            calendarWriter.write(calendar);
            calendarWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not save files");
        }
    }

    // EFFECTS: prompts user to choose a file name
    private String getFileName(Calendar calendar) {
        String recommendedName = recommendName(calendar);
        System.out.println("Choose a file name, or press enter to use the default name: ");
        System.out.println("    " + recommendedName);
        String command = getInputEmpty();

        if (command.equals("")) {
            return "./data/" + recommendedName + ".json";
        } else {
            return "./data/" + command + ".json";
        }
    }

    // EFFECTS: gets input from user, but allows user to enter nothing
    private String getInputEmpty() {
        return input.nextLine();
    }

    // EFFECTS: returns a string with first letter lowercase, and no spaces
    private String recommendName(Calendar calendar) {
        String name = calendar.getName();
        name = name.substring(0,1).toLowerCase() + name.substring(1);
        name = name.replaceAll(" ", "");
        return name;
    }

    // EFFECTS: prompts user to input a calendar name, and
    //          returns a new calendar with that name
    private Calendar makeCalendar() {
        System.out.println("Enter the name of the calendar: ");
        String name = getInput();

        return new Calendar(name);
    }
}
