package com.example.mynoteapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private static final String TAG = "@@@ NoteAdapter";
    private List<NoteEntity> data;

    public void setData(List<NoteEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
//        Log.d(TAG, "onBindViewHolder() return = [" + dataSource[position] + "]");
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount() called" + dataSource.length);
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTv = itemView.findViewById(R.id.note_item_title_text_view);
        private final TextView dateTv = itemView.findViewById(R.id.note_item_date_text_view);

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
        }

        public void bind(NoteEntity note) {
            titleTv.setText(note.getTitle());
            dateTv.setText(note.getDate());
        }
    }
}
