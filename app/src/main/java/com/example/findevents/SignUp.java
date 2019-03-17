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



public class SignUp extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText pseudo_enregistrement = (EditText) findViewById(R.id.pseudo);
        //final EditText email_enregistrement = (EditText) findViewById(R.id.email);
        final EditText pass_enregistrement = (EditText) findViewById(R.id.password);
        //final EditText pass2_enregistrement = (EditText) findViewById(R.id.passwordConfirmation);


        Button lien_register = findViewById(R.id.sign_up_button);

        lien_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputOk = true;

                //récuperation des champs de text
                final String pseudo = pseudo_enregistrement.getText().toString();
                //final String mail = email_enregistrement.getText().toString();
                final String password = pass_enregistrement.getText().toString();
                //final String password2 = pass2_enregistrement.getText().toString();

                //vérification des user input
                if (pseudo.equals("")) {
                    Toast.makeText(context, "Vous devez entrer un nom", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }  else if (password.equals("")) {
                    Toast.makeText(context, "Vous devez entrer un mot de passe", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }  else if (password.length() < 6) {
                    Toast.makeText(context, "Votre mot de passe doit dépasser 6 charactères", Toast.LENGTH_SHORT).show();
                    isInputOk = false;
                }

                if (isInputOk) {
                    //création de la requete
                    String url = "http://fullstackter.alwaysdata.net/api/register";

                    RequestQueue queue = Volley.newRequestQueue(context);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("reponse inscription", response);
                                    try {
                                        JSONObject reponse = new JSONObject(response);
                                        if (reponse.has("token")) {
                                            Log.d("reponse inscription", response);
                                            //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(context, "inscription bien prise en compte, vous pouvez maintenant vous connecter", Toast.LENGTH_SHORT).show();
                                            Intent LoginActivity = new Intent(context, LoginActivity.class);
                                            context.startActivity(LoginActivity);
                                        } else {
                                            Toast.makeText(context, "pseudo déja pris", Toast.LENGTH_SHORT).show();
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
                            params.put("pseudo", pseudo);
                            params.put("password", password);
                            return params;
                        }

                    };
                    queue.add(postRequest);
                }
            }
        });
    }
}



