package model.date;

// Represents the time in hours and minutes in the 24h clock
public class Time {
    private final int hours;
    private final int minutes;

    // REQUIRES: 0 <= hours <= 23, 0 <= minutes <= 59
    // EFFECTS: the time's hours is set to hours,
    //          the time's minutes is set to minutes,
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    // EFFECTS: returns time in format: hh:mmXM
    public String get12HTime() {
        String minutesString;

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = Integer.toString(minutes);
        }

        if (hours > 12) {
            return (hours - 12) + ":" + minutesString + "PM";
        } else if (hours == 12) {
            return "12:" + minutesString + "PM";
        } else if (hours == 0) {
            return "12:" + minutesString + "AM";
        } else {
            return hours + ":" + minutesString + "AM";
        }
    }

    // EFFECTS: returns time in format: hh:mm
    public String get24HTime() {
        String minutesString;

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = Integer.toString(minutes);
        }

        return hours + ":" + minutesString;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }
}
