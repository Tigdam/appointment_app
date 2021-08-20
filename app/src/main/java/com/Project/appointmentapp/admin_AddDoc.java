package com.Project.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class admin_AddDoc extends AppCompatActivity {

    private EditText Dname,Demail,Dpassword;
    private Button btn;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_doc);

        Dname= findViewById(R.id.nametxt);
        Demail= findViewById(R.id.emailtxt);
        Dpassword = findViewById(R.id.passwordtxt);
        progressBar = findViewById(R.id.progressBar);

        btn= findViewById(R.id.button);


        mAuth = FirebaseAuth.getInstance();


        //btn.setOnClickListener(v -> {
           /* HashMap<String,Object> map=new HashMap<>();
            map.put("DocName",Dname.getText().toString());
            map.put("Doc_Email",Demail.getText().toString());
            map.put("Doc_Password",Dpassword.getText().toString());




            FirebaseDatabase.getInstance().getReference().child("doctors").push()
                    .setValue(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.i("jfbvk","Oncomplete:");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("jfbvk","Onfailure" + e.toString());
                }
            });
            Intent intent = new Intent(admin_AddDoc.this, admin_doctor_list.class);
            startActivity(intent);*/
        btn.setOnClickListener(v -> {

            String email = Demail.getText().toString().trim();
            String password = Dpassword.getText().toString().trim();
            String fullName = Dname.getText().toString().trim();
            String uid = null;
            String doctor = "DOCTOR";



            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                nList userHelperClass_signup = new nList(uid, fullName, email, password, 1,doctor);

                                FirebaseDatabase.getInstance().getReference("all_users")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .setValue(userHelperClass_signup).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(admin_AddDoc.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                        else {
                                            if(task.isSuccessful()){
                                                Toast.makeText(admin_AddDoc.this, "Failed to registered ", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    }
                                });

                                FirebaseDatabase.getInstance().getReference("doctor_details")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .setValue(userHelperClass_signup).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(admin_AddDoc.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                        else {
                                            if(task.isSuccessful()){
                                                Toast.makeText(admin_AddDoc.this, "Failed to registered ", Toast.LENGTH_SHORT).show();
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