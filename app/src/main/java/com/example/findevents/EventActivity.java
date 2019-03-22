package com.example.findevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.User;


public class EventActivity extends AppCompatActivity {

    Context context = this;

    // UI references.
    private AutoCompleteTextView mTitle;
    private EditText mDescription,mDateEvent,mAdresse,mLatitude,mLongtitude;
    private View mProgressView,mLoginFormView;
    private Button bAddEvent;
    private ImageButton imageButtonBack;
    private String UserUrl;
    private Integer ConnectedUserId=0;
    private Map<Integer,String> userparams;
    private String EventUrl;



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
        mLatitude=(EditText) findViewById(R.id.eventLatitude);
        mLongtitude=(EditText)findViewById(R.id.eventLatitude);


        EventUrl = "http://fullstackter.alwaysdata.net/api/events";

        UserUrl="http://fullstackter.alwaysdata.net/api/users";

        getUserData();

        bAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputOk = true;

                //récuperation des champs de text explicite( visibles dans la formulaire d'ajoute d'évènement)
                final String title =mTitle.getText().toString();
                final String description=mDescription.getText().toString();
                final String DateEvent=mDateEvent.getText().toString();
                final String Adresse=mAdresse.getText().toString();
                final double Latitude=Double.valueOf(mLatitude.getText().toString());
                final double Longtitude=Double.valueOf(mLongtitude.getText().toString());

                //récuperation des champs de text implicite( invisibles dans la formulaire d'ajoute d'évènement)
                final String pcreator=ConnectedUserId.toString();
                //final String pLongitude="43";
                //final String pLatitude="5";



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


                    RequestQueue queue = Volley.newRequestQueue(context);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, EventUrl,
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
                            params.put("latitude", Double.toString(Latitude));
                            params.put("longitude", Double.toString(Longtitude));
                            return params;
                        }

                    };
                    queue.add(postRequest);
                }
            }
        });




    }

    //Pour récuperer l'info de l'utilisateur connecté
    private void getUserData() {



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(UserUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        User user=new User();

                        user.setU_id(jsonObject.getInt("id"));
                        user.setU_pseudo(jsonObject.getString("pseudo"));
                        userparams=new HashMap<>();
                        userparams.put(user.getU_id(),user.getU_pseudo());
                        //listUsers.add(user);
                        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        // Reading from SharedPreferences
                        String value = sharedpreferences.getString("pseudo", "");
                        Log.d("mes preferences infos", value);
                        if(user.getU_pseudo().equals(value)){
                            //on récupere l'id de l'utilisateur connecté
                            ConnectedUserId=user.getU_id();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //progressDialog.dismiss();
                    }
                }

                //progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast toast = Toast.makeText(getApplicationContext(), "Erreur de chargement", Toast.LENGTH_SHORT);
                toast.show();
                //progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void eventBackonClick(View view){
        EventActivity.this.finish();
    }
}


    /*public void GetLocationFromAddress(String strAddress,int id, GoogleMap googleMap)
    {
        Geocoder coder = new Geocoder(this);

        List<Address> address = null;
        MarkerOptions markerOptions = new MarkerOptions();
        mar
        markerOptions.SetSnippet(strAddress);
        address = coder.GetFromLocationName(strAddress, 5);
        if (address == null)
        {
            return;
        }
        else
        {

            for (int i = 0; i < address.Count; i++)
            {
                Address ad = address[i];

                markerOptions.SetPosition(new LatLng(ad.Latitude, ad.Longitude));
                markerOptions.SetTitle(listArtisan[id]);

                googleMap.AddMarker(markerOptions);

            }
            googleMap.SetInfoWindowAdapter(this);


        }

    }*/
