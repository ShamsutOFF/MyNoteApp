package com.example.mynoteapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NoteFragment extends Fragment {
    public static final String NOTE_ARGS_KEY = "NOTE_ARGS_KEY";
    private static final String TAG = "@@@ NoteFragment";
    private NoteEntity note = null;
    private EditText titleEt;
    private EditText dateOfCreationEt;
    private EditText contentEt;
    private Button saveButton;


    public static NoteFragment newInstance(@Nullable NoteEntity noteEntity) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(NOTE_ARGS_KEY, noteEntity);
        noteFragment.setArguments(bundle);
        return noteFragment;
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_note, null);
        titleEt = view.findViewById(R.id.title_edit_text);
        dateOfCreationEt = view.findViewById(R.id.date_of_creation_edit_text);
        contentEt = view.findViewById(R.id.content_edit_text);
        saveButton = view.findViewById(R.id.save_button);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        note = (NoteEntity) getArguments().getSerializable(NOTE_ARGS_KEY);
        fillNote(note);
        saveButton.setOnClickListener(v -> {
            try {
                getContract().saveNote(gatherNote());
            } catch (NoteRepo.NoteCreationException e) {
                e.printStackTrace();
            }
        });
    }

    private NoteEntity gatherNote() {
        Log.d(TAG, "gatherNote() called");
        return new NoteEntity(
                note == null ? NoteEntity.generateNewId() : note.id,
                titleEt.getText().toString(),
                dateOfCreationEt.getText().toString(),
//                note == null ? NoteEntity.getCurrentDate() : note.creationDate,
                contentEt.getText().toString()
        );
    }

    private void fillNote(NoteEntity note) {
        Log.d(TAG, "fillNote() called with: note = [" + note + "]");
        if (note == null) return;
        titleEt.setText(note.title);
        dateOfCreationEt.setText(note.dateOfCreation);
        contentEt.setText(note.content);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new RuntimeException("Activity must implements Contract!");
        }
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE_ARGS_KEY);
        }
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void saveNote(NoteEntity note) throws NoteRepo.NoteCreationException;
    }
}


