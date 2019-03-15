package com.example.findevents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commentaire extends AppCompatActivity {

    private List<CommentaireItem> listCommentaire;
    private ListView recyclerView;
    private EditText saisirCmt;
    private Button envoyer;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentaire);
        url = "http://fullstackter.alwaysdata.net/api/messages";
        recyclerView = findViewById(R.id.recyclerViewCommentaire);
        listCommentaire = new ArrayList<>();
        envoyer = (Button) findViewById(R.id.buttonInserer);
        saisirCmt = (EditText) findViewById(R.id.saisirCmt);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new lireJSON().execute(url);
            }
        });

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = saisirCmt.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Erreur2", Toast.LENGTH_LONG).show();
                } else {
                    ajouterCommentaire(url);
                }
            }
        });
    }
    private void ajouterCommentaire (String mess){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mess,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "envoyé", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Commentaire.this, MainActivity.class));
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
                params.put("message", saisirCmt.getText().toString().trim());
                //params.put("event","9");
                params.put("user", "7");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    class lireJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            try {
                JSONArray listJson = new JSONArray(s);
                for (int i = 0; i < listJson.length(); i++) {
                    JSONObject cmt = listJson.getJSONObject(i);
                    listCommentaire.add(new CommentaireItem(
                            cmt.getInt("user"),
                            cmt.getInt("event"),
                            cmt.getInt("id"),
                            cmt.getString("created_at"),
                            cmt.getString("message")
                    ));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListAdapter adapter = new ListAdapter(
                    getApplicationContext(),
                    R.layout.activity_list_commentaire,
                    listCommentaire
            );
            recyclerView.setAdapter(adapter);
            //Toast.makeText(getApplicationContext(),"fdssdf "+listCommentaire.size(),Toast.LENGTH_LONG).show();
        }

    }

    private String docNoiDung_Tu_URL (String theUrl){
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
