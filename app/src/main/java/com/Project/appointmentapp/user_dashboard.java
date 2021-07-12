package com.Project.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class user_dashboard extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;

    String userID;

    TextView logout;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        logout = findViewById(R.id.logoutbtn);
        imageView = findViewById(R.id.pat_profile_pic);

        imageView.setOnClickListener(v -> {
            startActivity(new Intent(user_dashboard.this, User_Profile.class));

        });

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(user_dashboard.this, MainActivity.class));
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView nameTextView = (TextView) findViewById(R.id.user_name);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserHelperClass_signup userProfile = snapshot.getValue(UserHelperClass_signup.class);

                if(userProfile != null)
                {
                    String fullName = userProfile.fullName;

                    nameTextView.setText(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(user_dashboard.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}