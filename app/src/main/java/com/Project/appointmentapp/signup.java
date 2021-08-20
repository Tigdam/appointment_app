package com.Project.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    EditText regUsername, regEmail, regPassword, conifirm_pass;
    Button singup_btn;
    TextView signin;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        regUsername = findViewById(R.id.pat_reg_username);
        regEmail = findViewById(R.id.pat_reg_email);
        regPassword = findViewById(R.id.pat_reg_pass);
        conifirm_pass = findViewById(R.id.pat_reg_cpass);

        progressBar = findViewById(R.id.progressBar);

        singup_btn = findViewById(R.id.pat_reg_submit);

        signin = findViewById(R.id.pat_reg_signin);

        mAuth = FirebaseAuth.getInstance();

        //sign in takes to login screen
        signin.setOnClickListener(v -> {
            Intent loginscreen = new Intent(signup.this, MainActivity.class);
            startActivity(loginscreen);
        });

        singup_btn.setOnClickListener(v -> {

            String email = regEmail.getText().toString().trim();
            String password = regPassword.getText().toString().trim();
            String fullName = regUsername.getText().toString().trim();
            String uid = null;

            if(fullName.isEmpty()){
                regUsername.setError("Full Name is Required");
                regUsername.requestFocus();
                return;
            }
            if(password.isEmpty()){
                regPassword.setError("Password is Required");
                regPassword.requestFocus();
                return;
            }
            if(email.isEmpty()){
                regEmail.setError("Full Name is Required");
                regEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                regEmail.setError("Please provide valid email");
                regEmail.requestFocus();
                return;
            }
            if(password.length()<6){
                regPassword.setError("Min password length should be 6 characters");
                regPassword.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UserHelperClass_signup userHelperClass_signup = new UserHelperClass_signup(uid, fullName, email, password, 2);

                               /* String uid =  Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                                firebaseDatabase.getReference().child("Users").child(uid).setValue(userHelperClass_signup);*/

                                
                                FirebaseDatabase.getInstance().getReference("Patient_deatails")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .setValue(userHelperClass_signup).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(signup.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                        else {
                                            if(task.isSuccessful()){
                                                Toast.makeText(signup.this, "Failed to registered ", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    }
                                });

                                FirebaseDatabase.getInstance().getReference("all_users")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .setValue(userHelperClass_signup).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(signup.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                        else {
                                            if(task.isSuccessful()){
                                                Toast.makeText(signup.this, "Failed to registered ", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    }
                                });


                            }
                        }
                    });

        });


    }



}
