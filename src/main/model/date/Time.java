package model.date;

import exceptions.BadTimeFormattingException;

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

    // EFFECTS: if timeString is in form HH:MM, sets hour to HH and minute to MM
    //          otherwise throws BadTimeFormattingException
    public Time(String timeString) throws BadTimeFormattingException {
        setTimeFrom24H(timeString);
    }

    // EFFECTS: returns time in format: hh:mmXM, where X is either A or P
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
        String hourString;

        if (minute < 10) {
            minutesString = "0" + minute;
        } else {
            minutesString = Integer.toString(minute);
        }

        if (hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = Integer.toString(hour);
        }

        return hourString + ":" + minutesString;
    }

    // MODIFIES: this
    // EFFECTS: if timeString is in the form hh:mm, changes this
    //          time to timeString, else returns BadTimeFormattingException
    public void setTimeFrom24H(String timeString) throws BadTimeFormattingException {
        int hour;
        int minute;

        checkFormat(timeString);

        hour = getHourFromString(timeString);
        minute = getMinuteFromString(timeString);

        this.hour = hour;
        this.minute = minute;
    }

    private void checkFormat(String timeString) throws BadTimeFormattingException {
        if (timeString.length() != 5) {
            throw new BadTimeFormattingException();
        }

        if (timeString.charAt(2) != ':') {
            throw new BadTimeFormattingException();
        }
    }

    // EFFECTS: if timeString is in the form hh:mm, returns mm.
    //          Otherwise throws BadTimeFormattingException
    private int getMinuteFromString(String timeString) throws BadTimeFormattingException {
        int minute;

        try {
            minute = Integer.parseInt(timeString.substring(3,5));

        } catch (NumberFormatException e) {
            throw new BadTimeFormattingException();
        }

        if (!(minute >= 0 && minute <= 59)) {
            throw new BadTimeFormattingException();
        }

        return minute;
    }

    // TODO: add different exceptions for hour and minute exceptions
    // EFFECTS: if timeString is in the form hh:mm, returns hh.
    //          Otherwise throws BadTimeFormattingException
    private int getHourFromString(String timeString) throws BadTimeFormattingException {
        int hour;
        try {
            hour = Integer.parseInt(timeString.substring(0,2));
        } catch (NumberFormatException e) {
            throw new BadTimeFormattingException();
        }

        if (!(hour >= 0 && hour <= 23)) {
            throw new BadTimeFormattingException();
        } else {
            return hour;
        }
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
