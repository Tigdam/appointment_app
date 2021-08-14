package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
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

public class dr_edit_profile extends AppCompatActivity {

    ImageView imageView;
    Button save,cancel;
    EditText docFname, docLname, docEmail, docAddress, docMob, docQualification, docExp, docSpec,
            docFTime, docTTime, docAbout;
    Spinner docGender,docAvail;
    private int mYear, mMonth, mDay;
    TextView docDOB;
    FirebaseUser user;

    String userID;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_edit_profile);

        imageView =findViewById(R.id.docimg);
        docFname=findViewById(R.id.doc_ep_fname);
        docLname=findViewById(R.id.doc_ep_lname);
        docEmail=findViewById(R.id.doc_ep_emailid);
        docAddress=findViewById(R.id.doc_ep_add);
        docGender=findViewById(R.id.doc_ep_gender_spinner);
        docMob=findViewById(R.id.doc_ep_mobile);
        docDOB=findViewById(R.id.doc_ep_dob);
        docQualification=findViewById(R.id.doc_ep_qual);
        docExp=findViewById(R.id.doc_ep_exp);
        docSpec=findViewById(R.id.doc_ep_spec);
        docAvail=findViewById(R.id.doc_ep_avail_spinner);
        docFTime=findViewById(R.id.doc_ep_ftime);
        docTTime=findViewById(R.id.doc_ep_ttime);
        docAbout=findViewById(R.id.doc_ep_about);
        save=findViewById(R.id.save);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        docGender.setAdapter(adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.availability_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        docAvail.setAdapter(adapter1);

        docDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == docDOB) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(dr_edit_profile.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    docDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                    datePickerDialog.show();
                }




            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("doctors");
        userID = user.getUid();

        save.setOnClickListener(v->{
           rootNode=FirebaseDatabase.getInstance();
            reference = rootNode.getReference("doctor_details");

            String uid = userID;
            String Doc_Fname=docFname.getText().toString();
            String Doc_Lname=docLname.getText().toString();
            String Doc_Email=docEmail.getText().toString();
            String Doc_Mob=docMob.getText().toString();
            String Doc_DOB=docDOB.getText().toString();
            String Doc_Qual=docQualification.getText().toString();
            String Doc_Exp=docExp.getText().toString();
            String Doc_Spec=docSpec.getText().toString();
            String Doc_FTimme=docFTime.getText().toString();
            String Doc_TTime=docTTime.getText().toString();
            String Doc_About=docAbout.getText().toString();
            String Doc_Add=docAddress.getText().toString();



            docGender=findViewById(R.id.doc_ep_gender_spinner);

            docAvail=findViewById(R.id.doc_ep_avail_spinner);

            UserHelperClass_DocEP helperClass=new UserHelperClass_DocEP(Doc_Fname,Doc_Lname,Doc_Email,Doc_Mob,Doc_DOB,Doc_Qual,Doc_Exp,Doc_Spec,Doc_FTimme,Doc_TTime,Doc_About,Doc_Add,uid);
            reference.child(Doc_Fname).setValue(helperClass);

        });

    }
}