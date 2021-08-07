package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class doctor_login extends AppCompatActivity {

    TextView gotoAdmin_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        gotoAdmin_textView = findViewById(R.id.admin_log_signup);

        gotoAdmin_textView.setOnClickListener(v -> {
            startActivity(new Intent(doctor_login.this, admin_login.class));
        });
    }
}