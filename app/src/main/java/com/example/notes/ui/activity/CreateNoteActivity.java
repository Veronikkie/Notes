package com.example.notes.ui.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.notes.R;
import com.example.notes.entity.Note;
import com.orm.SugarRecord;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;

    private String title;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        titleEditText = findViewById(R.id.titleEdit);
        descriptionEditText = findViewById(R.id.descriptionEdit);

        initActionBar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (areTitleAndDescriptionNotEmpty()) {
            insertNoteToDatabase();
        }

        super.onBackPressed();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.new_note_action_bar_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private boolean areTitleAndDescriptionNotEmpty() {
        title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString();

        return !title.isEmpty() || !description.isEmpty();
    }

    private void insertNoteToDatabase() {
        Note note = new Note(title, description);
        SugarRecord.save(note);

        setResult(RESULT_OK);
    }
}
