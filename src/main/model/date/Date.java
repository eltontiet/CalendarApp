package model.date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

// TODO: Implement leap years, days of the week, and days between two dates
// Represents a date with a year, month, and day
public class Date {
    private int year;
    private int month;
    private int day;


    private final List<Integer> monthsWith28Days = new ArrayList<>(Arrays.asList(2));
    private final List<Integer> monthsWith30Days = new ArrayList<>(Arrays.asList(4, 6, 9, 11));
    private final List<Integer> monthsWith31Days = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));


    // REQUIRES: year >= 0, 1 <= month <= 12, 1 <= day <= maxDays
    // EFFECTS: the date's year is set to year,
    //          the date's month is set to month,
    //          the date's day is set to day,
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        int maxDays = getDaysInMonth();
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

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

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
}
