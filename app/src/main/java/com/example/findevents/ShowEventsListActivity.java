package com.example.findevents;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import beans.CardsAdapter;
import beans.Events;

public class ShowEventsListActivity extends AppCompatActivity {

    private String url = "http://fullstackter.alwaysdata.net/api/events";

    private List<Events> eventsList;

    private CardsAdapter adapter;

    private DividerItemDecoration dividerItemDecoration;

    private LinearLayoutManager linearLayoutManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        //REFERENCE RECYCLER
        RecyclerView rv= (RecyclerView) findViewById(R.id.myRecycler);

        //SET PROPERTIES

        rv.setItemAnimator(new DefaultItemAnimator());
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());

        dividerItemDecoration = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());

        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(dividerItemDecoration);

        eventsList = new ArrayList<>();

        //ADAPTER
         adapter=new CardsAdapter(getApplicationContext(),eventsList);
        rv.setAdapter(adapter);

        getData();







    }


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                        Events event = new Events();
                        event.setId(jsonObject.getInt("id"));
                        event.setCreator(jsonObject.getInt("creator"));
                        event.setTitle(jsonObject.getString("title"));
                        event.setDescritption(jsonObject.getString("description"));
                        event.setDate_creation(jsonObject.getString("created_at"));
                        event.setDate_updated(jsonObject.getString("updated_at"));
                        event.setDate_event(jsonObject.getString("date_event"));
                        event.setLocation(jsonObject.getString("location_name"));
                        event.setLatitude(jsonObject.getInt("latitude"));
                        event.setLongitude(jsonObject.getInt("longitude"));



                        eventsList.add(event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast toast = Toast.makeText(getApplicationContext(), "Erreur de chargement", Toast.LENGTH_SHORT);
                toast.show();
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



}





