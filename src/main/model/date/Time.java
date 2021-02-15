package model.date;

// Represents the time in hours and minutes in the 24h clock
public class Time {
    private int hour;
    private int minute;

    // REQUIRES: 0 <= hours <= 23, 0 <= minutes <= 59
    // EFFECTS: the time's hours is set to hours,
    //          the time's minutes is set to minutes,
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // EFFECTS: returns time in format: hh:mmXM
    public String get12HTime() {
        String minutesString;

        if (minute < 10) {
            minutesString = "0" + minute;
        } else {
            minutesString = Integer.toString(minute);
        }

        if (hour > 12) {
            return (hour - 12) + ":" + minutesString + "PM";
        } else if (hour == 12) {
            return "12:" + minutesString + "PM";
        } else if (hour == 0) {
            return "12:" + minutesString + "AM";
        } else {
            return hour + ":" + minutesString + "AM";
        }
    }

    // EFFECTS: returns time in format: hh:mm
    public String get24HTime() {
        String minutesString;

        if (minute < 10) {
            minutesString = "0" + minute;
        } else {
            minutesString = Integer.toString(minute);
        }

        return hour + ":" + minutesString;
    }

    // getters
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    // setters
    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
