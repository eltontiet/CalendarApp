package model;

import model.date.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {
    Schedule schedule;

    @BeforeEach
    void setup() {
        schedule = new Schedule("University");
    }

    @Test
    void testGetName() {
        assertEquals("University", schedule.getName());
    }

    @Test
    void testSetName() {
        schedule.setName("Reading Week");
        assertEquals("Reading Week", schedule.getName());
    }

    @Test
    void testGetActivities() {
        Activity activity1 = new Activity("CPSC 210", new Time(10,0), 60);
        Activity activity2 = new Activity("CPSC 121", new Time(9,0),60);
        schedule.addActivity(activity1);
        schedule.addActivity(activity2);
        assertTrue(schedule.getActivities().contains(activity1));
        assertTrue(schedule.getActivities().contains(activity2));
        assertEquals(2, schedule.getActivities().size());
    }

    @Test
    void testGetActivity() {
        Activity activity1 = new Activity("CPSC 210", new Time(10,0), 60);
        Activity activity2 = new Activity("CPSC 121", new Time(9,0),60);
        schedule.addActivity(activity1);
        schedule.addActivity(activity2);
        assertEquals(activity1, schedule.getActivity("CPSC 210"));
        assertEquals(activity2, schedule.getActivity("CPSC 121"));
        assertNull(schedule.getActivity("CPSC 110"));
    }

    @Test
    void testRemoveActivity() {
        Activity activity1 = new Activity("CPSC 210", new Time(10,0), 60);
        Activity activity2 = new Activity("CPSC 121", new Time(9,0),60);
        schedule.addActivity(activity1);
        schedule.addActivity(activity2);
        assertTrue(schedule.getActivities().contains(activity1));
        assertTrue(schedule.getActivities().contains(activity2));
        assertEquals(2, schedule.getActivities().size());
        schedule.removeActivity(activity1);
        assertFalse(schedule.getActivities().contains(activity1));
        assertTrue(schedule.getActivities().contains(activity2));
        assertEquals(1, schedule.getActivities().size());
    }
}
