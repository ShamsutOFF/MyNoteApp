package com.example.mynoteapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class NoteFragment extends Fragment {
    public static final String NOTE_ARGS_KEY = "NOTE_ARGS_KEY";
    private static final String TAG = "@@@ NoteFragment";
    private NoteEntity note = null;
    private EditText titleEt;
    private EditText dateOfCreationEt;
    private EditText contentEt;
    private Button saveButton;

    public static NoteFragment newInstance(NoteEntity noteEntity) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle args = new Bundle();

        args.putParcelable(NOTE_ARGS_KEY, noteEntity);

        noteFragment.setArguments(args);
        return noteFragment;
    }

    public interface Controller {
        void saveResult(NoteEntity note);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" +
                container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_note, null);

        titleEt = view.findViewById(R.id.title_edit_text);
        dateOfCreationEt = view.findViewById(R.id.date_of_creation_edit_text);
        contentEt = view.findViewById(R.id.content_edit_text);

        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            Controller controller = (Controller) getActivity();
            controller.saveResult(new NoteEntity(
                    titleEt.getText().toString(),
                    dateOfCreationEt.getText().toString(),
                    contentEt.getText().toString()
            ));
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = ["
                + savedInstanceState + "]");

        titleEt.setText(note.title);
        dateOfCreationEt.setText(note.dateOfCreation);
        contentEt.setText(note.content);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implements ProfileController!");
        }
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE_ARGS_KEY);
        }
    }
}


