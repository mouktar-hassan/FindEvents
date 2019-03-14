package com.example.findevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EventActivity extends AppCompatActivity {

    Context context = this;

    // UI references.
    private AutoCompleteTextView mTitle;
    private EditText mDescription,mDateCreation,mDateEvent,mAdresse;
    private View mProgressView,mLoginFormView;
    private Button bAddEvent;
    private ImageButton imageButtonBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Set up the AddEvent form.
        mTitle=(AutoCompleteTextView)findViewById(R.id.eventTitle);
        mDescription=(EditText)findViewById(R.id.eventDescription);
        mDateEvent=(EditText)findViewById(R.id.eventDateEvent);
        mAdresse=(EditText)findViewById(R.id.eventAdresse);
        //mAdresse.setOnClickListener(adresseListener);
        bAddEvent=(Button)findViewById(R.id.addEventbutton);
        imageButtonBack=(ImageButton)findViewById(R.id.imageBtnBackEvent);

        bAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputOk = true;

                //récuperation des champs de text explicite( visibles dans la formulaire d'ajoute d'évènement)
                final String title =mTitle.getText().toString();
                final String description=mDescription.getText().toString();
                final String DateEvent=mDateEvent.getText().toString();
                final String Adresse=mAdresse.getText().toString();
                //récuperation des champs de text implicite( invisibles dans la formulaire d'ajoute d'évènement)
                final String pcreator="14";
                final String pLongitude="1";
                final String pLatitude="1";
                final String pDistance="8";


                //vérification des user input
                if (title.equals("")) {
                    Toast.makeText(context, "Vous devez entrer un Titre!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }  else if (description.equals("")) {
                    Toast.makeText(context, "Vous devez entrer une Description!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }  else if (description.length() < 10) {
                    Toast.makeText(context, "Veuillez écrire une description pertinent!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }else if (DateEvent.equals("")){
                    Toast.makeText(context, "Vous devez entrer une Date d'Évènement!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }
                else if(Adresse.equals("")){
                    Toast.makeText(context, "Vous devez entrer une Adresse exacte!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }

                if (isInputOk) {
                    //création de la requete
                    String url = "http://fullstackter.alwaysdata.net/api/events?longitude=1&latitude=1&radius=1000";

                    RequestQueue queue = Volley.newRequestQueue(context);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("reponse d'ajoute", response);
                                    try {
                                        JSONObject reponse = new JSONObject(response);
                                        if (reponse.has("token")) {
                                            Log.d("reponse d'ajoute", response);
                                            //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(context, "Ajoute de l'évènement avec succès!!!", Toast.LENGTH_SHORT).show();

                                            Intent iListEvents = new Intent(context, ShowEventsListActivity.class);
                                            context.startActivity(iListEvents);
                                        } else {
                                            Toast.makeText(context, "Ajoute de l'Évènement avec succès", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Problème de connexion", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {


                            HashMap<String, String> headers = new HashMap<>();

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            String token_type = preferences.getString("token_type", "none");
                            String access_token = preferences.getString("access_token", "none");

                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            headers.put("Authorization", token_type + " " + access_token);
                            //headers.put("Authorization", "Bearer" + " " + access_token);
                            return headers;

                        }

                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("creator", pcreator);
                            params.put("title", title);
                            params.put("description", description);
                            params.put("date_event", DateEvent);
                            params.put("location_name", Adresse);
                            params.put("latitude", pLatitude);
                            params.put("longitude", pLongitude);
                            params.put("distance", pDistance);
                            return params;
                        }

                    };
                    queue.add(postRequest);
                }
            }
        });




    }

    public void eventBackonClick(View view){
        EventActivity.this.finish();
    }
}
