package com.Project.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class admin_login extends AppCompatActivity {

    EditText adminEmail, adminPassword;
    Button adminLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminEmail = findViewById(R.id.admin_log_email);
        adminPassword = findViewById(R.id.admin_log_pass);
        adminLogin = findViewById(R.id.admin_log_submit);

        mAuth = FirebaseAuth.getInstance();

        adminLogin.setOnClickListener(v -> {

            String userEnterEmail = adminEmail.getText().toString().trim();
            String userEnteredPassword = adminPassword.getText().toString().trim();

            /*if(password.isEmpty()){
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
*/
            mAuth.signInWithEmailAndPassword(userEnterEmail, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                        startActivity(new Intent(admin_login.this, admin_deshboard.class));

                    }
                    else {
                        Toast.makeText(admin_login.this, "Failed to login! Check your credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        });



    }
}