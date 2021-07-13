package com.example.mynoteapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteArrayList implements NoteRepo {
    private final List<NoteEntity> noteEntityList = new ArrayList<>();
    private final String TAG = "@@@ NoteArrayList";

    NoteArrayList() {
        noteEntityList.add(new NoteEntity(NoteEntity.generateNewId(), "Купить помидоры", "03.07.21", "Нужно купить вкусные помидоры в Пятаке"));
        noteEntityList.add(new NoteEntity(NoteEntity.generateNewId(), "Сходить на прогулку", "03.07.21", "Прогуляться вечерком"));
        noteEntityList.add(new NoteEntity(NoteEntity.generateNewId(), "Сделать Домашку по программированию", "03.07.21", "Добить уже 6 урок. Тема сложная"));
        noteEntityList.add(new NoteEntity(NoteEntity.generateNewId(), "Поработать", "03.07.21", "Натянуть потолки в понедельник и докрасить стены"));
        noteEntityList.add(new NoteEntity(NoteEntity.generateNewId(), "Догнать группу", "03.07.21", "Нужно догнать и начать посещать онлайн вебинары"));
    }

    @Override
    public void createNote(NoteEntity newNote) throws NoteCreationException {
        Log.d(TAG, "createNote() called with: newNote = [" + newNote + "]");
        for (NoteEntity note : noteEntityList) {
            if (note.getTitle().equals(newNote.getTitle()))
                throw new NoteCreationException(newNote);
        }
        noteEntityList.add(newNote);
    }

    @Override
    public List<NoteEntity> getNotes() {
        Log.d(TAG, "getNotes() called");
        return new ArrayList<>(noteEntityList);
    }

    @Override
    public void updateNote(NoteEntity updatedNote) {
        Log.d(TAG, "updateNote() called with: updatedNote = [" + updatedNote + "]");
        deleteNote(updatedNote.id);
        noteEntityList.add(updatedNote);
    }

    @Override
    public void deleteNote(String id) {
        Log.d(TAG, "deleteNote() called with: id = [" + id + "]");
        for (NoteEntity note : noteEntityList) {
            if (note.id.equals(id)) {
                noteEntityList.remove(note);
                break;
            }
        }
    }
}
