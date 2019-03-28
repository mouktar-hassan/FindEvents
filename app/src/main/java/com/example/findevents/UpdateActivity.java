package com.example.findevents;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    private Context context = this;

    private int id;
    private String title;
    private String description;
    private String dateCreation;
    private String dateEvent;
    private String location;
    private EditText textViewTitle,textViewDescription,textViewDateCreated,textViewDateEvent,textViewLocation;
    private Button modifier;
    private ImageButton retour;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent= this.getIntent();
        this.id=intent.getExtras().getInt("id");
        this.title=intent.getStringExtra("title");
        this.description=intent.getStringExtra("description");
        this.dateEvent=intent.getStringExtra("dateEvent");
        this.dateCreation=intent.getStringExtra("dateCreation");
        this.location=intent.getStringExtra("location");

        url="http://fullstackter.alwaysdata.net/api/events/"+id;

        textViewTitle=(EditText) findViewById(R.id.titleEvent);
        textViewDescription=(EditText) findViewById(R.id.description);
        //textViewDateCreated=(EditText) findViewById(R.id.dateCreation);
        textViewDateEvent=(EditText) findViewById(R.id.dateEvent);
        textViewLocation=(EditText) findViewById(R.id.location);
        textViewLocation.setEnabled(false);
        modifier=(Button) findViewById(R.id.modifier);
        retour =(ImageButton) findViewById(R.id.annuler);
        //validation de la date, l'heur et la minute


        textViewTitle.setText(title);
        textViewDescription.setText(description);
       // textViewDateCreated.setText(dateCreation);
        textViewDateEvent.setText(dateEvent);
        textViewLocation.setText(location);

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierEvent(url);
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void modifierEvent(String url){
        boolean isInputOk = true;
        Boolean dateexacte = testDate(textViewDateEvent.toString());
        /*if (dateexacte==false){
            Toast.makeText(context, "Vous devez entrer une Date d'Évènement de la forme YYYY-MM-DD-HH-MM!", Toast.LENGTH_SHORT).show();
            isInputOk = false;
        }*/

        //if (isInputOk==true)

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Modifié", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdateActivity.this, ShowEventsListActivity.class));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    Log.d("dqsdqsd ", "Erreur1 \n" + error.toString());
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("title", textViewTitle.getText().toString());
                    params.put("description", textViewDescription.getText().toString());
                    params.put("date_event",textViewDateEvent.getText().toString());
                    params.put("location_name",textViewLocation.getText().toString());

                    return params;
                }
            };
            requestQueue.add(stringRequest);



    }


    //Pour valider le format du Date , l'heur et la minute
    private Boolean testDate(String date){
        String datePattern = "\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{2}";
        Boolean isCorrectDate = date.matches(datePattern);
        return isCorrectDate;
    }

}