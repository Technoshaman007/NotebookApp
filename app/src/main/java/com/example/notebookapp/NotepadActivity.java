package com.example.notebookapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NotepadActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        dbHelper = new DatabaseHelper(this);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button homeButton = findViewById(R.id.homeButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(NotepadActivity.this,
                            "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addNote(title, content);
                    Toast.makeText(NotepadActivity.this,
                            "Запись сохранена", Toast.LENGTH_SHORT).show();
                    titleEditText.setText("");
                    contentEditText.setText("");
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
