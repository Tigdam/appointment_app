package com.Project.appointmentapp;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

public class ForgetPassword extends AppCompatActivity {

    EditText emailEditText;
    Button resetpasswordButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEditText = findViewById(R.id.forget_password_Email);
        resetpasswordButton = findViewById(R.id.forget_password);

        mAuth = FirebaseAuth.getInstance();

        resetpasswordButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();

            if(email.isEmpty()){
                emailEditText.setError("Email is Required");
                emailEditText.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Please provide valid email");
                emailEditText.requestFocus();
                return;
            }

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ForgetPassword.this, "Check your email to reset the password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ForgetPassword.this, "Try again! Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });

    }
}