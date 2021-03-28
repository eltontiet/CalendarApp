package model;

import model.date.Date;
import model.date.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarTest {
    Calendar calendar;

    @BeforeEach
    void setup() {
        calendar = new Calendar("STRESS");
    }

    @Test
    void testGetName() {
        assertEquals("STRESS", calendar.getName());
    }

    @Test
    void testGetSchedules() {
        Schedule testSchedule1 = new Schedule("School");
        Schedule testSchedule2 = new Schedule("Free Time");
        Schedule testSchedule3 = new Schedule("Work?");

        calendar.addSchedule(testSchedule1);
        calendar.addSchedule(testSchedule3);

        assertTrue(calendar.getSchedules().contains(testSchedule1));
        assertFalse(calendar.getSchedules().contains(testSchedule2));
        assertTrue(calendar.getSchedules().contains(testSchedule3));
        assertEquals(2, calendar.getSchedules().size());
    }

    @Test
    void testGetEvents() {
        Event testEvent1 = new Event("Project Phase 1",new Date(2021,2,14),new Time(19,0),0);
        Event testEvent2 = new Event("Midterm 1",new Date(2021,2,24),new Time(19,30),60);
        Event testEvent3 = new Event("Midterm 2",new Date(2021,3,25),new Time(19,30),60);

        calendar.addEvent(testEvent1);
        calendar.addEvent(testEvent3);

        assertTrue(calendar.getEvents().contains(testEvent1));
        assertFalse(calendar.getEvents().contains(testEvent2));
        assertTrue(calendar.getEvents().contains(testEvent3));
        assertEquals(2, calendar.getEvents().size());
    }

    @Test
    void testGetSchedule() {
        Schedule testSchedule1 = new Schedule("School");
        Schedule testSchedule2 = new Schedule("Free Time");
        Schedule testSchedule3 = new Schedule("Work?");

        calendar.addSchedule(testSchedule1);
        calendar.addSchedule(testSchedule2);
        calendar.addSchedule(testSchedule3);

        assertEquals(testSchedule1, calendar.getSchedule("School"));
        assertEquals(testSchedule3, calendar.getSchedule("Work?"));
        assertEquals(testSchedule2, calendar.getSchedule("Free Time"));
        assertNull(calendar.getSchedule("Graduation"));
    }

    @Test
    void testGetEvent() {
        Event testEvent1 = new Event("Project Phase 1",new Date(2021,2,14),new Time(19,0),0);
        Event testEvent2 = new Event("Midterm 1",new Date(2021,2,24),new Time(19,30),60);
        Event testEvent3 = new Event("Midterm 2",new Date(2021,3,25),new Time(19,30),60);

        calendar.addEvent(testEvent1);
        calendar.addEvent(testEvent2);
        calendar.addEvent(testEvent3);

        assertEquals(testEvent1, calendar.getEvent("Project Phase 1"));
        assertEquals(testEvent3, calendar.getEvent("Midterm 2"));
        assertEquals(testEvent2, calendar.getEvent("Midterm 1"));
        assertNull(calendar.getEvent("Midterm 3"));
    }

    @Test
    void testRemoveSchedule() {
        Schedule testSchedule1 = new Schedule("School");
        Schedule testSchedule2 = new Schedule("Free Time");
        Schedule testSchedule3 = new Schedule("Work?");

        calendar.addSchedule(testSchedule1);
        calendar.addSchedule(testSchedule2);
        calendar.addSchedule(testSchedule3);

        assertTrue(calendar.getSchedules().contains(testSchedule1));
        assertTrue(calendar.getSchedules().contains(testSchedule2));
        assertTrue(calendar.getSchedules().contains(testSchedule3));
        assertEquals(3, calendar.getSchedules().size());

        calendar.removeSchedule(testSchedule2);

        assertTrue(calendar.getSchedules().contains(testSchedule1));
        assertFalse(calendar.getSchedules().contains(testSchedule2));
        assertTrue(calendar.getSchedules().contains(testSchedule3));
        assertEquals(2, calendar.getSchedules().size());
    }

    @Test
    void testRemoveEvent() {
        Event testEvent1 = new Event("Project Phase 1",new Date(2021,2,14),new Time(19,0),0);
        Event testEvent2 = new Event("Midterm 1",new Date(2021,2,24),new Time(19,30),60);
        Event testEvent3 = new Event("Midterm 2",new Date(2021,3,25),new Time(19,30),60);

        calendar.addEvent(testEvent1);
        calendar.addEvent(testEvent2);
        calendar.addEvent(testEvent3);

        assertTrue(calendar.getEvents().contains(testEvent1));
        assertTrue(calendar.getEvents().contains(testEvent2));
        assertTrue(calendar.getEvents().contains(testEvent3));
        assertEquals(3, calendar.getEvents().size());

        calendar.removeEvent(testEvent2);

        assertTrue(calendar.getEvents().contains(testEvent1));
        assertFalse(calendar.getEvents().contains(testEvent2));
        assertTrue(calendar.getEvents().contains(testEvent3));
        assertEquals(2, calendar.getEvents().size());
    }

    @Test
    void testFindActivitySchedule() {
        Schedule testSchedule1 = new Schedule("School");
        Schedule testSchedule2 = new Schedule("Free Time");
        Schedule testSchedule3 = new Schedule("Work?");

        Activity testActivity1 = new Activity("CPSC 210", new Time(11,0),60);
        Activity testActivity2 = new Activity("Gaming", new Time(15,0),30);
        Activity testActivity3 = new Activity("Applications", new Time(15,30),120);
        Activity testActivity4 = new Activity("Free Marks", new Time(0,0),1200);

        testSchedule1.addActivity(testActivity1);
        testSchedule2.addActivity(testActivity2);
        testSchedule3.addActivity(testActivity3);

        calendar.addSchedule(testSchedule1);
        calendar.addSchedule(testSchedule2);
        calendar.addSchedule(testSchedule3);

        assertEquals(testSchedule1, calendar.findActivitySchedule(testActivity1));
        assertEquals(testSchedule2, calendar.findActivitySchedule(testActivity2));
        assertEquals(testSchedule3, calendar.findActivitySchedule(testActivity3));
        assertNull(calendar.findActivitySchedule(testActivity4));
    }
}
