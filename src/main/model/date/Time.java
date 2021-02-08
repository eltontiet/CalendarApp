package model.date;

// Represents the time in hours and minutes in the 24h clock
public class Time {
    private int hours;
    private int minutes;

    // REQUIRES: 0 <= hours <= 23, 0 <= minutes <= 59
    // EFFECTS: the time's hours is set to hours,
    //          the time's minutes is set to minutes,
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    // EFFECTS: returns time in format: hh:mmXM
    public String get12HTime() {
        return "";
    }

    // EFFECTS: returns time in format: hh:mm
    public String get24HTime() {
        return "";
    }

    // ??
    public int getHours() {
        return 0;
    }

    // ??
    public int getMinutes() {
        return 0;
    }
}
