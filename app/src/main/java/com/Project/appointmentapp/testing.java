package com.Project.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class testing extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;

    String uid;

    TextView nametxt, dobtxt, gendertxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        nametxt = findViewById(R.id.name);
        gendertxt = findViewById(R.id.gender);
        dobtxt = findViewById(R.id.dob);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Patient_deatails");
        uid = user.getUid();


        final TextView nameTextView = nametxt;

        final TextView dobTextView = dobtxt;

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserHelperClass_patedit testingpage = snapshot.getValue(UserHelperClass_patedit.class);

                if(testingpage != null)
                {
                    String fname = testingpage.fname;
                    String dob = testingpage.dob;



                    nameTextView.setText(fname);
                    dobTextView.setText((dob));

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(testing.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });





    }
}