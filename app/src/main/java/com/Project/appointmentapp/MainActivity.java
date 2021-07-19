package com.Project.appointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button signin;
    TextView forgotpassword,sigupbtn;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.pat_log_email);
        editTextPassword = findViewById(R.id.pat_log_pass);
        forgotpassword = findViewById(R.id.pat_log_fpass);
        sigupbtn = findViewById(R.id.pat_log_signup);

        signin = findViewById(R.id.pat_log_submit);

        sigupbtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, signup.class));
        });

        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(password.isEmpty()){
                editTextPassword.setError("Password is Required");
                editTextPassword.requestFocus();
                return;
            }
            if(email.isEmpty()){
                editTextEmail.setError("Email is Required");
                editTextEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Please provide valid email");
                editTextEmail.requestFocus();
                return;
            }
            if(password.length()<6){
                editTextPassword.setError("Min password length should be 6 characters");
                editTextPassword.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        /*assert user != null;
                        if(user.isEmailVerified())
                        {
                            startActivity(new Intent(MainActivity.this, user_dashboard.class));
                        }
                        else
                        {
                            user.isEmailVerified();
                            Toast.makeText(MainActivity.this, "Check your email to verify your email", Toast.LENGTH_SHORT).show();
                        }*/
                        startActivity(new Intent(MainActivity.this, user_dashboard.class));

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Failed to login! Check your credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        });

        forgotpassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgetPassword.class);
            startActivity(intent);
        });

    }


}

