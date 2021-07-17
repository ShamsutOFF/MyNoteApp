package com.example.mynoteapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseNotesRepo implements NoteRepo {
    private static final String NOTES_TABLE_NAME = "notes";
    private static final String TAG = "@@@ FirebaseNotesRepo";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<Runnable> subscribers = new ArrayList<>();
    private List<NoteEntity> cache = new ArrayList<>();

    public FirebaseNotesRepo() {
        Log.d(TAG, "FirebaseNotesRepo() called");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(NOTES_TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(TAG, "onSuccess() called with: queryDocumentSnapshots = [" + queryDocumentSnapshots + "]");
                refillCache(queryDocumentSnapshots);
            }
        });
        db.collection(NOTES_TABLE_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d(TAG, "onEvent() called with: value = [" + value + "], error = [" + error + "]");
                refillCache(value);
            }
        });
    }


    private void refillCache(@Nullable QuerySnapshot snapshot) {
        Log.d(TAG, "refillCache() called with: snapshot = [" + snapshot + "]");
        if (snapshot == null) return;
        cache = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            cache.add(document.toObject(NoteEntity.class));
        }
        notifyNoteList();
    }

    private void notifyNoteList() {
        Log.d(TAG, "notifyNoteList() called");
        for (Runnable subscriber : subscribers) {
            subscriber.run();
        }
    }

    @Override
    public List<NoteEntity> getNotes() {
        Log.d(TAG, "getNotes() called and return: cache = [" + cache + "]");
        return cache;
    }

    @Override
    public void createNote(NoteEntity note) throws NoteCreationException {
        Log.d(TAG, "createNote() called with: note = [" + note + "]");
        db.collection(NOTES_TABLE_NAME).add(note);
    }

    @Override
    public void updateNote(NoteEntity note) {
        Log.d(TAG, "updateNote() called with: note = [" + note + "]");

    }

    @Override
    public void deleteNote(String id) {
        Log.d(TAG, "deleteNote() called with: id = [" + id + "]");
        db.collection(NOTES_TABLE_NAME).document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });


    }

    @Override
    public void subscribe(Runnable subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unSubscribe(Runnable subscriber) {
        subscribers.remove(subscriber);
    }
}
