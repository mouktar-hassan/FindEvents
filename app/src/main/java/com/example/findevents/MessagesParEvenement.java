package com.example.findevents;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import beans.Events;

public class MessagesParEvenement extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterMessagesEvent adapter;

    private ArrayList<ModelMessagesEvent> listMessagesEvents;

    private String URL_DATA = "http://fullstackter.alwaysdata.net/api/messages?event=";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_par_evenement);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listMessagesEvents = new ArrayList<>();

        int id = getIdEvent();

        url = URL_DATA+id;

        loadEvents();

        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
    }

    private int getIdEvent(){
        if (getIntent().hasExtra("id")){
            Log.d("message", "getIncomingIntent: Id Event trouvé.");
            int idEvent = getIntent().getIntExtra("id",0);
            return (idEvent);
        }
        return (1234);
    }

    private void loadEvents () {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("recherche commentaires en cours");
        progressDialog.show();

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        progressDialog.dismiss();

                        try{
                            // Loop through the array elements
                            for ( int i = 0; i<array.length(); i++) {

                                // Get current json object
                                JSONObject jsonObject = array.getJSONObject(i);

                                Log.d("response", String.valueOf(jsonObject));

                                ModelMessagesEvent message = new ModelMessagesEvent(
                                        jsonObject.getInt("id"),
                                        jsonObject.getInt("user"),
                                        jsonObject.getInt("event"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("pseudo"),
                                        jsonObject.getString("message"),
                                        jsonObject.getString("created_at")
                                );

                                listMessagesEvents.add(message);
                            }
                            adapter = new AdapterMessagesEvent(listMessagesEvents,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
