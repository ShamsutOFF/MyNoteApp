package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
        implements NoteFragment.Controller, NoteListFragment.Controller {
    private static final String TAG = "@@@ AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new NoteListFragment())
                .commit();
    }

    @Override
    public void saveResult(NoteEntity dossier) {
        //todo
    }

    @Override
    public void openNoteScreen(NoteEntity note) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                .add(isLandscape ? R.id.detail_container : R.id.container, NoteFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }
}