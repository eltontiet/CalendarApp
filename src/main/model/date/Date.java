package model.date;

// Represents a date with a year, month, and day
public class Date {
    private int year;
    private int month;
    private int day;



    // REQUIRES: year >= 0, 1 <= month <= 12, 1 <= day <= maxDays
    // EFFECTS: the date's year is set to year,
    //          the date's month is set to month,
    //          the date's day is set to day,
    public Date(int year, int month, int day) {
        int maxDays = getDaysInMonth(month);
        this.year = year;
        this.month = month;
        this.day = day;
    }


    // REQUIRES: 1 <= month <= 12
    // EFFECTS: returns number of days in a month
    public int getDaysInMonth(int month) {
        return 0;
    }

    public int getYear() {
        return 0;
    }

    public int getMonth() {
        return 0;
    }

    public int getDay() {
        return 0;
    }

    public int setYear() {
        return 0;
    }

    public int setMonth() {
        return 0;
    }

    public int setDay() {
        return 0;
    }

    // EFFECTS: returns the date in the form: yyyy-mm-dd
    public String getDate() {
        return "";
    }
}
