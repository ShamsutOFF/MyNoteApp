package com.example.mynoteapp;

import java.util.List;

public interface NoteRepo {

    void createNote(NoteEntity note) throws NoteCreationException;

    List<NoteEntity> getNotes();

    void updateNote(NoteEntity note);

    void deleteNote(String id);

    void subscribe(Runnable subscriber);

    void unSubscribe(Runnable subscriber);

    class NoteCreationException extends Throwable {
        public NoteCreationException(NoteEntity newNote) {
            super("Такая заметка уже есть = " + newNote.getTitle());
        }
    }

}