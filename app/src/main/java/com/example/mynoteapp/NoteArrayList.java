package com.example.mynoteapp;

import java.util.ArrayList;
import java.util.List;

public class NoteArrayList implements NoteRepo {
    private final List<NoteEntity> noteEntityList = new ArrayList<>();

    NoteArrayList() {
        noteEntityList.add(new NoteEntity("Купить помидоры", "03.07.21", "Нужно купить вкусные помидоры в Пятаке"));
        noteEntityList.add(new NoteEntity("Сходить на прогулку", "03.07.21", "Прогуляться вечерком"));
        noteEntityList.add(new NoteEntity("Сделать Домашку по программированию", "03.07.21", "Добить уже 6 урок. Тема сложная"));
        noteEntityList.add(new NoteEntity("Поработать", "03.07.21", "Натянуть потолки в понедельник и докрасить стены"));
        noteEntityList.add(new NoteEntity("Догнать группу", "03.07.21", "Нужно догнать и начать посещать онлайн вебинары"));
    }

    @Override
    public void createNote(NoteEntity newNote) throws NoteCreationException {
        for (NoteEntity note : noteEntityList) {
            if (note.getTitle().equals(newNote.getTitle()))
                throw new NoteCreationException(newNote);
        }
        noteEntityList.add(newNote);
    }

    @Override
    public List<NoteEntity> getNotes() {
        return new ArrayList<>(noteEntityList);
    }

    @Override
    public void updateNote(NoteEntity updatedNote) {
        deleteNote(updatedNote.getTitle());
        noteEntityList.add(updatedNote);
    }

    @Override
    public void deleteNote(String title) {
        for (NoteEntity note : noteEntityList) {
            if (note.getTitle().equals(title)) {
                noteEntityList.remove(note);
                break;
            }
        }
    }
}
