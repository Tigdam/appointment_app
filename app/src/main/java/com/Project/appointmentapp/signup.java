package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    EditText regUsername, regEmail, regPassword, conifirm_pass;
    Button singup_btn;
    TextView signin;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.emailid);
        regPassword = findViewById(R.id.password);
        conifirm_pass = findViewById(R.id.confirm_password);

        singup_btn = findViewById(R.id.signupbtn);

        signin = findViewById(R.id.sign_in);

        //sign in takes to login screen
        signin.setOnClickListener(v -> {
            Intent loginscreen = new Intent(signup.this, MainActivity.class);
            startActivity(loginscreen);
        });

        singup_btn.setOnClickListener(v -> {

            //check password confirmation
            confirm_password();

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            String username = regUsername.getText().toString();
            String email = regEmail.getText().toString();
            String password = regPassword.getText().toString();
            String type = "patient";

            UserHelperClass_signup helperClass = new UserHelperClass_signup(username, email, password, type);
            reference.child(username).setValue(helperClass);




        });




    }

    private void confirm_password() {
        if(!conifirm_pass.equals(regPassword))
        {
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
        }


    }
}