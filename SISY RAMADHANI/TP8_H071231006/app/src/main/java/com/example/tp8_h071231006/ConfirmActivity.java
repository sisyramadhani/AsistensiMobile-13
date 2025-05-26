package com.example.tp8_h071231006;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        String type = getIntent().getStringExtra("type");
        TextView tvMessage = findViewById(R.id.tv_message);
        Button btnNo = findViewById(R.id.btn_no);
        Button btnYes = findViewById(R.id.btn_yes);

        if (type.equals("add")) {
            tvMessage.setText("Apakah anda ingin membatalkan penambahan pada form?");
        } else {
            tvMessage.setText("Apakah anda ingin membatalkan perubahan pada form?");
        }

        btnNo.setOnClickListener(v -> finish());

        btnYes.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}
