package com.example.tp8_h071231006;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        TextView tvHeaderTitle = findViewById(R.id.tv_title);
        tvHeaderTitle.setText("Tambah");

        databaseHelper = new DatabaseHelper(this);
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);

        Button btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (!title.isEmpty()) {
                long id = databaseHelper.addNote(title, description);
                if (id != -1) {
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            } else {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCancelConfirmation() {
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra("type", "add");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            finish();
        }
    }

    public void handleBackClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Batal")
                .setMessage("Apakah anda ingin membatalkan penambahan pada form?")
                .setPositiveButton("YA", (dialog, which) -> finish())
                .setNegativeButton("TIDAK", null)
                .show();
    }
}
