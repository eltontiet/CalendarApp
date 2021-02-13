package model;

import model.date.Date;
import model.date.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    Event event;
    Date date;
    Time time;

    @BeforeEach
    void setup() {
        date = new Date(2021,2,12);
        time = new Time(6,15);
        event = new Event("Midterm", date, time,60);
    }

    @Test
    void testGetName() {
        assertEquals("Midterm",event.getName());
    }

    @Test
    void testGetDate() {
        assertEquals(date,event.getDate());
    }

    @Test
    void testGetTime() {
        assertEquals(time,event.getTime());
    }

    @Test
    void testGetDuration() {
        assertEquals(60,event.getDuration());
    }


    @Test
    void testSetName() {
        event.setName("Free Time");
        assertEquals("Free Time",event.getName());
    }

    @Test
    void testSetDate() {
        Date newDate = new Date(2021,2,13);
        event.setDate(newDate);
        assertEquals(newDate,event.getDate());
    }

    @Test
    void testSetTime() {
        Time newTime = new Time(15,0);
        event.setTime(newTime);
        assertEquals(newTime,event.getTime());
    }

    @Test
    void testSetDuration() {
        event.setDuration(15);
        assertEquals(15,event.getDuration());
    }
}

