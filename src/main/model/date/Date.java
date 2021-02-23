package model.date;

import exceptions.DayCodeOutOfRangeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// TODO: Implement days of the week, and days between two dates
// Represents a date with a year, month, and day
public class Date {
    private int year;
    private int month;
    private int day;

    private final List<Integer> monthsWith28Days = new ArrayList<>(Collections.singletonList(2));
    private final List<Integer> monthsWith30Days = new ArrayList<>(Arrays.asList(4, 6, 9, 11));
    private final List<Integer> monthsWith31Days = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));


    // REQUIRES: year >= 0, 1 <= month <= 12, 1 <= day <= getDaysInMonth()
    // EFFECTS: the date's year is set to year,
    //          the date's month is set to month,
    //          the date's day is set to day,
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }


    // REQUIRES: 1 <= month <= 12
    // EFFECTS: returns number of days in a month
    public int getDaysInMonth() {
        if (monthsWith28Days.contains(month)) {
            return 28;
        } else if (monthsWith30Days.contains(month)) {
            return 30;
        } else {
            return 31;
        }
    }

    // getters
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    // setters
    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    // REQUIRES: 1000 <= year <= 9999
    // EFFECTS: returns the date in the form: yyyy-mm-dd
    public String getDate() {
        String yearString = Integer.toString(year);
        String monthString;
        String dayString;

        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = Integer.toString(month);
        }

        if (day < 10) {
            dayString = "0" + day;
        } else {
            dayString = Integer.toString(day);
        }

        return yearString + "-" + monthString + "-" + dayString;
    }

    // This method is based off of the sakamoto method:
    // https://en.wikipedia.org/wiki/Determination_of_the_day_of_the_week#Sakamoto's_methods
    // Using this to better understand it:
    // https://stackoverflow.com/questions/6385190/correctness-of-sakamotos-algorithm-to-find-the-day-of-week/6385934#6385934
    // EFFECTS: returns the day of the week from a date
    public String getDayOfWeek() {
        int day = this.day;
        int month = this.month;
        List<Integer> t = new ArrayList<>(Arrays.asList(0,3,2,5,0,3,5,1,4,6,2,4));
        int year = this.year;

        if (month < 3) {
            year -= 1;
        }

        int dayCode = year + year / 4 - year / 100 + year / 400 + t.get(month - 1) + day;
        dayCode = dayCode % 7;

        return getDayOfWeek(dayCode);
    }

    /*
    //EFFECTS: returns day of the week from a number between 0 and 6
    private String getDayOfWeek(int dayCode) throws DayCodeOutOfRangeException {
        switch (dayCode) {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                throw new DayCodeOutOfRangeException(); //TODO
        }
    }
    */

    //EFFECTS: returns day of the week from a number between 0 and 6
    private String getDayOfWeek(int dayCode) {
        switch (dayCode) {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            default:
                return "Saturday";
        }
    }

    // EFFECTS: returns whether or not this year is a leap year
    public Boolean isLeapYear() {
        return ((year % 4) == 0) && (!((year % 100) == 0) || ((year % 400) == 0));
    }
}
