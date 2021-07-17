package com.example.mynoteapp;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class NoteEntity implements Serializable {
    public String id;
    public String title;
    public String dateOfCreation;
    public String content;

    public NoteEntity() {
    }

    public NoteEntity(String id, String title, String dateOfCreation, String content) {
        this.id = id;
        this.title = title;
        this.dateOfCreation = dateOfCreation;
        this.content = content;
    }

    @Override
    public String toString() {
        return title + " от " + dateOfCreation;
    }

    public String getDate() {
        return dateOfCreation;
    }

    public String getTitle() {
        return title;
    }

    public static String generateNewId() {
        return UUID.randomUUID().toString();
    }

    public static long getCurrentDate() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
