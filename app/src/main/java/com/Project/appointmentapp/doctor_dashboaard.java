package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class doctor_dashboaard extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboaard);

        imageView = findViewById(R.id.docimg);

        imageView.setOnClickListener(v -> {
            startActivity(new Intent(doctor_dashboaard.this, dr_edit_profile.class));
        });
    }
}