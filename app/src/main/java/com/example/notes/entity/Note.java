package com.example.notes.entity;

import com.orm.SugarRecord;

public class Note extends SugarRecord {

    private String title;
    private String description;

    public Note() { }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
