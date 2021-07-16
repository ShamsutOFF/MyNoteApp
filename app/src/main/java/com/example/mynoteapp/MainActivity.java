package com.example.mynoteapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NoteFragment.Contract, NoteListFragment.Contract {
    private static final String NOTES_LIST_FRAGMENT_TAG = "NOTES_LIST_FRAGMENT_TAG";
    private static final String TAG = "@@@ MainActivity";
    private boolean isTwoPaneMode = false;
    private final NoteArrayList noteArrayList = new NoteArrayList(); //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTwoPaneMode = findViewById(R.id.detail_container) != null;

        showNoteList();
        showMenu();
    }

    private void showNoteList() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, new NoteListFragment(), NOTES_LIST_FRAGMENT_TAG)
                .commit();
    }

    private void showMenu() {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        findViewById(R.id.home_button).setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new NoteListFragment())
                    .addToBackStack(null)
                    .commit();
        });
        findViewById(R.id.add_button).setOnClickListener(v -> {
            createNewNote();
        });
        findViewById(R.id.settings_button).setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(isLandscape ? R.id.detail_container : R.id.main_container, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void showEditNote() {
        Log.d(TAG, "showEditNote() called");
        showEditNote(null);
    }

    private void showEditNote(@Nullable NoteEntity note) {
        Log.d(TAG, "showEditNote() called with: note = [" + note + "]");
        if (!isTwoPaneMode) {
            setTitle(note == null ? R.string.create_note_title : R.string.edit_note_title);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!isTwoPaneMode) {
            transaction.addToBackStack(null);
        }
        transaction
                .add(isTwoPaneMode ? R.id.detail_container : R.id.main_container, NoteFragment.newInstance(note))
                .commit();
    }

    @Override
    public void createNewNote() {
        Log.d(TAG, "createNewNote() called");
        showEditNote();
    }

    @Override
    public void editNote(NoteEntity note) {
        Log.d(TAG, "editNote() called with: note = [" + note + "]");
        showEditNote(note);
    }

    @Override
    public void saveNote(NoteEntity note) throws NoteRepo.NoteCreationException {
        Log.d(TAG, "saveNote() called with: note = [" + note + "]");
        setTitle(R.string.app_name);
        getSupportFragmentManager().popBackStack();
        NoteListFragment noteListFragment = (NoteListFragment) getSupportFragmentManager().findFragmentByTag(NOTES_LIST_FRAGMENT_TAG);
        noteListFragment.addNote(note);
    }
}