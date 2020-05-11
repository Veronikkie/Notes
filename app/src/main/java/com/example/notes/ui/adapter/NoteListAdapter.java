package com.example.notes.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.entity.Note;
import com.example.notes.ui.activity.MainActivity;
import com.example.notes.ui.adapter.holder.NoteViewHolder;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private MainActivity activity;

    private List<Note> notesList;
    private Note currentNote;

    public NoteListAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_note_list, parent, false);

        return new NoteViewHolder(itemView, activity.new OnItemClickListener(),
                activity.new OnItemLongClickListener());
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        currentNote = notesList.get(position);

        holder.bind(currentNote);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void insertNoteToList(Note note) {
        notesList.add(note);
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public List<Note> getNotesList() {
        return notesList;
    }
}
