package ui;

import model.*;
import model.date.Date;
import model.date.Time;

import java.util.ArrayList;
import java.util.List;

// Change current activities, add or remove new activities to the calendar
public class ActivityMenu extends Menu {
    private Schedule schedule;

    // EFFECTS: Initializes calendar and activity menu
    public ActivityMenu(Calendar calendar) {
        super(calendar);
        getActivityList();
        printMenu();
        processInput();
    }

    // MODIFIES: this
    // EFFECTS: initializes activityList
    private void getActivityList() {
        String input;
        System.out.println("Choose a schedule");
        listSchedules(calendar);
        input = getInput();
        chooseSchedule(input);
    }

    // EFFECTS: gets the schedule corresponding to input
    //          and goes to chooseActivity()
    private void chooseSchedule(String input) {
        Schedule schedule = getSchedule(input, calendar);

        if (schedule == null) {
            getActivityList();
        } else {
            this.schedule = schedule;
        }
    }

    // EFFECTS: Processes user input
    private void processInput() {
        String command = getInput();

        switch (command.toLowerCase()) {
            case "v":
                processViewActivity();
                break;
            case "l":
                listActivities(schedule);
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
    private void processViewActivity() {
        Activity activity;
        System.out.println("Choose the number of an activity, or write the name of it");
        listActivities(schedule);
        String command = getInput();

        activity = getActivity(command, schedule);

        if (activity == null) {
            goBack();
        } else {
            printActivity(activity);
        }
    }

    // MODIFIES: this
    // EFFECTS: lists out all activities, then prompts user
    //          to choose what to remove from the list
    @Override
    protected void removeItem() {
        System.out.println("Choose the number of an activity, or write the name of it");
        listActivities(schedule);
        String command = getInput();
        removeActivity(command);
    }

    // MODIFIES: this
    // EFFECTS: removes the activity from the list
    private void removeActivity(String command) {
        Activity activity;
        activity = getActivity(command, schedule);

        if ((activity == null)) {
            goBack();
        } else {
            schedule.removeActivity(activity);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays a list of items to choose from, then
    //          prompts user to edit it
    @Override
    protected void editItem() {
        System.out.println("Choose the number of an activity, or write the name of it");
        listActivities(schedule);
        String command = getInput();
        editActivity(command);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to edit the activity
    private void editActivity(String command) {
        Activity activity = getActivity(command, schedule);

        if (activity == null) {
            goBack();
        } else {
            chooseEdit(activity);
        }
    }

    // EFFECTS: prompts user for what to edit, and allows editing
    private void chooseEdit(Activity activity) {
        printEditMenu();
        getEditCommand(activity);
    }

    // EFFECTS: processes input, and sets up an activity to edit
    private void getEditCommand(Activity activity) {
        String input = getInput();
        Activity newActivity = new Activity("", new Time(0,0), 0);
        newActivity.setActivity(activity);

        chooseEditActivity(newActivity, input);

        confirmEdit(activity, newActivity);
    }

    // EFFECTS: selects what is going to be edited based on input
    private void chooseEditActivity(Activity newActivity, String input) {
        switch (input) {
            case "n":
                editName(newActivity);
                break;
            case "d":
                editDates(newActivity);
                break;
            case "t":
                editTime(newActivity);
                break;
            case "l":
                editLength(newActivity);
                break;
            case "notes":
                editNotes(newActivity);
                break;
            case "q":
                break;
            default:
                System.out.println("Please input a valid command");
        }
    }

    // MODIFIES: this
    // EFFECTS: gives user the option to add, edit, or remove notes.
    private void editNotes(Activity newActivity) {
        printEditNoteMenu();
        processEditNoteInput(newActivity);
    }

    // EFFECTS: outputs menu options
    private void printEditNoteMenu() {
        super.printMenu();
        System.out.println("\tn to add a note");
        System.out.println("\te to edit a note");
        System.out.println("\tr to remove a note");
        System.out.println("\tq to go back");
    }

    // EFFECTS: processes user input
    private void processEditNoteInput(Activity newActivity) {
        String command = getInput();
        switch (command) {
            case "n":
                makeNote(newActivity);
                break;
            case "e":
                editNote(newActivity);
                break;
            case "r":
                removeNote(newActivity);
                break;
            case "q":
                break;
            default:
                System.out.println("Please input a valid command");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to choose a note and remove it
    private void removeNote(Activity newActivity) {
        String input;
        int index;
        Note note = null;

        System.out.println("Select a note based on the number beside it");
        listNotes(newActivity);

        input = getInput();
        index = inputToInt(input);

        if (index < newActivity.getNotes().size()) {
            note = newActivity.getNotes().get(index);
        } else {
            System.out.println("Please provide a valid input");
            editNotes(newActivity);
        }

        newActivity.removeNote(note);
    }


    // MODIFIES: this
    // EFFECTS: prompts user to choose a note and edit it
    private void editNote(Activity newActivity) {
        String input;
        int index;
        Note note;

        System.out.println("Select a note based on the number beside it");
        listNotes(newActivity);

        input = getInput();
        index = inputToInt(input) - 1;

        if (index < newActivity.getNotes().size() && !(index < 0)) {
            note = newActivity.getNotes().get(index);
            editTitle(note);
            editBody(note);
        } else {
            System.out.println("Please provide a valid input");
            editNote(newActivity);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to either keep title, or change it
    private void editTitle(Note note) {
        System.out.println("\nDo you want to change the title of the note? " + note.getTitle() + ":");
        System.out.println("(y/n)");

        if (getInput().equals("y")) {
            System.out.println("Now Editing: \n");
            note.editTitle(getInput());
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to either keep body, or change it
    private void editBody(Note note) {
        System.out.println("\nDo you want to change the body of the note? (y/n)");
        System.out.println("\t" + note.getBody());

        if (getInput().equals("y")) {
            System.out.println("Now Editing: \n");
            note.editBody(getInput());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new note and puts it into newActivity
    private void makeNote(Activity newActivity) {
        newActivity.addNote(inputNote());
    }

    // EFFECTS: outputs title of notes in newActivity
    //          with a number corresponding to its index
    private void listNotes(Activity newActivity) {
        List<String> list = new ArrayList<>();

        for (Note n: newActivity.getNotes()) {
            list.add(n.getTitle());
        }
        listItem(list);
    }

    // MODIFIES: this
    // EFFECTS: sets duration in newActivity to a new inputted value
    private void editLength(Activity newActivity) {
        newActivity.setDuration(inputDuration());
    }

    // MODIFIES: this
    // EFFECTS: sets time in newActivity to a new inputted value
    private void editTime(Activity newActivity) {
        newActivity.setTime(inputTime());
    }

    // MODIFIES: this
    // EFFECTS: gives user the option to add, edit, or remove dates.
    private void editDates(Activity newActivity) {
    }

    // MODIFIES: this
    // EFFECTS: sets name in newEvent to a new inputted value
    private void editName(Activity newActivity) {
        newActivity.setName(inputName());
    }

    // EFFECTS: outputs options for editing
    private void printEditMenu() {
        System.out.println("\nWhat do you want to edit?");
        System.out.println("\tn for name");
        System.out.println("\td for date (not yet implemented)");
        System.out.println("\tt for time");
        System.out.println("\tl for length of the event (duration)");
        System.out.println("\tnotes to edit notes");
        System.out.println("\tq to go back");
    }

    // EFFECTS: confirmation for editing an event
    private void confirmEdit(Activity activity, Activity newActivity) {
        System.out.println("\nDo you want to change the event from: ");
        printActivity(activity);

        System.out.println("to: ");
        printActivity(newActivity);

        System.out.println("(y/n)");
        String command = getInput();

        if (command.equals("y")) {
            activity.setActivity(newActivity);
            System.out.println("Success");
        } else if (command.equals("n")) {
            System.out.println("Aborting");
        } else {
            System.out.println("please input a valid command");
            confirmEdit(activity, newActivity);
        }
    }

    // MODIFIES: this
    // EFFECTS: Asks user for confirmation about the new activity
    private void confirmActivity(Activity activity) {
        System.out.println("\nDo you want to make a new activity: ");
        System.out.println("\t" + activity.getName());
        System.out.println("(y/n)");

        String command = getInput();

        if (command.equals("y")) {
            schedule.addActivity(activity);
            System.out.println("Success");
        } else if (command.equals("n")) {
            System.out.println("Aborting");
        } else {
            System.out.println("please input a valid command");
            confirmActivity(activity);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to make item, and adds it to list
    @Override
    protected void makeItem() {
        Activity activity = makeActivity();
        confirmActivity(activity);
    }

    // TODO: reduce duplication on inputs
    // EFFECTS: prompts user to input name for name, dates
    //          time, duration, and note, and returns the new activity
    private Activity makeActivity() {
        String name;
        List<Date> dates;
        Time time;
        int duration;

        name = inputName();

        dates = inputDates();

        time = inputTime();

        duration = inputDuration();

        Activity activity = new Activity(name, time, duration);

        for (Date d: dates) {
            activity.addDate(d);
        }

        return activity;
    }

    // EFFECTS: prompts user to make a note, and returns it
    private Note inputNote() {
        String title;
        String body;

        System.out.println("What is the title of this note? \n");
        title = getInput();

        System.out.println("What is in the body of the note? \n");
        body = getInput();

        return new Note(title, body);
    }

    // EFFECTS: prompts the user for the duration of the activity
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

    // EFFECTS: prompts the user for the time of the activity
    //          and returns it
    private Time inputTime() {
        int hour = getHour();
        int minute = getMinute();
        return new Time(hour,minute);
    }

    // EFFECTS: prompts the user for the minute that
    //          the activity takes place
    private int getMinute() {
        System.out.println("At what minute does the activity take place? ");
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
        System.out.println("At what hour does the activity take place? (24H time)");
        String input = getInput();
        int i = inputToInt(input);

        if (i < 0 || i > 23) {
            System.out.println("Please input an integer between 0 and 23 ");
            return getHour();
        } else {
            return i;
        }
    }

    // EFFECTS: prompts the user for the date of the activity
    private Date inputDate() {
        int year = getYear();
        int month = getMonth();
        int day = getDay(month);
        return new Date(year, month, day);
    }

    // EFFECTS: continues asking the user for more dates until they quit
    private List<Date> inputDates() {
        List<Date> dates = new ArrayList<>();
        boolean quit = false;

        while (!quit) {
            dates.add(inputDate());
            System.out.println("Do you want to add another date? (y/n)");
            String input = getInput();
            if (!input.equals("y")) {
                quit = true;
            }
        }
        return dates;
    }

    // EFFECTS: prompts the user for the day that
    //          the activity takes place
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

    // EFFECTS: prompts the user for the hour that
    //          the activity takes place
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
    //          the activity takes place
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

    // EFFECTS: prompts the user for the name of the activity
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
        System.out.println("\tv to view details of an activity");
        System.out.println("\tl to get a list of activities in the schedule");
        System.out.println("\tn to make a new activity in the schedule");
        System.out.println("\tr to remove an activity from the schedule");
        System.out.println("\te to edit an activity");
        System.out.println("\tq to get back to main menu");
    }
}
