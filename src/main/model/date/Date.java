package model.date;

import exceptions.*;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a date with a year, month, and day
public class Date implements Writable {
    private int year;
    private int month;
    private int day;

    private static final int FEBRUARY = 2;
    private static final List<Integer> MONTHS_WITH_30_DAYS = new ArrayList<>(Arrays.asList(4, 6, 9, 11));
    //private static final List<Integer> monthsWith31Days = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));


    // REQUIRES: year >= 0, 1 <= month <= 12, 1 <= day <= getDaysInMonth()
    // EFFECTS: the date's year is set to year,
    //          the date's month is set to month,
    //          the date's day is set to day,
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    // EFFECTS: If dateString is in form yyyy-mm-dd, and is a legal date,
    //          sets date from dateString; otherwise throws a DateException
    public Date(String dateString) throws DateException {
        setFromString(dateString);
    }


    // REQUIRES: 1 <= month <= 12
    // EFFECTS: returns number of days in a month
    public int getDaysInMonth() {
        if (FEBRUARY == month) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        } else if (MONTHS_WITH_30_DAYS.contains(month)) {
            return 30;
        } else {
            return 31;
        }
    }

    // REQUIRES: 1 <= month <= 12
    // EFFECTS: returns number of days in a month
    public int getDaysInMonth(int month, int year) {
        if (FEBRUARY == month) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        } else if (MONTHS_WITH_30_DAYS.contains(month)) {
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

        if (year < 10) {
            yearString = "000" + yearString;
        } else if (year < 100) {
            yearString = "00" + yearString;
        } else if (year < 1000) {
            yearString = "0" + yearString;
        }

        return yearString + "-" + monthString + "-" + dayString;
    }

    // MODIFIES: this
    // EFFECTS: if dateString is in form yyyy-mm-dd, sets this date to dateString
    //          else, throws DateFormatException
    public void setFromString(String dateString) throws DateException {
        checkStringFormat(dateString);

        int year = getYearFromString(dateString);
        int month = getMonthFromString(dateString);
        int day = getDayFromString(dateString, year, month);

        this.year = year;
        this.month = month;
        this.day = day;
    }

    // EFFECTS: returns the day from string if dd is a number between 1 and
    //          daysInMonth(year,month), otherwise throws a DateFormatException
    //          if day is not a number, or throws DayRangeException if day is outside range
    private int getDayFromString(String dateString, int year, int month) throws DateFormatException, DayRangeException {
        int day;

        try {
            day = Integer.parseInt(dateString.substring(8,10));

        } catch (NumberFormatException e) {
            throw new DateFormatException();
        }

        if (!(day > 0 && day < getDaysInMonth(year, month))) {
            throw new DayRangeException();
        }

        return day;
    }

    // EFFECTS: returns the month from string if mm is a number between 1 and
    //          12. otherwise, throws a DateFormatException if month is not a number,
    //          or throws MonthRangeException if month is outside range
    private int getMonthFromString(String dateString) throws DateFormatException, MonthRangeException {
        int month;

        try {
            month = Integer.parseInt(dateString.substring(5,7));

        } catch (NumberFormatException e) {
            throw new DateFormatException();
        }

        if (!(month > 0 && month <= 12)) {
            throw new MonthRangeException();
        }

        return month;
    }

    // EFFECTS: returns the year from string if yyyy is a number between 1 and 9999,
    //          otherwise throws a DateFormatException if year is not a number
    private int getYearFromString(String dateString) throws DateFormatException {
        int year;

        try {
            year = Integer.parseInt(dateString.substring(0,4));

        } catch (NumberFormatException e) {
            throw new DateFormatException();
        }

        return year;
    }

    // EFFECTS: throws DateFormatException if string is not in form: yyyy-mm-dd
    private void checkStringFormat(String dateString) throws DateFormatException {
        if (!(dateString.length() == 10)) {
            throw new DateFormatException();
        }

        if (dateString.charAt(4) != '-' || dateString.charAt(7) != '-') {
            throw new DateFormatException();
        }
    }

    // EFFECTS: returns the number of days till date, not including this date.
    //          if date is at a later date than this, PastException is thrown.
    public Integer getDaysTill(Date date) throws PastException {
        Date currentDate = new Date(year, month, day);
        int daysBetween = 0;

        if (isLater(date, currentDate)) {
            throw new PastException();
        }

        if (currentDate.getYear() < date.getYear()) {

            daysBetween += currentDate.getDaysInMonth() - currentDate.getDay();

            for (int i = currentDate.getMonth() + 1; i <= 12; i++) {
                daysBetween += currentDate.getDaysInMonth(i, currentDate.getYear());
            }

            currentDate.setYear(currentDate.getYear() + 1);
            currentDate.setMonth(1);
            currentDate.setDay(1);

        } else if (currentDate.getMonth() < date.getMonth()) {
            daysBetween += currentDate.getDaysInMonth() - currentDate.getDay();

            currentDate.setMonth(currentDate.getMonth() + 1);
            currentDate.setDay(1);
        }

        daysBetween += currentDate.getDaysBetweenYears(date);
        currentDate.setYear(date.getYear());

        daysBetween += currentDate.getDaysBetweenMonths(date);

        daysBetween += date.getDay() - currentDate.getDay();

        return daysBetween;
    }

    // REQUIRES: this and date2 have the same year
    // EFFECTS: returns the months between the months of the two dates
    private int getDaysBetweenMonths(Date date2) {
        int daysBetween = 0;

        for (int i = getMonth(); i < date2.getMonth(); i++) {
            daysBetween += getDaysInMonth(i, year);
        }

        return daysBetween;
    }

    // EFFECTS: returns the days between the years of the two dates
    private Integer getDaysBetweenYears(Date date2) {
        int daysBetween = 0;

        for (int i = year; i < date2.getYear(); i++) {
            if (isLeapYear(i)) {
                daysBetween += 366;
            } else {
                daysBetween += 365;
            }
        }

        return daysBetween;
    }

    // EFFECTS: returns whether date is on a date after currentDate
    private boolean isLater(Date currentDate, Date date) {
        int year1 = currentDate.getYear();
        int year2 = date.getYear();
        int month1 = currentDate.getMonth();
        int month2 = date.getMonth();
        int day1 = currentDate.getDay();
        int day2 = date.getDay();

        if (year1 > year2) {
            return false;
        } else if (year1 == year2 && month1 > month2) {
            return false;
        } else {
            return year1 != year2 || month1 != month2 || day1 <= day2;
        }
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
        dayCode = Math.abs(dayCode) % 7;

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
                throw new DayCodeOutOfRangeException();
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
    public Boolean isLeapYear(int year) {
        return ((year % 4) == 0) && (!((year % 100) == 0) || ((year % 400) == 0));
    }

    // EFFECTS: returns this date as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("date", getDate());

        return json;
    }
}
