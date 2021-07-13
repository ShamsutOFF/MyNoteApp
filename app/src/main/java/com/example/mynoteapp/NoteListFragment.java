package com.example.mynoteapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteListFragment extends Fragment {

    private static final String TAG = "@@@ NoteListFragment";
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepo repo;

    private LinearLayout linearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        repo = new NoteArrayList();
        recyclerView = view.findViewById(R.id.recycler_view);

//        adapter.setData(repo.getNotes());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        linearLayout = view.findViewById(R.id.linear);
        adapter = new NoteAdapter();
        adapter.setOnItemClickListener(getContract()::editNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        renderList(repo.getNotes());
        adapter.setData(repo.getNotes());
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new RuntimeException("Activity must implements Contract!");
        }
    }

    public void addNote(NoteEntity newNote) throws NoteRepo.NoteCreationException {
        Log.d(TAG, "addNote() called with: newNote = [" + newNote + "]");
        NoteEntity sameNote = findNoteWithId(newNote.id);
        if (sameNote != null) {
            repo.deleteNote(sameNote.id);
        }
        repo.createNote(newNote);
        renderList(repo.getNotes());
    }

    @Nullable
    private NoteEntity findNoteWithId(String id) {
        Log.d(TAG, "findNoteWithId() called with: id = [" + id + "]");
        for (NoteEntity note : repo.getNotes()) {
            if (note.id.equals(id)) {
                return note;
            }
        }
        return null;
    }

    private void renderList(List<NoteEntity> notes) {
        Log.d(TAG, "renderList() called with: notes = [" + notes + "]");
        adapter.setData(notes);
    }

    Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void createNewNote();

        void editNote(NoteEntity note);
    }
}
