package model;

import exceptions.CalendarNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConfigTest {
    Config config;

    @BeforeEach
    void setup() {
        config = new Config();
        config.setup();
    }

    @Test
    void testGetName() {
        config.addFile("School", "./data/schoolCalendar.json");
        try {
            assertEquals("School", config.getName("./data/schoolCalendar.json"));
            assertEquals("Default Calendar", config.getName(Config.DEFAULT_SAVE_LOCATION));
        } catch (CalendarNotFoundException e) {
            fail("Should find both files");
        }
    }

    @Test
    void testGetFileLocation() {
        config.addFile("University", "./data/universityCalendar.json");
        try {
            assertEquals("./data/universityCalendar.json", config.getFileLocation("University"));
            assertEquals(Config.DEFAULT_SAVE_LOCATION, config.getFileLocation("Default Calendar"));
        } catch (FileNotFoundException e) {
            fail("Should find both files");
        }
    }

    @Test
    void testBadNameGetFileLocation() {
        try {
            config.getFileLocation("This is not a real calendar");
            fail("Should throw exception");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testBadFileLocationGetName() {
        try {
            config.getName("./data/What is this?");
            fail("Should throw exception");
        } catch (CalendarNotFoundException e) {
            // pass
        }
    }

    @Test
    void testAddFile() {
        config.addFile("University", "./data/universityCalendar.json");
        assertEquals(2, config.getFiles().size());

        try {
            assertEquals("./data/universityCalendar.json", config.getFileLocation("University"));
        } catch (FileNotFoundException e) {
            fail("Should not fail");
        }
    }

    @Test
    void TestRemoveFileLocation() {
        config.addFile("Work", "./data/workCalendar.json");
        assertEquals(2, config.getFiles().size());

        try {
            config.removeFile("./data/workCalendar.json");
            assertEquals(1, config.getFiles().size());
        } catch (FileNotFoundException e) {
            fail("Should not throw exception");
        }

        try {
            config.getFileLocation("./data/workCalendar.json");
            fail("Should throw exception");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void TestRemoveCalendar() {
        config.addFile("Free Time", "./data/freeTimeCalendar.json");
        assertEquals(2, config.getFiles().size());

        try {
            config.removeCalendar("Free Time");
            assertEquals(1, config.getFiles().size());
        } catch (CalendarNotFoundException e) {
            fail("Should not throw exception");
        }

        try {
            config.getName("Free Time");
            fail("Should throw exception");
        } catch (CalendarNotFoundException e) {
            // pass
        }
    }

    @Test
    void testBadNameRemoveFileLocation() {
        try {
            config.removeFile("./data/What is this?");
            fail("Should throw exception");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testBadFileLocationRemoveCalendar() {
        try {
            config.removeCalendar("This is not a real calendar");
            fail("Should throw exception");
        } catch (CalendarNotFoundException e) {
            // pass
        }
    }

    @Test
    void testSetCurrentFile() {
        config.addFile("Midterms", "./data/midtermsCalendar.json");
        config.setCurrentFile("./data/midtermsCalendar.json");
        assertEquals("./data/midtermsCalendar.json", config.getCurrentFile());
    }

}
