package com.Project.appointmentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText email_txt, pass_txt;
    Button signing_btn;
    TextView forget_pass, signup_btn;

    String item = "admin";
    String userid, pass;
    DatabaseReference mreference;

    private static final String FILE_NAME = "state.txt";
    String state;
    String flag = null, uuid = null;
    private static long back_pressed;
    private FirebaseAuth defaultAuth;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_txt = findViewById(R.id.loginEmail);
        pass_txt = findViewById(R.id.password);

        forget_pass = findViewById(R.id.forgot_pass);
        signup_btn = findViewById(R.id.sign_in);
        signing_btn = findViewById(R.id.signinbtn);

        mDialog = new ProgressDialog(this);

        defaultAuth = FirebaseAuth.getInstance();
        redirectstate();
        FirebaseApp.initializeApp(this);
        mreference = FirebaseDatabase.getInstance().getReference("users");

        //Register page
        signup_btn.setOnClickListener(v -> {
            Intent registerScreen = new Intent(MainActivity.this, signup.class);
            startActivity(registerScreen);
        });

        //Forget Password
        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //forPassCustomDialog();
            }
        });

        //login 
        signing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userid = email_txt.getText().toString().trim();
                pass = pass_txt.getText().toString().trim();

                if (userid.isEmpty()) {
                    email_txt.setError("Please Enter Username");
                    return;
                } else if (pass.isEmpty()) {
                    pass_txt.setError("Enter Password");
                    return;
                } else {

                    mDialog.setTitle("Authenticating...");
                    mDialog.setMessage(userid);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();

                    checklogin();
                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void checklogin() {

        String mEmail, mPassword;
        mEmail = userid + "@gmail.com";
        mPassword = pass;

        defaultAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // checkEmailVerification();
                    verifyusercredential();
                } else {
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "UserId/Password is Incorrect or you aren't registered", Toast.LENGTH_LONG).show();
                    defaultAuth.signOut();

                }
            }

        });

    }

    public void checkEmailVerification(){

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        boolean emailflag=firebaseUser.isEmailVerified();

        if(emailflag){

            return;

        }
        else
        {
            sendEmailVerificaionLink();
            defaultAuth.signOut();
        }
    }


    private void sendEmailVerificaionLink(){

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Verification link sent..!",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Please Verify your email before Login",Toast.LENGTH_SHORT).show();
                        finish();
                        defaultAuth.signOut();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error...Verification link hasn't sent..!!",Toast.LENGTH_LONG).show();
                        finish();
                        defaultAuth.signOut();

                    }

                }
            });
        }
    }


    public void verifyusercredential() {


        mreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    item = dataSnapshot.child(userid).child("type").getValue(String.class);
                    verify(item);

                } catch (Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Account not found ..!", Toast.LENGTH_SHORT).show();
                    defaultAuth.signOut();
                    startActivity(getIntent());
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void verify(String item) {

        if (item.equals("patient")) {
            mDialog.dismiss();
            savests("2" + userid);
            Intent intent = new Intent(this, User_dashboard.class);
            intent.putExtra("tid", userid);
            startActivity(intent);
            finish();

        } else if (item.equals("doctor")) {
            mDialog.dismiss();
            savests("2" + userid);
            Intent intent = new Intent(this, doctor_dashboaard.class);
            intent.putExtra("tid", userid);
            startActivity(intent);
            finish();

        } else if (item.equals("admin")) {
            mDialog.dismiss();
            savests("1" + userid);
            Intent intent1 = new Intent(this, admin_dashboard.class);
            intent1.putExtra("tid", userid);
            startActivity(intent1);
            finish();
        } else {
            defaultAuth.signOut();
            Toast.makeText(getApplicationContext(), "UserId/Password is Incorrect or you aren't registered", Toast.LENGTH_LONG).show();
            mDialog.dismiss();

        }


        //end of emailverification

    }

    public void savests(String statevar) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(statevar.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    ((FileOutputStream) fos).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void redirectstate() {

        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }


            state = sb.toString();

            onstsbasicredirectfinalcheck(state);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void onstsbasicredirectfinalcheck(String st) {


        try {
            flag = st.substring(0, 1);
            uuid = st.substring(1).trim().toLowerCase();
        } catch (Exception e) {
        }

        if (flag.equals("3")) {
            Intent intenttt = new Intent(MainActivity.this, User_dashboard.class);
            intenttt.putExtra("tid", uuid);
            startActivity(intenttt);
            finish();
        } else if (flag.equals("2")) {
            Intent intenttt = new Intent(MainActivity.this, doctor_dashboaard.class);
            intenttt.putExtra("tid", uuid);
            startActivity(intenttt);
            finish();
        } else if (flag.equals("1")) {
            Intent intentt = new Intent(MainActivity.this, admin_dashboard.class);
            intentt.putExtra("tid", uuid);
            startActivity(intentt);
            finish();
        }


    }


//    private void forPassCustomDialog()
//    {
//        final AlertDialog.Builder myDialog =new AlertDialog.Builder(MainActivity.this);
//        LayoutInflater inflater =LayoutInflater.from(MainActivity.this);
//
//        final View myview=inflater.inflate(R.layout.forgot_pass,null);
//
//        final AlertDialog dialog=myDialog.create();
//        dialog.setView(myview);
//
//        final EditText forpassEmail=myview.findViewById(R.id.forpassemail_et);
//        Button resetpass_btn=myview.findViewById(R.id.forpass_bt);
//
//        resetpass_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mEmail=forpassEmail.getText().toString().trim();
//                if(TextUtils.isEmpty(mEmail))
//                {
//                    forpassEmail.setError("Please Enter Valid Email..!");
//                    return;
//                }
//
//                mDialog.setMessage("Sending Reset Link...");
//                mDialog.show();
//                defaultAuth.sendPasswordResetEmail(mEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful())
//                        {
//                            dialog.dismiss();
//                            mDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Reset link sent to your Email..!", Toast.LENGTH_LONG).show();
//
//                        }
//                        else
//                        {
//                            mDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Error...in sending Password Reset Link", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });
//
//        dialog.show();
//    }


}

