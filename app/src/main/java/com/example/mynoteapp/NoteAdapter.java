package com.example.mynoteapp;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private static final String TAG = "@@@ NoteAdapter";
    private List<NoteEntity> data;
    private OnItemClickListener onItemClickListener;
    private NoteRepo repo;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<NoteEntity> data) {
        Log.d(TAG, "setData() called with: data = [" + data + "]");
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
        return new ViewHolder(parent, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount() called");
        return data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NoteEntity note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
        private final TextView titleTv;
        private final TextView dateTv;
        private final CardView cardView;
        private NoteEntity noteEntity;
        private final RecyclerView recyclerView;

        public ViewHolder(ViewGroup parent, OnItemClickListener clickListener) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
            repo = new FirebaseNotesRepo();
            recyclerView = parent.findViewById(R.id.recycler_view);
            cardView = (CardView) itemView;
            titleTv = itemView.findViewById(R.id.note_item_title_text_view);
            dateTv = itemView.findViewById(R.id.note_item_date_text_view);
            cardView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onItemClick(noteEntity);
                    Log.d(TAG, "ViewHolder() called with: parent = [" + parent + "], clickListener = [" + clickListener + "], noteEntity = [" + noteEntity + "]");
                }
            });
            Log.d(TAG, "ViewHolder() called with: parent = [" + parent + "]");
        }

        public void bind(NoteEntity noteEntity) {
            Log.d(TAG, "bind() called with: note = [" + noteEntity + "]");
            this.noteEntity = noteEntity;
            titleTv.setText(noteEntity.getTitle());
            dateTv.setText(noteEntity.getDate());

            itemView.setOnLongClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
                popupMenu.inflate(R.menu.note_item_menu);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
                return true;
            });
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.update_note_item_menu) {
                onItemClickListener.onItemClick(noteEntity);
            }
            if (item.getItemId() == R.id.double_note_item_menu) {
                try {
                    repo.createNote(new NoteEntity(NoteEntity.generateNewId(), noteEntity.title, noteEntity.dateOfCreation, noteEntity.content));
                } catch (NoteRepo.NoteCreationException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(data.size() - 1);
            }
            if (item.getItemId() == R.id.delete_note_item_menu) {
                showAlertDialog();
            }
            return true;
        }

        private void showAlertDialog() {
            new AlertDialog.Builder(cardView.getContext())
                    .setTitle(R.string.alert_title)
                    .setMessage(R.string.alert_message)
                    .setPositiveButton(R.string.yes, (d, i) -> {
                        repo.deleteNote(noteEntity.id);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, (d, i) -> {
                        Toast.makeText(itemView.getContext(), "Вы отменили удаление", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        }
    }
}
