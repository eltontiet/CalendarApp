package persistence;

import model.Calendar;
import model.Config;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConfigReaderTest {

    @Test
    void testReaderNoFile() {
        ConfigReader reader = new ConfigReader("./data/doesNotExist.json");
        try {
            Config c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCalendar() {
        ConfigReader reader = new ConfigReader("./data/testConfigReaderEmptyFile.json");
        try {
            Config c = reader.read();
            assertEquals("./data/Default Calendar.json", c.getCurrentFile());
            assertEquals(0, c.getFiles().size());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReaderGeneralConfig() {
        // TODO: make better test file, too similar to writer
        try {
            ConfigReader jsonReader = new ConfigReader("./data/testConfigReaderGeneralConfig.json");
            Config c2 = jsonReader.read();

            assertEquals("./data/testReaderGeneralCalendar.json", c2.getCurrentFile());
            assertEquals(2, c2.getFiles().size());
            assertEquals("./data/test2Calendar.json", c2.getFileLocation("Test2"));
            assertEquals("./data/testReaderGeneralCalendar.json", c2.getFileLocation("Test Reader General Calendar"));

        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }
}
