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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button signin;
    TextView forgotpassword,sigupbtn, gotoDoctor;

    FirebaseAuth mAuth;
    String type = "doctor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.pat_log_email);
        editTextPassword = findViewById(R.id.pat_log_pass);
        forgotpassword = findViewById(R.id.pat_log_fpass);
        sigupbtn = findViewById(R.id.pat_log_signup);
        gotoDoctor = findViewById(R.id.doc_log);

        gotoDoctor.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, doctor_login.class));
        });

        signin = findViewById(R.id.pat_log_submit);

        sigupbtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, signup.class));
        });

        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(v -> {
            final String[] email = {editTextEmail.getText().toString().trim()};
            String password = editTextPassword.getText().toString().trim();


            if(password.isEmpty()){
                editTextPassword.setError("Password is Required");
                editTextPassword.requestFocus();
                return;
            }
            if(email[0].isEmpty()){
                editTextEmail.setError("Email is Required");
                editTextEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email[0]).matches()) {
                editTextEmail.setError("Please provide valid email");
                editTextEmail.requestFocus();
                return;
            }
            if(password.length()<6){
                editTextPassword.setError("Min password length should be 6 characters");
                editTextPassword.requestFocus();
                return;
            }



            mAuth.signInWithEmailAndPassword(email[0], password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        String uid = task.getResult().getUser().getUid();


                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        firebaseDatabase.getReference().child("Users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int usertype = snapshot.getValue(Integer.class);


                                if(usertype == 0){
                                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, user_dashboard.class));
                                }



                                //Toast.makeText(MainActivity.this, "Check your email to verify your email", Toast.LENGTH_SHORT).show();






                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {


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

