package com.example.notebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private ListView notesListView;
    private TextView selectedNoteTextView;
    private DatabaseHelper dbHelper;
    private List<DatabaseHelper.Note> notesList;
    private int selectedNoteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        notesListView = findViewById(R.id.notesListView);
        selectedNoteTextView = findViewById(R.id.selectedNoteTextView);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button addButton = findViewById(R.id.addButton);
        Button aboutButton = findViewById(R.id.aboutButton);
        Button exitButton = findViewById(R.id.exitButton);
        loadNotes();

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper.Note selectedNote = notesList.get(position);
                selectedNoteId = selectedNote.getId();
                selectedNoteTextView.setText("Выбрано: " + selectedNote.getTitle() +
                        "\nДата: " + selectedNote.getDate() +
                        "\nСодержание: " + selectedNote.getContent());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedNoteId != -1) {
                    dbHelper.deleteNote(selectedNoteId);
                    loadNotes();
                    selectedNoteTextView.setText("");
                    selectedNoteId = -1;
                    Toast.makeText(MainActivity.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Выберите запись для удаления", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotepadActivity.class));
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        notesList = dbHelper.getAllNotes();
        ArrayAdapter<DatabaseHelper.Note> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, notesList);
        notesListView.setAdapter(adapter);
    }
}