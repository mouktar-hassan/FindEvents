package com.example.findevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EventActivity extends AppCompatActivity {

    private EditText titleEvent;
    private EditText description;
    private EditText dateCreation;
    private EditText dateEvent;
    private ImageButton position;
    private Button ajouter;
    private Button annuler;
    private Button events;//voir tous les evenements
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleEvent= findViewById(R.id.editText);
        description = findViewById(R.id.editText2);
        dateCreation = findViewById(R.id.editText3);
        dateEvent = findViewById(R.id.editText4);
        position = findViewById(R.id.imageButton);
        ajouter = findViewById(R.id.button);
        annuler = findViewById(R.id.button2);
        events = findViewById(R.id.button3);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent allEvent =new Intent(EventActivity.this, MapsActivity.class);
                startActivity(allEvent);

            }
        });

    }

}
