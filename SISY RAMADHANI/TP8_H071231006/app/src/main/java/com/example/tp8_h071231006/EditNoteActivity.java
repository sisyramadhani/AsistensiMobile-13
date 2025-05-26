package com.example.tp8_h071231006;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private DatabaseHelper databaseHelper;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        databaseHelper = new DatabaseHelper(this);
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        ImageView ivDelete = findViewById(R.id.iv_delete);
        Button btnUpdate = findViewById(R.id.btn_update);

        //judul header
        TextView tvHeaderTitle = findViewById(R.id.tv_title);
        tvHeaderTitle.setText("Ubah");

        note = (Note) getIntent().getSerializableExtra("note");
        if (note != null) {
            etTitle.setText(note.getTitle());
            etDescription.setText(note.getDescription());
        }

        btnUpdate.setOnClickListener(v -> updateNote());

        ivDelete.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void updateNote() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (!title.isEmpty()) {
            note.setTitle(title);
            note.setDescription(description);
            int rowsAffected = databaseHelper.updateNote(note);
            if (rowsAffected > 0) {
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Note")
                .setMessage("Apakah Anda yakin ingin menghapus note ini?")
                .setPositiveButton("YA", (dialog, which) -> {
                    databaseHelper.deleteNote(note.getId());
                    setResult(RESULT_OK);
                    finish();
                })
                .setNegativeButton("TIDAK", null)
                .show();
    }

    public void handleBackClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Batal")
                .setMessage("Apakah anda ingin membatalkan perubahan pada form?")
                .setPositiveButton("YA", (dialog, which) -> finish())
                .setNegativeButton("TIDAK", null)
                .show();
    }
}