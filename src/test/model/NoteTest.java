package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {
    Note note;

    @BeforeEach
    void setup() {
        note = new Note("This is a title", "This is a body");
    }

    @Test
    void testGetTitle() {
        assertEquals("This is a title", note.getTitle());
    }

    @Test
    void testGetBody() {
        assertEquals("This is a body", note.getBody());
    }

    @Test
    void testEditTitle() {
        note.editTitle("This is a new title");
        assertEquals("This is a new title", note.getTitle());
    }

    @Test
    void testEditBody() {
        note.editBody("This is a new body");
        assertEquals("This is a new body", note.getBody());
    }
}
