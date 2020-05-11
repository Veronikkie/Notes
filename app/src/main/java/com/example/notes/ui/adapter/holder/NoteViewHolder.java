package com.example.notes.ui.adapter.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.entity.Note;
import com.example.notes.ui.activity.MainActivity;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public static final String SELECTED_NOTE_ID_KEY = "selected_note_id_key";

    private MainActivity.OnItemClickListener onHolderClickListener;
    private MainActivity.OnItemLongClickListener onHolderLongClickListener;

    private LinearLayout container;

    private TextView titleView;
    private TextView descriptionView;

    private String title;
    private String description;

    public NoteViewHolder(View itemView, MainActivity.OnItemClickListener onHolderClickListener,
                          MainActivity.OnItemLongClickListener onHolderLongClickListener) {
        super(itemView);

        this.onHolderClickListener = onHolderClickListener;
        this.onHolderLongClickListener = onHolderLongClickListener;

        container = itemView.findViewById(R.id.container);
        titleView = itemView.findViewById(R.id.title);
        descriptionView = itemView.findViewById(R.id.description);
    }

    public void bind(Note note) {

        title = note.getTitle();
        description = note.getDescription();

        onHolderClickListener.setSelectedNote(note);
        onHolderLongClickListener.setSelectedNote(note);

        initViews();
    }

    private void initViews() {
        if (title != null && !title.isEmpty()) {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }

        if (description != null && !description.isEmpty()) {
            descriptionView.setText(description);
            descriptionView.setVisibility(View.VISIBLE);
        } else {
            descriptionView.setVisibility(View.GONE);
        }

        container.setOnClickListener(onHolderClickListener);
        container.setOnLongClickListener(onHolderLongClickListener);
    }
}
