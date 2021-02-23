package model;

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
        Date date2 = new Date(2000, 1, 1);
        Date date3 = new Date(2004, 2, 3);
        Date date4 = new Date(2100, 6, 4);

        assertFalse(date.isLeapYear());
        assertTrue(date2.isLeapYear());
        assertTrue(date3.isLeapYear());
        assertFalse(date4.isLeapYear());
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
        assertEquals("0:00", time4.get24HTime());
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
}