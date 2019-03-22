package com.example.findevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String URL_CURRENT_USER = "http://fullstackter.alwaysdata.net/api/user";
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent addEvent =new Intent(MainActivity.this, EventActivity.class);
                startActivity(addEvent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getGetCurrentUser();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //recuperation des preferences pour savoir si on est connecté
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String valeur_token = preferences.getString("valeur_token", "none");

        if (id == R.id.nav_login) {
            if (valeur_token.equals("none")) {
                //si l'on est pas connecté
                // Handle the camera action
                Intent login =new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
            else {
                Toast.makeText(getApplicationContext(), "Vous etes déja connécté", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_gallery) {
            if (valeur_token.equals("none")) {
                //si l'on est pas connecté
                Intent signup =new Intent(MainActivity.this, SignUp.class);
                startActivity(signup);
            }
            else {
                Toast.makeText(getApplicationContext(), "Vous etes déja connécté", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_slideshow) {
            Intent showall =new Intent(MainActivity.this, ShowEventsListActivity.class);
            startActivity(showall);

        }
        else if (id==R.id.nav_comments){
            if (valeur_token.equals("none")) {
                //si l'on est pas connecté
                Toast.makeText(getApplicationContext(), "Vous devez vous connecter pour consulter les messages", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent imessage=new Intent(MainActivity.this,MessageActivity.class);
                startActivity(imessage);
            }
        }

        else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onClick(View view) {
    }

    public void seeEvents(View view) {
        Intent ievents=new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(ievents);
    }



    public String getAccessToken (){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String leTokenEst = preferences.getString("valeur_token", "none");
        return leTokenEst;
    }

    private void getGetCurrentUser() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CURRENT_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("reponse", response);


                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("user");

                            Log.d("response", String.valueOf(jsonObject2));

                            int idCurrentUser ;
                            String pseudoCurrentUser;

                            idCurrentUser = jsonObject2.getInt("id");
                            pseudoCurrentUser = jsonObject2.getString("pseudo");

                            SharedPreferences preferencesCurent = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editorCurent = preferencesCurent.edit();
                            editorCurent.putString("idCurrentUser", String.valueOf(idCurrentUser));
                            editorCurent.commit();

                            Log.d("valeurs", pseudoCurrentUser + " " + idCurrentUser);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error recup", error.getMessage());
                    }
                })
        {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                HashMap<String,String> headers = new HashMap<>();

                //headers.put("Authorization", token_type + " " + access_token);
                //String x ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjAxYjQ3Y2E4YmNiOWQ1YzNkNDJlOGY0MjcxNDBiYTk3YTcwZTRlMjljYjNlZjZkZWRjMDM4NDc3MmI2NjVkZWQwMzIzNDk4ZTYxOGFiNmIwIn0.eyJhdWQiOiIxIiwianRpIjoiMDFiNDdjYThiY2I5ZDVjM2Q0MmU4ZjQyNzE0MGJhOTdhNzBlNGUyOWNiM2VmNmRlZGMwMzg0NzcyYjY2NWRlZDAzMjM0OThlNjE4YWI2YjAiLCJpYXQiOjE1NTMxMDE3NzQsIm5iZiI6MTU1MzEwMTc3NCwiZXhwIjoxNTg0NzI0MTc0LCJzdWIiOiI3Iiwic2NvcGVzIjpbXX0.EizSefhUAt3-Omn9_SZzmI8yt3lY7JjxO74FufT3bNlgfX8pPB3wlwMDncQm4LcT77upAYHLOsrvbT1OZpyboTe_jBGwY7ocy97PIODB7V3Iu64VQBMHxtB_zwvtXBMLlAomMtHR5XXmWs7Cv-rGB6WKHGilEhxLgxk3gKURKVbm65RvNOX1ty4JJu7QhMjcv5B_a_TXQJ30KU9UWXU0nCZorR6NADm24-ARuU80jrgHmXlnIRq3-lAwYww7s7jM32gkWTy4sBP0CN-NrBJMXsMWmdwIlmTpKIaG1iQ6Fe2vDzlBuP1wYQtBiAsz2Cje2F2zltsVOWNJThAvce2Nag8-0gjPhO8YAIUymFbIdz6GivLRSl9jb3kGYWI6y_8uSPtnxSo2HuKwwWU-vPvnI774TRaMIlmOtakFq8888WMJXn0n03JSW14gfNiVa1uX58qpc6S3EUrM8Xyk-11TlAns06y14_QEVIaPQrWI5jhgsTz6SDUIh-KdKSFhB2NK7GlU-hmDQ-u9LSvzu9BOCW91zS8-yiz4g95VfJEzEbwZXTL1lN6JsPscnXnjdhn_U4PTYD4i3QGu0qblNnGuUVvaaTWgPIniqhjBOS6ohz-eDzUAP5zNdqnchhSwSAqFImI6p6_fhMjLVhTOv9kT5WuvcurJWGfvqHdchP-uS8o";
                String ajouterTokeN = getAccessToken();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer" + " " + ajouterTokeN);
                return headers;
            }
        };
        queue.add(stringRequest);
    }
}