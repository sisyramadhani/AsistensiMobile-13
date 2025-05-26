package com.example.tp8_h071231006;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<Note> notes;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public NoteAdapter(ArrayList<Note> notes, OnItemClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false); // mengambil layout untuk add baru
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note, listener);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateList(ArrayList<Note> newList) {
        notes = newList;
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription, tvTimestamp;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
        }

        public void bind(final Note note, final OnItemClickListener listener) {
            tvTitle.setText(note.getTitle());
            tvDescription.setText(note.getDescription());

            if (note.getCreatedAt().equals(note.getUpdatedAt())) {
                tvTimestamp.setText("Created at " + note.getCreatedAt());
            } else {
                tvTimestamp.setText("Updated at " + note.getUpdatedAt());
            }

            itemView.setOnClickListener(v -> listener.onItemClick(note));
        }
    }
}