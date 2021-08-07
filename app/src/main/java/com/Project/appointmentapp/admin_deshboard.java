package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class admin_deshboard extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deshboard);

        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            startActivity(new Intent(admin_deshboard.this, admin_AddDoc.class));
        });
    }
}