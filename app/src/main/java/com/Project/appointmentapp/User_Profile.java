package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class User_Profile extends AppCompatActivity {

    FirebaseUser user;

    String userID;

    ImageView imageView;
    Button patSaveBtn;
    EditText patFname, patLname, patEmail, patMob, patProfession, patWeight, patHeight, patHistory, patAddress;
    private int mYear, mMonth, mDay;
    Spinner patGender;
    TextView patDOB;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageView = findViewById(R.id.profile_img);
//        date_of_birth = findViewById(R.id.dob);
        patFname=findViewById(R.id.pat_ep_fullname);
        patEmail=findViewById(R.id.pat_ep_emailid);
        patDOB=findViewById(R.id.pat_ep_dob);
        patGender=findViewById(R.id.pat_ep_gender_spinner);
        patMob=findViewById(R.id.pat_ep_mobile);
        patProfession=findViewById(R.id.pat_ep_prof);
        patWeight=findViewById(R.id.pat_ep_weight);
        patHeight=findViewById(R.id.pat_ep_height);
        patHistory=findViewById(R.id.pat_ep_medical);
        patAddress=findViewById(R.id.pat_ep_address);
        patSaveBtn=findViewById(R.id.save);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        patGender.setAdapter(adapter);

        imageView.setOnClickListener(v -> {
            startActivity(new Intent(User_Profile.this, testing.class));
        });

        patDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == patDOB) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(User_Profile.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    patDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                   // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                    datePickerDialog.show();
                }




            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        patSaveBtn.setOnClickListener(v-> {


            rootNode=FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Patient_deatails");
            String uid = userID;
            String fname = patFname.getText().toString();
            String email = patEmail.getText().toString();
            String dob = patDOB.getText().toString();
            String mob = patMob.getText().toString();
            String gen = patGender.toString();
            String prof = patProfession.getText().toString();
            String weight = patWeight.getText().toString();
            String height = patHeight.getText().toString();
            String history = patHistory.getText().toString();
            String address = patAddress.getText().toString();

            if(fname.isEmpty()){
                patFname.setError("Full Name is Required");
                patFname.requestFocus();
                return;
            }
            if(email.isEmpty()){
                patEmail.setError("Email is Required");
                patEmail.requestFocus();
                return;
            }
            if(dob.isEmpty()){
                patDOB.setError("DOB is Required");
                patDOB.requestFocus();
                return;
            }
            if(mob.isEmpty()){
                patMob.setError("Mobile Number is Required");
                patMob.requestFocus();
                return;
            }
            if(prof.isEmpty()){
                patProfession.setError("Profession is Required");
                patProfession.requestFocus();
                return;
            }
            if(weight.isEmpty()){
                patWeight.setError("Weight is Required");
                patWeight.requestFocus();
                return;
            }
            if(height.isEmpty()){
                patHeight.setError("Height is Required");
                patHeight.requestFocus();
                return;
            }
            if(history.isEmpty()){
                patHistory.setError("History is Required");
                patHistory.requestFocus();
                return;
            }
            if(address.isEmpty()){
                patAddress.setError("Address is Required");
                patAddress.requestFocus();
                return;
            }


            UserHelperClass_patedit helperClass = new UserHelperClass_patedit(uid,fname,email, dob,mob,gen,prof,weight,height,history,address);
            reference.child(uid).setValue(helperClass);


        });
    }

}