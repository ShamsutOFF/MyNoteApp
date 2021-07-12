package com.example.mynoteapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    //        implements NoteFragment.Controller, NoteListFragment.Controller {
    private static final String TAG = "@@@ AppCompatActivity";
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new NoteArrayList();
        recyclerView = findViewById(R.id.recycler_main_view_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter();
        adapter.setData(repo.getNotes());
        recyclerView.setAdapter(adapter);
//        recyclerView.setAdapter(new NoteAdapter(repo));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.recycler_main_view_container, new NoteListFragment())
//                .commit();
//
//        findViewById(R.id.home_button).setOnClickListener(v -> {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.recycler_main_view_container, new NoteListFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
//        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
//        findViewById(R.id.add_button).setOnClickListener(v -> {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(isLandscape ? R.id.detail_container : R.id.recycler_main_view_container, new AddNewNoteFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
//
//        findViewById(R.id.settings_button).setOnClickListener(v -> {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(isLandscape ? R.id.detail_container : R.id.recycler_main_view_container, new SettingsFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
//    }
//
//    @Override
//    public void saveResult(NoteEntity dossier) {
//        //todo
//    }
//
//    @Override
//    public void openNoteScreen(NoteEntity note) {
//        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(isLandscape ? R.id.detail_container : R.id.recycler_main_view_container, NoteFragment.newInstance(note))
//                .addToBackStack(null)
//                .commit();
    }
}