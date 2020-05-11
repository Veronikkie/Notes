package com.example.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.entity.Note;
import com.example.notes.ui.adapter.NoteListAdapter;
import com.example.notes.ui.adapter.holder.NoteViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orm.SugarRecord;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CREATE_NOTE_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_REQUEST_CODE = 2;

    private RecyclerView recyclerView;
    private NoteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        showAllNotes();
    }

    private void initViews() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                startActivityForResult(intent, CREATE_NOTE_REQUEST_CODE);
            }
        });


        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        adapter = new NoteListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void showAllNotes() {
        List<Note> notes = Note.listAll(Note.class);

        adapter.setNotesList(notes != null ? notes : new ArrayList<Note>());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Note note = SugarRecord.last(Note.class);
                adapter.insertNoteToList(note);
                adapter.notifyItemInserted(adapter.getNotesList().size() - 1);
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        }

        if (requestCode == UPDATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showAllNotes();
            }
        }
    }

    public class OnItemClickListener implements View.OnClickListener {

        private Note selectedNote;

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), UpdateNoteActivity.class);
            intent.putExtra(NoteViewHolder.SELECTED_NOTE_ID_KEY, selectedNote.getId());
            startActivityForResult(intent, UPDATE_NOTE_REQUEST_CODE);
        }

        public void setSelectedNote(Note selectedNote) {
            this.selectedNote = selectedNote;
        }
    }

    public class OnItemLongClickListener implements View.OnLongClickListener {

        private Note selectedNote;

        @Override
        public boolean onLongClick(View v) {
            List<Note> noteList = adapter.getNotesList();
            int itemPosition = noteList.indexOf(selectedNote);

            noteList.remove(selectedNote);

            Note.delete(selectedNote);

            adapter.notifyItemRemoved(itemPosition);

            return true;
        }

        public void setSelectedNote(Note selectedNote) {
            this.selectedNote = selectedNote;
        }
    }
}
