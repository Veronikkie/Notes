package com.example.notes.ui.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.notes.R;
import com.example.notes.entity.Note;
import com.example.notes.ui.adapter.holder.NoteViewHolder;

public class UpdateNoteActivity extends AppCompatActivity {

    public static final String UPDATED_NOTE_KEY_EXTRA = "updated_note_key_extra";

    private EditText titleEditText;
    private EditText descriptionEditText;

    private Note selectedNote;

    private String title;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        long noteId = getIntent().getLongExtra(NoteViewHolder.SELECTED_NOTE_ID_KEY, -1);
        selectedNote = Note.findById(Note.class, noteId);

        initViews();
        initActionBar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        updateNoteInDatabase();

        super.onBackPressed();
    }

    private void initViews() {
        titleEditText = findViewById(R.id.titleEdit);
        descriptionEditText = findViewById(R.id.descriptionEdit);

        titleEditText.setText(selectedNote.getTitle());
        descriptionEditText.setText(selectedNote.getDescription());
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.update_note_action_bar_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void updateNoteInDatabase() {
        title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString();

        if (isTitleUpdated() || isDescriptionUpdated()) {
            selectedNote.setTitle(title);
            selectedNote.setDescription(description);
            selectedNote.save();

            Intent intent = new Intent();

            intent.putExtra(UPDATED_NOTE_KEY_EXTRA, selectedNote);
            setResult(RESULT_OK, intent);
        }
    }

    private boolean isTitleUpdated() {
        return !title.equals(selectedNote.getTitle());
    }

    private boolean isDescriptionUpdated() {
        return !description.equals(selectedNote.getDescription());
    }
}
