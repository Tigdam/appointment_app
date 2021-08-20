package com.Project.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class doctor_login extends AppCompatActivity {

    TextView gotoAdmin_textView, forgotpassword;
    EditText editTextEmail, editTextPassword;
    Button signin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        editTextEmail = findViewById(R.id.doc_log_email);
        editTextPassword = findViewById(R.id.doc_log_pass);
        forgotpassword = findViewById(R.id.doc_log_fpass);
        signin = findViewById(R.id.doc_log_singin);
        gotoAdmin_textView = findViewById(R.id.admin_log_signup);

        gotoAdmin_textView.setOnClickListener(v -> {
            startActivity(new Intent(doctor_login.this, admin_login.class));
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
                        String uid = task.getResult().getUser().getUid();


                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                        firebaseDatabase.getReference().child("all_users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int usertype = snapshot.getValue(Integer.class);
                                if(usertype == 1){
                                    startActivity(new Intent(doctor_login.this, doctor_dashboaard.class));
                                }
                                if(usertype == 2){
                                    startActivity(new Intent(doctor_login.this, admin_deshboard.class));
                                }








                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                //Toast.makeText(MainActivity.this, "Check your email to verify your email", Toast.LENGTH_SHORT).show();

                            }
                        });
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


                    }
                    else {
                        Toast.makeText(doctor_login.this, "Failed to login! Check your credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        });

        forgotpassword.setOnClickListener(v -> {
            Intent intent = new Intent(doctor_login.this, ForgetPassword.class);
            startActivity(intent);
        });



    }
}