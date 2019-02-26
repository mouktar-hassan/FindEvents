package com.example.findevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


public class EventActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mTitle;
    private EditText mDescription;
    private EditText mDateCreation;
    private EditText mDateEvent;
    private EditText mAdresse;
    private View mProgressView;
    private View mLoginFormView;
    private Button bAddEvent;

    private View.OnClickListener adresseListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent iAdresse = new Intent(EventActivity.this, AdresseActivity.class);
            startActivity(iAdresse);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Set up the AddEvent form.
        mTitle=(AutoCompleteTextView)findViewById(R.id.eventTitle);
        mDescription=(EditText)findViewById(R.id.eventDescription);
        mDateCreation=(EditText)findViewById(R.id.eventDateCreation);
        mDateEvent=(EditText)findViewById(R.id.eventDateEvent);
        mAdresse=(EditText)findViewById(R.id.eventAdresse);
        mAdresse.setOnClickListener(adresseListener);
        bAddEvent=(Button)findViewById(R.id.addEventbutton);




    }
}
