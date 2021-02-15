package model;

// Represents a note with a title and body
public class Note {
    private String title;
    private String body;

    // EFFECTS: sets title to noteTitle,
    //          and body to noteBody
    public Note(String noteTitle, String noteBody) {
        this.title = noteTitle;
        this.body = noteBody;
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    // MODIFIES: this
    // EFFECTS: edits this.title to be title
    public void editTitle(String title) {
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: edits this.title to be title
    public void editBody(String body) {
        this.body = body;
    }
}
