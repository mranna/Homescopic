package com.example.hemanthanil.homescopic;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Main4Activity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE=1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_main4;
    FloatingActionButton fab;
    private FirebaseDatabase firebaseDatabase;


    TextView messageText, messageUser, messageTime;
    EditText input;




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main4,"You have signed in",Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                Snackbar.make(activity_main4,"Successfully signed in",Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            }
            else {
                Snackbar.make(activity_main4,"Try Again",Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        firebaseDatabase = FirebaseDatabase.getInstance();
        activity_main4 =(RelativeLayout) findViewById(R.id.activity_main4);
        input =(EditText)findViewById(R.id.input);
        fab = findViewById(R.id.fadid);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");


            }
        });



        //Check if not sign-in then navigate signin page
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else {

            Snackbar.make(activity_main4,"Welcome"+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            displayChatMessage();
        }

    }
    private void displayChatMessage(){

        ListView listOfMessage =(ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(Main4Activity.this, ChatMessage.class, R.layout.chat_list, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                //Get reference to the views of list items
                TextView messageText,messageUser,messageTime;

                messageUser=(TextView)v.findViewById(R.id.message_user);
                messageText=(TextView)v.findViewById(R.id.message_text);
                messageTime=(TextView)v.findViewById(R.id.message_time);


                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
            }
        };
//       listOfMessage.setAdapter(adapter);
//
//        adapter =new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.chat_list,FirebaseDatabase.getInstance().getReference()) {
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//
//                    TextView text = (TextView) v.findViewById(R.id.message_text);
//
//                    text.setText("hello");
//
//
//            }
//        };
        listOfMessage.setAdapter(adapter);
    }
}
