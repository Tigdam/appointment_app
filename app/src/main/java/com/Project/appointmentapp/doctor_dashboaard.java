package com.Project.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class doctor_dashboaard extends AppCompatActivity {

    ImageView imageView;

    FirebaseUser user;
    DatabaseReference reference;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboaard);

        imageView = findViewById(R.id.docimg);

        imageView.setOnClickListener(v -> {
            startActivity(new Intent(doctor_dashboaard.this, dr_edit_profile.class));
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("all_users");
        userID = user.getUid();



        final TextView DocnameTextView = (TextView) findViewById(R.id.doctor_name);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nList userDashboard = snapshot.getValue(nList.class);

                if(userDashboard != null)
                {
                    String fullName = userDashboard.DocName;

                    DocnameTextView.setText(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(doctor_dashboaard.this, "Something went wrong!"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}