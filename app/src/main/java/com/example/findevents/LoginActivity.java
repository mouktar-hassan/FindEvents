package com.example.findevents;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    public static final String MyPREFERENCES = "MyPrefs" ;

    public Context context = this;
    String valeur_token;


    //EditText etpseudo;
    //EditText etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //recuperation des preferences pour savoir si on est connecté
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        valeur_token = preferences.getString("valeur_token", "none");

        if (valeur_token.equals("none")) {
            //si l'on est pas connecté

            setContentView(R.layout.activity_login);

            Log.d("preferences", "Il n'y a pas les preferences");
            //setContentView(R.layout.activity_login);

            //récuperation des view inportantes
            final EditText etpseudo = (EditText) findViewById(R.id.pseudo);
            //etpseudo = (EditText) findViewById(R.id.pseudo);
            final EditText etpassword = (EditText) findViewById(R.id.password);
            //etpassword = (EditText) findViewById(R.id.password);

            final TextView lien_enregistrement = (TextView) findViewById(R.id.textView_enregistrement);

            Button lien_login = findViewById(R.id.login);

            lien_login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    //récuperation des champs de text

                    final String pseudo = etpseudo.getText().toString();
                    final String password = etpassword.getText().toString();

                    //création de la requete
                    String url = "http://fullstackter.alwaysdata.net/api/login";

                    RequestQueue  queue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest  postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("reponse login", response);
                                    try {
                                        JSONObject resultat = new JSONObject(response);
                                        if (resultat.has("token")) {
                                            //Log.d("reponse login", response);
                                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                            valeur_token = resultat.getString("token");
                                            //Toast.makeText(getApplicationContext(), valeur_token, Toast.LENGTH_SHORT).show();
                                            Log.d("reponse login", valeur_token);


                                            //remplissage des preference avec les info user
                                            //SharedPreferences preferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("pseudo", pseudo);
                                            editor.putString("password", password);
                                            editor.putString("valeur_token", valeur_token);
                                            editor.commit();

                                            //Toast.makeText(context, "login bien prise en compte, vous pouvez maintenant vous connecter", Toast.LENGTH_SHORT).show();
                                            //changement d'activity une fois connecté
                                            Intent eventsActivity = new Intent(LoginActivity.this, MainActivity.class);
                                            //LoginActivity.this.startActivity(eventsActivity);
                                            startActivity(eventsActivity);
                                            Toast.makeText(getApplicationContext(), "Bienvenu cher(e) "+pseudo+"", Toast.LENGTH_SHORT).show();

                                            //Intent list = new Intent(context, ShowEventsListActivity.class);
                                            //context.startActivity(list);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "erreur identifiants", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Problème de connexion, Vérifiez vos identifiants", Toast.LENGTH_SHORT).show();

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("pseudo", "none");
                                    editor.putString("password", "none");
                                    editor.putString("valeur_token", "none");
                                    editor.commit();
                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {


                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            //headers.put("Authorization", token_type + " " + access_token);
                            headers.put("Authorization", "Bearer" + " " + valeur_token);
                            return headers;

                        }
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("pseudo", pseudo);
                            params.put("password", password);
                            return params;
                        }

                    };

                    queue.add(postRequest);

                } //fin onclick
            }); // fin lien login

            //click sur le lien d'enregistrement
            lien_enregistrement.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(LoginActivity.this, SignUp.class);
                    LoginActivity.this.startActivity(registerIntent);
                }
            });
        }// fin if

        else {
            //si l'on est connecté
            Log.d("preferences", valeur_token);
            Toast.makeText(this, "vous etes déja connecté.", Toast.LENGTH_LONG).show();

            Intent eventsActivity = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(eventsActivity);
            //setContentView(eventsActivity);
        }

    }// fin on create

}

