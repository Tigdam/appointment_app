package com.Project.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class admin_doctor_list extends AppCompatActivity {
    private ImageView add;
    private RecyclerView recyclerView;
    private nAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_doctor_list);

        add= findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_doctor_list.this, admin_AddDoc.class);
                startActivity(intent);

            }
        });

        recyclerView= findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<nList> options =
                new FirebaseRecyclerOptions.Builder<nList>()

                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctors"),nList.class)
                        .setLifecycleOwner(this)
                        .build();
        nAdaptor adapter= new nAdaptor(options,this);
        recyclerView.setAdapter(adapter);
    }
}