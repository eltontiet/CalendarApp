package persistence;

import model.*;
import model.date.Date;
import model.date.Time;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Calendar c = new Calendar("Test");
            CalendarWriter writer = new CalendarWriter("./data/illegal\0:fileName.json");
            writer.open();
            fail("IOexception was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFile() {
        try {
            Calendar c = new Calendar("Test");
            CalendarWriter writer = new CalendarWriter("./data/testWriterEmptyFile.json");
            writer.open();
            writer.write(c);
            writer.close();

            CalendarReader jsonReader = new CalendarReader("./data/testWriterEmptyFile.json");
            c = jsonReader.read();

            assertEquals("Test", c.getName());
            assertEquals(0, c.getEvents().size());
            assertEquals(0, c.getSchedules().size());
        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    void testWriterGeneralCalendar() {
        try {
            Calendar c = new Calendar("Test");

            Event ce1 = new Event("Holiday", new Date(2021,3,7), new Time(0,0), 0);
            Event ce2 = new Event("Coding!", new Date(2021,3,9), new Time(15,0), 300);

            Event ae1 = new Event("Project Phase 2", new Date(2021,3,7), new Time(20,0), 0);
            Event ae2 = new Event("ITE 2", new Date(2021,3,24), new Time(19,30), 60);

            c.addEvent(ce1);
            c.addEvent(ce2);

            Schedule s = new Schedule("Term 2");

            Activity a1 = new Activity("CPSC 210", new Time(11,0), 60);
            Activity a2 = new Activity("CPSC 121", new Time(10, 0), 60);

            a1.addNote(new Note("Project", "Do your project every day!"));
            a1.addNote(new Note("Project (Seriously)", "You will regret procrastinating it"));

            a1.addDate(new Date(2021, 3, 8));
            a1.addDate(new Date(2021, 3, 10));
            a1.addDate(new Date(2021, 3, 12));

            a1.addEvent(ae1);
            a1.addEvent(ae2);

            a2.addDate(new Date(2021,3,8));
            a2.addDate(new Date(2021, 3, 10));
            a2.addDate(new Date(2021, 3, 12));

            s.addActivity(a1);
            s.addActivity(a2);

            c.addSchedule(s);

            Schedule s2 = new Schedule("Term 1");

            Activity a3 = new Activity("CPSC 110", new Time(15,0), 90);

            a3.addNote(new Note("Natural Recursion", "Always trust the natural recursion!"));
            a3.addNote(new Note("Cows", "Cows are docile creatures, unlike cats!"));

            a3.addDate(new Date(2020,10,7));
            a3.addDate(new Date(2021, 10, 9));
            a3.addDate(new Date(2021, 10, 14));

            s2.addActivity(a3);

            c.addSchedule(s2);

            CalendarWriter writer = new CalendarWriter("./data/testWriterGeneralCalendar.json");
            writer.open();
            writer.write(c);
            writer.close();



            CalendarReader jsonReader = new CalendarReader("./data/testWriterGeneralCalendar.json");
            c = jsonReader.read();

            List<Event> calendarEventList = c.getEvents();
            Event readEvent1 = calendarEventList.get(0);
            Event readEvent2 = calendarEventList.get(1);

            assertEquals("Test", c.getName());
            assertEquals(2, calendarEventList.size());

            checkEventEqual(readEvent1, ce1);
            checkEventEqual(readEvent2, ce2);

            List<Schedule> calendarScheduleList = c.getSchedules();
            Schedule readSchedule1 = calendarScheduleList.get(0);
            Schedule readSchedule2 = calendarScheduleList.get(1);

            assertEquals(2, calendarScheduleList.size());

            checkScheduleEqual(readSchedule1, s);
            checkScheduleEqual(readSchedule2, s2);

        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }
}
