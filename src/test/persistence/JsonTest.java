package persistence;

import model.Activity;
import model.Event;
import model.Note;
import model.Schedule;
import model.date.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void checkEventEqual(Event e1, Event e2) {
        assertEquals(e1.getName(), e2.getName());
        assertEquals(e1.getDate().getYear(), e2.getDate().getYear());
        assertEquals(e1.getDate().getMonth(), e2.getDate().getMonth());
        assertEquals(e1.getDate().getDay(), e2.getDate().getDay());

        assertEquals(e1.getTime().getHour(), e2.getTime().getHour());
        assertEquals(e1.getTime().getMinute(), e2.getTime().getMinute());

        assertEquals(e1.getDuration(), e2.getDuration());
    }

    protected void checkScheduleEqual(Schedule s1, Schedule s2) {
        assertEquals(s1.getName(), s2.getName());
        for (int i = 0; i < s1.getActivities().size(); i++) {
            checkActivityEqual(s1.getActivities().get(i), s2.getActivities().get(i));
        }
    }

    private void checkActivityEqual(Activity a1, Activity a2) {
        assertEquals(a1.getName(), a2.getName());
        assertEquals(a1.getTime().getHour(), a2.getTime().getHour());
        assertEquals(a1.getTime().getMinute(), a2.getTime().getMinute());

        for (int i = 0; i < a1.getDates().size(); i++) {
            checkDateEqual(a1.getDates().get(i), a2.getDates().get(i));
        }

        for (int i = 0; i < a1.getNotes().size(); i++) {
            checkNoteEqual(a1.getNotes().get(i), a2.getNotes().get(i));
        }

        for (int i = 0; i < a1.getEvents().size(); i++) {
            checkEventEqual(a1.getEvents().get(i), a2.getEvents().get(i));
        }
    }

    private void checkNoteEqual(Note n1, Note n2) {
        assertEquals(n1.getTitle(), n2.getTitle());
        assertEquals(n1.getBody(), n2.getBody());
    }

    private void checkDateEqual(Date d1, Date d2) {
        assertEquals(d1.getYear(), d2.getYear());
        assertEquals(d1.getMonth(), d2.getMonth());
        assertEquals(d1.getDay(), d2.getDay());
    }
}
