package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button trail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trail=findViewById(R.id.signinbtn);
        trail.setOnClickListener(v -> {
            Intent registerScreen = new Intent(MainActivity.this, signup.class);
            startActivity(registerScreen);
        });
    }
}