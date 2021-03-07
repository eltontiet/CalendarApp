package model;

import exceptions.*;
import model.date.Date;
import model.date.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {
    Date date;

    @BeforeEach
    void setup() {
        date = new Date(2021, 1, 1);
    }

    @Test
    void testGetDaysInMonth()  {
        assertEquals(31, date.getDaysInMonth());

        date.setMonth(2);
        assertEquals(28, date.getDaysInMonth());

        date.setYear(2020);
        assertEquals(29, date.getDaysInMonth());

        date.setMonth(3);
        assertEquals(31, date.getDaysInMonth());

        date.setMonth(4);
        assertEquals(30, date.getDaysInMonth());

        date.setMonth(5);
        assertEquals(31, date.getDaysInMonth());

        date.setMonth(6);
        assertEquals(30, date.getDaysInMonth());

        date.setMonth(7);
        assertEquals(31, date.getDaysInMonth());

        date.setMonth(8);
        assertEquals(31, date.getDaysInMonth());

        date.setMonth(9);
        assertEquals(30, date.getDaysInMonth());

        date.setMonth(10);
        assertEquals(31, date.getDaysInMonth());

        date.setMonth(11);
        assertEquals(30, date.getDaysInMonth());

        date.setMonth(12);
        assertEquals(31, date.getDaysInMonth());
    }

    @Test
    void testGetDaysOfMonthInYear()  {
        assertEquals(31, date.getDaysInMonth(1,2020));

        date.setMonth(2);
        assertEquals(29, date.getDaysInMonth(2,2020));
        assertEquals(28, date.getDaysInMonth(2,2021));

        date.setMonth(3);
        assertEquals(31, date.getDaysInMonth(3,2020));

        date.setMonth(4);
        assertEquals(30, date.getDaysInMonth(4,2020));

        date.setMonth(5);
        assertEquals(31, date.getDaysInMonth(5,2020));

        date.setMonth(6);
        assertEquals(30, date.getDaysInMonth(6,2020));

        date.setMonth(7);
        assertEquals(31, date.getDaysInMonth(7,2020));

        date.setMonth(8);
        assertEquals(31, date.getDaysInMonth(8,2020));

        date.setMonth(9);
        assertEquals(30, date.getDaysInMonth(9,2020));

        date.setMonth(10);
        assertEquals(31, date.getDaysInMonth(10,2020));

        date.setMonth(11);
        assertEquals(30, date.getDaysInMonth(11,2020));

        date.setMonth(12);
        assertEquals(31, date.getDaysInMonth(12,2020));
    }

    @Test
    void testGetDate() {
        Date date2 = new Date(1970, 1, 1);
        Date date3 = new Date(2002, 12, 26);
        Date date4 = new Date(1815, 12, 10);
        Date date5 = new Date(1996, 1, 23);
        Date date6 = new Date(1000, 10, 10);
        Date date7 = new Date(1000, 9, 10);
        Date date8 = new Date(9999, 10, 9);

        assertEquals("2021-01-01", date.getDate());
        assertEquals("1970-01-01", date2.getDate());
        assertEquals("2002-12-26", date3.getDate());
        assertEquals("1815-12-10", date4.getDate());
        assertEquals("1996-01-23", date5.getDate());
        assertEquals("1000-10-10", date6.getDate());
        assertEquals("1000-09-10", date7.getDate());
        assertEquals("9999-10-09", date8.getDate());
    }

    @Test
    void testGetYear() {
        assertEquals(2021, date.getYear());
    }

    @Test
    void testGetMonth() {
        assertEquals(1, date.getMonth());
    }

    @Test
    void testGetDay() {
        assertEquals(1, date.getDay());
    }

    @Test
    void testSetYear() {
        date.setYear(2020);
        assertEquals(2020, date.getYear());
    }

    @Test
    void testSetDay() {
        date.setDay(12);
        assertEquals(12, date.getDay());
    }

    @Test
    void testGetDayOfWeek() {
        Date date2 = new Date(2020, 12, 26);
        Date date3 = new Date(2022, 2, 20);
        Date date4 = new Date(2022, 6, 13);
        Date date5 = new Date(2022, 5, 10);
        Date date6 = new Date(2021, 2, 24);
        Date date7 = new Date(2021, 4, 1);

        assertEquals("Friday", date.getDayOfWeek());
        assertEquals("Saturday", date2.getDayOfWeek());
        assertEquals("Sunday", date3.getDayOfWeek());
        assertEquals("Monday", date4.getDayOfWeek());
        assertEquals("Tuesday", date5.getDayOfWeek());
        assertEquals("Wednesday", date6.getDayOfWeek());
        assertEquals("Thursday", date7.getDayOfWeek());
    }

    @Test
    void testIsLeapYear() {

        assertFalse(date.isLeapYear(date.getYear()));
        assertTrue(date.isLeapYear(2000));
        assertTrue(date.isLeapYear(2004));
        assertFalse(date.isLeapYear(2100));
    }

    @Test
    void testGetDaysTill() throws PastException { // TODO
        Date date2 = new Date(2021,1,15);
        assertEquals(14, date.getDaysTill(date2));

        date2 = new Date(2025,1,1);
        assertEquals(1460,date.getDaysTill(date2));

        date2 = new Date(2025,12,1);
        assertEquals(1794,date.getDaysTill(date2));

        date2 = new Date(2025,12,18);
        assertEquals(1811,date.getDaysTill(date2));

        date2 = new Date(2021,12,18);
        assertEquals(350,date.getDaysTill(date2));

        date2 = new Date(2021,2,1);
        assertEquals(30,date.getDaysTill(date2));

        date2 = new Date(2020,12,18);
        Date finalDate1 = date2;
        assertThrows(PastException.class,() -> date.getDaysTill(finalDate1));

        date = new Date(2021,12,31);
        date2 = new Date(2021,10,31);
        Date finalDate2 = date2;
        assertThrows(PastException.class,() -> date.getDaysTill(finalDate2));

        date2 = new Date(2021,12,18);
        Date finalDate3 = date2;
        assertThrows(PastException.class,() -> date.getDaysTill(finalDate3));
    }

    @Test
    void TestBadStringSetDateFromString() {
        try {
            date.setFromString("This is illegal");
            fail("This should fail");
        } catch (DateFormatException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestBadYearStringSetDateFromString() {
        try {
            date.setFromString("20a2-01-01");
            fail("This should fail");
        } catch (DateFormatException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestBadMonthStringSetDateFromString() {
        try {
            date.setFromString("2022-r1-01");
            fail("This should fail");
        } catch (DateFormatException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestBadDayStringSetDateFromString() {
        try {
            date.setFromString("2022-01-0r");
            fail("This should fail");
        } catch (DateFormatException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestMissingFirstDashSetDateFromString() {
        try {
            date.setFromString("2022201-02");
            fail("This should fail");
        } catch (DateFormatException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestMissingSecondDashSetDateFromString() {
        try {
            date.setFromString("2022-01302");
            fail("This should fail");
        } catch (DateFormatException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestBigMonthSetDateFromString() {
        try {
            date.setFromString("2022-13-02");

            fail("Should throw an error");
        } catch (MonthRangeException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestZeroMonthSetDateFromString() {
        try {
            date.setFromString("2022-00-02");

            fail("Should throw an error");
        } catch (MonthRangeException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestBigDaySetDateFromString() {
        try {
            date.setFromString("2022-01-32");

            fail("Should throw error");
        } catch (DayRangeException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestZeroDaySetDateFromString() {
        try {
            date.setFromString("2022-01-00");

            fail("Should throw error");
        } catch (DayRangeException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestBadFebruaryDaySetDateFromString() {
        try {
            date.setFromString("2022-02-29");
        } catch (DayRangeException e) {
            // pass
        } catch (DateException e) {
            fail("Throwing wrong error");
        }
    }

    @Test
    void TestSetDateFromString() {
        try {
            date.setFromString("2022-01-02");
            assertEquals(date.getYear(), 2022);
            assertEquals(date.getMonth(), 1);
            assertEquals(date.getDay(), 2);

            date.setFromString("0199-11-22");
            assertEquals(date.getYear(), 199);
            assertEquals(date.getMonth(), 11);
            assertEquals(date.getDay(), 22);

            date.setFromString("2020-02-29");
            assertEquals(date.getYear(), 2020);
            assertEquals(date.getMonth(), 2);
            assertEquals(date.getDay(), 29);

        } catch (DateException e) {
            fail("No error should be thrown");
        }
    }
}

class TimeTest {
    Time time1;
    Time time2;
    Time time3;
    Time time4;
    Time time5;

    @BeforeEach
    void setup() {
        time1 = new Time(23, 8);
        time2 = new Time(13, 31);
        time3 = new Time(12, 0);
        time4 = new Time(0, 0);
        time5 = new Time(11, 45);
    }

    @Test
    void testGet12HTime() {
        assertEquals("11:08PM", time1.get12HTime());
        assertEquals("1:31PM", time2.get12HTime());
        assertEquals("12:00PM", time3.get12HTime());
        assertEquals("12:00AM", time4.get12HTime());
        assertEquals("11:45AM", time5.get12HTime());
    }

    @Test
    void testGet24HTime() {
        assertEquals("23:08", time1.get24HTime());
        assertEquals("13:31", time2.get24HTime());
        assertEquals("12:00", time3.get24HTime());
        assertEquals("00:00", time4.get24HTime());
        assertEquals("11:45", time5.get24HTime());
    }

    @Test
    void testGetHour() {
        assertEquals(23, time1.getHour());
        assertEquals(13, time2.getHour());
        assertEquals(12, time3.getHour());
        assertEquals(0, time4.getHour());
        assertEquals(11, time5.getHour());
    }

    @Test
    void testGetMinute() {
        assertEquals(8, time1.getMinute());
        assertEquals(31, time2.getMinute());
        assertEquals(0, time3.getMinute());
        assertEquals(0, time4.getMinute());
        assertEquals(45, time5.getMinute());
    }

    @Test
    void testSetHour() {
        time1.setHour(5);
        assertEquals(5, time1.getHour());
    }

    @Test
    void testSetMinute() {
        time1.setMinute(30);
        assertEquals(30, time1.getMinute());
    }

    @Test
    void testSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("12:20");
            assertEquals("12:20", time1.get24HTime());
        } catch (BadTimeFormattingException e) {
            fail("Should work");
        }
    }

    @Test
    void testBadFormatSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("This is a very bad time");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }

    @Test
    void testNoColonSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("12525");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }

    @Test
    void testBigHourSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("24:25");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }

    @Test
    void testNegativeHourSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("-1:25");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }

    @Test
    void testBadHourSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("1r:25");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }

    @Test
    void testBigMinuteSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("16:60");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }

    @Test
    void testNegativeMinuteSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("12:-5");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }

    @Test
    void testBadMinuteSetTimeFrom24H() {
        try {
            time1.setTimeFrom24H("16:5r");
            fail("Should throw an exception");
        } catch (BadTimeFormattingException e) {
            // pass
        }
    }
}