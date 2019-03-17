package com.example.findevents;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShowEventDetailActivity extends AppCompatActivity {

    private ImageButton mDetailBackBtn;
    private TextView textViewTitle,textViewDescription,textViewDateCreated,textViewDateEvent,textViewLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_detail);
        //getSupportActionBar().hide();
        //setSupportActionBar(content_detail_list);


        //mDetailBackBtn=(ImageButton)findViewById(R.id.imageBtnBackDetail);
        textViewTitle=(TextView)findViewById(R.id.tvEventTitle);
        textViewDescription=(TextView)findViewById(R.id.tvEventDescription);
        textViewDateCreated=(TextView)findViewById(R.id.tvDate_Created);
        textViewDateEvent=(TextView)findViewById(R.id.tvDate_Event);
        textViewLocation=(TextView)findViewById(R.id.tvEventLocation);

        Intent myintent=getIntent();
        final String stitle=myintent.getExtras().getString("ptitle");
        final String sdescription=myintent.getExtras().getString("pdescription");
        final String sDateCreated=myintent.getExtras().getString("pcreated_at");
        final String sDateEvent=myintent.getExtras().getString("pdateEvent");
        final String sLocation=myintent.getExtras().getString("plocation");

        textViewTitle.setText(stitle);
        textViewDescription.setText("Description: "+sdescription);
        textViewDateCreated.setText("Date de Création: "+sDateCreated);
        textViewDateEvent.setText("Date de l'évènement: "+sDateEvent);
        textViewLocation.setText("Lieu: "+sLocation);


        /*
         final String name=i.getExtras().getString("Name");
        final String pos=i.getExtras().getString("Position");
        final int image=i.getExtras().getInt("Image");
         */
        //bouton pour Supprimer
        FloatingActionButton fab_participate = (FloatingActionButton) findViewById(R.id.fabDelete);
        fab_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Supprimer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Bouton pour Commenter
        FloatingActionButton fab_comment = (FloatingActionButton) findViewById(R.id.fabCommente);
        fab_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Commenter", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Bouton pour Modifier
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabEdite);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modifier", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public void detailBackonClick(View view){
        ShowEventDetailActivity.this.finish();

    }
}
