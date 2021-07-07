package com.example.mynoteapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class NoteListFragment extends Fragment {
    private final NoteEntity note1 =
            new NoteEntity("Купить помидоры", "03.07.21", "Нужно купить вкусные помидоры в Пятаке");
    private final NoteEntity note2 =
            new NoteEntity("Сходить на прогулку", "03.07.21", "Прогуляться вечерком");
    private final NoteEntity note3 =
            new NoteEntity("Сделать Домашку по программированию", "03.07.21", "Добить уже 6 урок. Тема сложная");
    private final NoteEntity note4 =
            new NoteEntity("Поработать", "03.07.21", "Натянуть потолки в понедельник и докрасить стены");
    private final NoteEntity note5 =
            new NoteEntity("Догнать группу", "03.07.21", "Нужно догнать и начать посещать онлайн вебинары");

    interface Controller {
        void openNoteScreen(NoteEntity note);
    }

    private LinearLayout linearLayout;

    private void addNoteToList(NoteEntity noteEntity) {
        Button button = new Button(getContext());
        button.setText(noteEntity.toString());
        button.setOnClickListener(v -> {
            ((Controller) getActivity()).openNoteScreen(noteEntity);
        });
        linearLayout.addView(button);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implements NoteListController!");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.linear);

        addNoteToList(note1);
        addNoteToList(note2);
        addNoteToList(note3);
        addNoteToList(note4);
        addNoteToList(note5);
    }
}
