package persistence;

import exceptions.CalendarNotFoundException;
import model.Calendar;
import model.Config;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConfigWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Config c = new Config();
            ConfigWriter writer = new ConfigWriter("./data/illegal\0:fileName.json");
            writer.open();
            fail("IOexception was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFile() {
        try {
            Config c = new Config();
            ConfigWriter writer = new ConfigWriter("./data/testConfigWriterEmptyFile.json");
            writer.open();
            writer.write(c);
            writer.close();

            ConfigReader jsonReader = new ConfigReader("./data/testConfigWriterEmptyFile.json");
            c = jsonReader.read();

            assertEquals(Config.DEFAULT_SAVE_LOCATION, c.getCurrentFile());
            assertEquals(0, c.getFiles().size());

        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    void testWriterGeneralConfig() {
        try {
            Config c = new Config();
            ConfigWriter writer = new ConfigWriter("./data/testConfigWriterGeneralConfig.json");
            c.addFile("Test Reader General Calendar", "./data/testReaderGeneralCalendar.json");
            c.addFile("Test2", "./data/test2Calendar.json");
            c.setCurrentFile(c.getFileLocation("Test Reader General Calendar"));

            writer.open();
            writer.write(c);
            writer.close();

            ConfigReader jsonReader = new ConfigReader("./data/testConfigWriterGeneralConfig.json");
            Config c2 = jsonReader.read();

            assertEquals(c.getCurrentFile(), c2.getCurrentFile());
            assertEquals(2, c2.getFiles().size());
            assertEquals("./data/test2Calendar.json", c2.getFileLocation("Test2"));
            assertEquals("./data/testReaderGeneralCalendar.json", c2.getFileLocation("Test Reader General Calendar"));

        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }
}
