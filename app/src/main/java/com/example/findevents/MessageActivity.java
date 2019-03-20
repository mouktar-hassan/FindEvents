package com.example.findevents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.CardsAdapter;
import beans.CardsAdapterMessage;
import beans.Events;
import beans.Message;
import beans.User;

public class MessageActivity extends AppCompatActivity {

    private String MessageUrl = "http://fullstackter.alwaysdata.net/api/messages";
    private String UserUrl="http://fullstackter.alwaysdata.net/api/users";



    private CardsAdapterMessage adapterMessage;

    private DividerItemDecoration dividerItemDecoration;

    private LinearLayoutManager linearLayoutManager;
    private List<Message> messageList;
    private Map<Integer,String> userparams;
    private String userPseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmessage);
        //setSupportActionBar(toolbar);



        //REFERENCE RECYCLER
        RecyclerView rv= (RecyclerView) findViewById(R.id.myRecycler_message);

        //SET PROPERTIES

        rv.setItemAnimator(new DefaultItemAnimator());
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());

        dividerItemDecoration = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());

        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(dividerItemDecoration);



        messageList=new ArrayList<>();

        getMessageData();

        //ADAPTER
        adapterMessage=new CardsAdapterMessage(getApplicationContext(),messageList);
        rv.setAdapter(adapterMessage);
    }


    private void getMessageData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MessageUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                        Message message=new Message();

                        message.setM_id(jsonObject.getInt("id"));
                        message.setM_user(jsonObject.getInt("user"));
                        message.setM_event(jsonObject.getInt("event"));
                        message.setM_message(jsonObject.getString("message"));

                        messageList.add(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapterMessage.notifyDataSetChanged();
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
                        String value = sharedpreferences.getString("id", "");
                        Log.d("mes preferences infos", value);
                        if(user.getU_id()==Integer.valueOf(value)){
                            //on récupere l'id de l'utilisateur connecté
                            userPseudo=user.getU_pseudo();

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


}
