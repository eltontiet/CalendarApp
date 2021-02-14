package model;

import model.date.Date;
import model.date.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {
    Activity activity;

    @BeforeEach
    void setup() {
        Time time = new Time(10,0);
        Date date1 = new Date(2021,2,14);
        Date date2 = new Date(2021,2,15);
        Note note1 = new Note("Java","Learn java");
        Note note2 = new Note("Grades","Do well in the class please!");
        Event event1 = new Event("Project Phase 1", new Date(2021,2,14),new Time(19,0),0);
        Event event2 = new Event("Midterm 1", new Date(2021,2,24),new Time(19,30),60);
        activity = new Activity("CPSC 210", time, 60);
        activity.addDate(date1);
        activity.addDate(date2);
        activity.addNote(note1);
        activity.addNote(note2);
        activity.addEvent(event1);
        activity.addEvent(event2);
    }

    @Test
    void testGetName() {
        assertEquals("CPSC 210",activity.getName());
    }

    @Test
    void testGetTime() {
        assertEquals(10,activity.getTime().getHour());
        assertEquals(0,activity.getTime().getMinute());
        assertEquals("10:00AM",activity.getTime().get12HTime());
        assertEquals("10:00",activity.getTime().get24HTime());
    }

    @Test
    void testGetDates() {
        Date date1 = activity.getDates().get(0);
        Date date2 = activity.getDates().get(1);
        assertEquals("2021-02-14",date1.getDate());
        assertEquals("2021-02-15",date2.getDate());
    }

    @Test
    void testGetDuration() {
        assertEquals(60,activity.getDuration());
    }

    @Test
    void testGetNotes() {
        Note note1 = activity.getNotes().get(0);
        Note note2 = activity.getNotes().get(1);
        assertEquals("Java", note1.getTitle());
        assertEquals("Learn java", note1.getBody());
        assertEquals("Grades", note2.getTitle());
        assertEquals("Do well in the class please!", note2.getBody());
    }

    @Test
    void testGetEvents() {
        Event event1 = activity.getEvents().get(0);
        Event event2 = activity.getEvents().get(1);
        assertEquals("Project Phase 1", event1.getName());
        assertEquals("Midterm 1", event2.getName());
    }

    @Test
    void testGetNote() {
        assertEquals("Learn java", activity.getNote("Java").getBody());
        assertEquals("Do well in the class please!", activity.getNote("Grades").getBody());
        assertEquals(null, activity.getNote("Fail"));
    }

    @Test
    void testGetEvent() {
        assertEquals(0, activity.getEvent("Project Phase 1").getDuration());
        assertEquals(60, activity.getEvent("Midterm 1").getDuration());
        assertEquals(null, activity.getEvent("Problem Set 9"));
    }

    @Test
    void testSetName() {
        activity.setName("CPSC 110");
        assertEquals("CPSC 110", activity.getName());
    }

    @Test
    void testSetDuration() {
        activity.setDuration(90);
        assertEquals(90, activity.getDuration());
    }

    @Test
    void testSetTime() {
        activity.setTime(new Time(12,0));
        assertEquals("12:00", activity.getTime().get24HTime());
    }

    @Test
    void testRemoveDate() {
        Date testDate1 = new Date(2021,2,19);
        Date testDate2 = new Date(2021,2,21);
        activity.addDate(testDate1);
        activity.addDate(testDate2);
        assertEquals(4, activity.getDates().size());
        assertTrue(activity.getDates().contains(testDate1));
        assertTrue(activity.getDates().contains(testDate2));
        activity.removeDate(testDate2);
        assertFalse(activity.getDates().contains(testDate2));
        assertTrue(activity.getDates().contains(testDate1));
        assertEquals(3,activity.getDates().size());
        activity.removeDate(testDate1);
        assertFalse(activity.getDates().contains(testDate1));
        assertEquals(2,activity.getDates().size());
    }

    @Test
    void testRemoveNote() {
        Note testNote1 = new Note("Project", "Slowly work on project everyday");
        Note testNote2 = new Note("Tests", "Writing them takes so long!");
        activity.addNote(testNote2);
        activity.addNote(testNote1);
        activity.removeNote(testNote2);
        assertFalse(activity.getNotes().contains(testNote2));
        assertTrue(activity.getNotes().contains(testNote1));
        assertEquals(3, activity.getNotes().size());
    }

    @Test
    void testRemoveEvent() {
        Event testEvent1 = new Event("Reading Break", new Date(2021,2,15),new Time(0,0),0);
        Event testEvent2 = new Event("Chill", new Date(2021,2,15),new Time(0,0),0);
        activity.addEvent(testEvent1);
        activity.addEvent(testEvent2);
        assertTrue(activity.getEvents().contains(testEvent1));
        assertTrue(activity.getEvents().contains(testEvent2));
        assertEquals(4, activity.getEvents().size());
        activity.removeEvent(testEvent2);
        assertTrue(activity.getEvents().contains(testEvent1));
        assertFalse(activity.getEvents().contains(testEvent2));
        assertEquals(3, activity.getEvents().size());
    }
}
