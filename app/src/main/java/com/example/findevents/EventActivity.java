package com.example.findevents;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Handler;
import android.os.Message;
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


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Map;
import java.util.regex.Pattern;

import beans.GeocodingLocation;



public class EventActivity extends AppCompatActivity {

    private Context context = this;

    // UI references.
    private AutoCompleteTextView mTitle;
    private EditText mDescription,mDateEvent,mAdresse,mLatitude,mLongtitude;
    private View mProgressView,mLoginFormView;
    private Button bAddEvent,bGetLatLng;
    private ImageButton imageButtonBack;
    private String UserUrl;
    private Integer ConnectedUserId=0;
    private Map<Integer,String> userparams;
    private String EventUrl;
    private int annee,mois,jour,heur,minute;
    private SimpleDateFormat mSimpleDateFormat;
    private DateFormat df;
    private Calendar cal;
    //Pour valider le double Lat Lng





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
        bGetLatLng=(Button)findViewById(R.id.getLatLng);
        imageButtonBack=(ImageButton)findViewById(R.id.imageBtnBackEvent);
        mLatitude=(EditText) findViewById(R.id.eventLatitude);
        mLongtitude=(EditText)findViewById(R.id.eventLongitude);
        //pour rendre les champs non editable
        mLatitude.setEnabled(false);
        mLongtitude.setEnabled(false);


        EventUrl = "http://fullstackter.alwaysdata.net/api/events?longitude=44&latitude=44&radius=100000";

        UserUrl="http://fullstackter.alwaysdata.net/api/users";


        //Bouton pour récuperer la latitude et la longitude de l'adresse saisie
        bGetLatLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String address = mAdresse.getText().toString();
                if (address.equals("")){
                    Toast.makeText(context, "Vous devez entrer une Adresse exacte!", Toast.LENGTH_SHORT).show();
                }
                else {
                    GeocodingLocation locationAddress = new GeocodingLocation();
                    locationAddress.getAddressFromLocation(address,
                            getApplicationContext(), new GeocoderHandler());
                }


            }
        });



        bAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputOk = true;

                final String DOUBLE_PATTERN = "[0-9]{0,1}[0-9]*";

                //récuperation des champs de text explicite( visibles dans la formulaire d'ajoute d'évènement)
                final String title =mTitle.getText().toString();
                final String description=mDescription.getText().toString();
                final String DateEvent=mDateEvent.getText().toString();
                final String Adresse=mAdresse.getText().toString();
                final String Latitude=mLatitude.getText().toString();
                final String Longtitude=mLongtitude.getText().toString();
                //final String Latitude=Double.valueOf(mLatitude.getText().toString());
                //final String Longtitude=Double.valueOf(mLongtitude.getText().toString());

                int createurDeLevent = getIdUtilisateurConnecte();
                final String pcreator=String.valueOf(createurDeLevent);

                Boolean dateexacte = testDate(DateEvent);

                String testLatitude1=mLatitude.getText().toString();
                String testLongitude2=mLongtitude.getText().toString();
                boolean testLatitude=isok(testLatitude1);
                boolean testLongitude=isok(testLongitude2);

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
                else if (dateexacte==false){
                    Toast.makeText(context, "Vous devez entrer une Date d'Évènement de la forme YYYY-MM-DD-HH-MM!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }
                else if(Adresse.equals("")){
                    Toast.makeText(context, "Vous devez entrer une Adresse exacte!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }
                else if (Latitude.isEmpty() && !Pattern.matches(DOUBLE_PATTERN,Latitude)){
                    Toast.makeText(context, "Vous devez entrer une Latitude et avec un type double!", Toast.LENGTH_SHORT).show();


                    isInputOk = false;

                }
                else if (Longtitude.isEmpty() && !Pattern.matches(DOUBLE_PATTERN,Longtitude)){
                    Toast.makeText(context, "Vous devez entrer une Longitude et avec un type double!", Toast.LENGTH_SHORT).show();
                    if (Pattern.matches(DOUBLE_PATTERN,Latitude)){
                        isInputOk = false;
                    }
                }



                /*else if(testLatitude==false){
                    Toast.makeText(context, "Vous devez entrer une LATITUDE exacte!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }
                else if(testLongitude==false){
                    Toast.makeText(context, "Vous devez entrer une LONGITUDE exacte!", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }*/


                if (isInputOk) {
                    //création de la requete

                    RequestQueue queue = Volley.newRequestQueue(context);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, EventUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("reponse d'ajoute", response);
                                    Toast.makeText(context, "Ajout de l'Évènement avec succès", Toast.LENGTH_SHORT).show();
                                    Intent iListEvents = new Intent(context, ShowEventsListActivity.class);
                                    startActivity(iListEvents);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Problème de connexion, Veuillez vous assurez que vous avez bien remplie toutes las champs de la formulaire", Toast.LENGTH_LONG).show();
                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {

                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            return headers;

                        }

                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("creator", pcreator);
                            params.put("title", title);
                            params.put("description", description);
                            params.put("date_event", DateEvent);
                            params.put("location_name", Adresse);
                            params.put("latitude", Latitude);
                            params.put("longitude", Longtitude);
                            return params;
                        }

                    };
                    queue.add(postRequest);
                }
            }
        });


    }

    //récuperer l'id de l'utilisateur connecté
    public int getIdUtilisateurConnecte (){
        SharedPreferences preferencesCurent = PreferenceManager.getDefaultSharedPreferences(this);
        String LYDIY = preferencesCurent.getString("idCurrentUser", "none");
        int valeur = Integer.parseInt(LYDIY);
        return valeur;
    }


    //Pour valider le format du Date , l'heur et la minute
    private Boolean testDate(String date){
        String datePattern = "\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{2}";
        Boolean isCorrectDate = date.matches(datePattern);
        return isCorrectDate;
    }




    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isdouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isok(String str) {
        boolean x = isInteger (str);
        boolean z = isdouble (str);

        if (x==true||z==true){
            return true;
        }
        else return false;
    }


    //méthode pour fermer l'activité en cours d'execution

    public void eventBackonClick(View view){
        EventActivity.this.finish();
    }


    //Classe pour la manupilation du géocodage
     class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            String result;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("addressLatLng");
                    if (result.equals("")){
                        Toast.makeText(context, "Vous devez entrer une Adresse exacte!", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.equals("Impossible")){
                        Toast.makeText(context, "L'adresses saisie est incorrect. Veuillez saisir une adresse correct!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        final String SEPARATEUR = ",";
                        //pour découper la résultat en Lat et en Lng
                        String motsLatLng[] = result.split(SEPARATEUR);
                        mLatitude.setText(motsLatLng[0]);
                        mLongtitude.setText(motsLatLng[1]);
                    }

                    break;
                default:
                    result = null;

            }


        }
    }



}


