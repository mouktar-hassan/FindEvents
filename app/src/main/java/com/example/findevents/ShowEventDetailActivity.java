package com.example.findevents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.Events;
import beans.Guests;
import beans.User;

public class ShowEventDetailActivity extends AppCompatActivity {

    //UI Design
    private ImageButton mDetailBackBtn;
    private TextView textViewTitle,textViewDescription,textViewDateCreated,textViewDateEvent,textViewLocation;
    //TextView textComment;
    private String Messageurl, EventUrl,GuestsUrl,UserUrl;
    EditText subEditText;
    private Integer sEventId,sEventUser,ConnectedUserId=0;
    private List<User> listUsers;
    private Map<Integer,String> userparams;
    private Map<Integer,Integer> guestparams;
    boolean isParticipated;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_detail);
        //getSupportActionBar().hide();
        //setSupportActionBar(content_detail_list);
        Messageurl = "http://fullstackter.alwaysdata.net/api/messages";
        EventUrl="http://fullstackter.alwaysdata.net/api/events/";
        GuestsUrl="http://fullstackter.alwaysdata.net/api/guests";
        UserUrl="http://fullstackter.alwaysdata.net/api/users";


        //mDetailBackBtn=(ImageButton)findViewById(R.id.imageBtnBackDetail);
        textViewTitle=(TextView)findViewById(R.id.tvEventTitle);
        textViewDescription=(TextView)findViewById(R.id.tvEventDescription);
        textViewDateCreated=(TextView)findViewById(R.id.tvDate_Created);
        textViewDateEvent=(TextView)findViewById(R.id.tvDate_Event);
        textViewLocation=(TextView)findViewById(R.id.tvEventLocation);

        listUsers=new ArrayList<>();
        //au débart c'est false
        //isParticipated=false;
        //pour récuperer l'info de l'utilisateur connecté
        getUserData();

        getGuestData();

        //getGuestData();




        //textComment=(TextView) findViewById(R.id.edt_comment) ;

        //On récupere les valeurs dans la liste de l'évènements cliqué

        Intent myintent=getIntent();
        final String stitle=myintent.getExtras().getString("ptitle");
        final String sdescription=myintent.getExtras().getString("pdescription");
        final String sDateCreated=myintent.getExtras().getString("pcreated_at");
        final String sDateEvent=myintent.getExtras().getString("pdateEvent");
        final String sLocation=myintent.getExtras().getString("plocation");
        sEventId=myintent.getExtras().getInt("pid");
        sEventUser=myintent.getExtras().getInt("pcreator");


        //On affiche le détail de l'évènement cliqué
        textViewTitle.setText(stitle);
        textViewDescription.setText("Description: "+sdescription);
        textViewDateCreated.setText("Date de Création: "+sDateCreated);
        textViewDateEvent.setText("Date de l'évènement: "+sDateEvent);
        textViewLocation.setText("Lieu: "+sLocation);



        //Les Boutons(Modifier, Commenter et SUpprimer)
        //bouton pour Supprimer
        FloatingActionButton fab_participate = (FloatingActionButton) findViewById(R.id.fabDelete);
        fab_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(ShowEventDetailActivity.this).create();
                alertDialog.setTitle("Voulez-vous supprimer ce évènement?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.dismiss();
                                deleteEvent (EventUrl+sEventId);
                            }
                        });
                alertDialog.show();
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
                //Pour lancer le dialogue
                openDialog();



            }
        });

        //Bouton pour Modifier
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabEdite);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modifier", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //participateEvent(GuestsUrl);
               // getGuestData();
                if (isParticipated=false){
                    participateEvent(GuestsUrl);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Désolé, vous avez déjà participé ce évènement!!!", Toast.LENGTH_LONG).show();

                }





            }
        });


    }

    //Pour lancer le dialogue d'ajoute de commentaire
    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(ShowEventDetailActivity.this);
        View subView = inflater.inflate(R.layout.activity_custom_dialog, null);
         subEditText = (EditText)subView.findViewById(R.id.edt_comment);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Commentaire");
        builder.setMessage("Veuillez taper votre commentaire!");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();


        //Apres le clique du bouton Envoyer
        builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String message=subEditText.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Merci de saisir votre commentaire!", Toast.LENGTH_LONG).show();
                } else {
                    ajouterCommentaire(Messageurl);
                }

            }
        });

        ////Apres le clique du bouton Annuler
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ShowEventDetailActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    private void ajouterCommentaire (String mess){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mess,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Votre commentaire a été envoyé!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ShowEventDetailActivity.this, ShowEventsListActivity.class));
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
                params.put("message", subEditText.getText().toString());
                //params.put("event","9");
                params.put("user", sEventUser.toString());
                params.put("event",sEventId.toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void deleteEvent(String deleteURL){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, deleteURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Erreur de reponse  ", "Erreur1 \n" + error.toString());

                    }
                }
        );
        requestQueue.add(stringRequest);

    }





    public void detailBackonClick(View view){
        ShowEventDetailActivity.this.finish();

    }



    //Pour participer un évènement
    private void participateEvent (String mess){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mess,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Votre participation a été validée avec succès!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ShowEventDetailActivity.this, ShowEventsListActivity.class));
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
                params.put("user",ConnectedUserId.toString());
                params.put("event", sEventId.toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
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

    //pour récuper l'information des invités en vérifiant si l'utilisation a participé l' évènement en question ou pas
    private void getGuestData() {



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(GuestsUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        Guests guest=new Guests();

                        guest.setG_id(jsonObject.getInt("id"));
                        guest.setG_user(jsonObject.getInt("user"));
                        guest.setG_event(jsonObject.getInt("event"));

                        guestparams=new HashMap<>();
                        guestparams.put(guest.getG_user(),guest.getG_event());

                        if (guest.getG_user()==ConnectedUserId && guest.getG_event()==sEventId ) {
                            isParticipated=true;
                            //Toast.makeText(getApplicationContext(), "Désolé, vous avez déjà participé ce évènement!", Toast.LENGTH_LONG).show();
                        }
                        else {

                            isParticipated=true;

                            //participateEvent(GuestsUrl);
                            //isParticipated=true;
                            //Toast.makeText(getApplicationContext(), "Pas pas encore!", Toast.LENGTH_LONG).show();
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
