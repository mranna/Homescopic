package com.example.hemanthanil.homescopic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homegallery extends AppCompatActivity {


    TextView tester;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mcondtionRef=mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homegallery);
        tester=(TextView)findViewById(R.id.tester);

        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homegallery.this,Main3Activity.class);
                startActivity(intent);
            }
        });

    }
    protected void onStart(){
        super.onStart();

        //Creating a condtion under root
        mcondtionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //fired when condtion value updates get data back as snapshot
                String text =dataSnapshot.getValue(String.class);
                tester.setText(text);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

}}
