package com.example.findevents;


import android.app.LauncherActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import beans.Events;

public class JSONParser extends AsyncTask<Void,Void,Void> {

    String data ="";


    private List<Events> al=new ArrayList<>();
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.myjson.com/bins/zng4y");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            for(int i =0 ;i <JA.length(); i++){
                System.out.print("Longueur ==============================================="+JA.length());
                //Log.e("Logueur ====================================","");
                Log.e("Taille de JSON==="+JA.length(),"longueur!!!!!");



                JSONObject JO= JA.getJSONObject(i);
                Events eventss=new Events(
                        JO.getInt("id"),
                        JO.getInt("creator"),
                        JO.getString("title"),
                        JO.getString("description"),
                        JO.getString("date_creation"),
                        JO.getString("date_event"),
                        JO.getInt("location")
                );
                Log.e("Taille DE LIST==="+al.size(),"longueur!!!!!");
                al.add(eventss);
                Log.e("Taille DE LIST==="+al.size(),"longueur!!!!!");





            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        Iterator itr=al.iterator();
        while(itr.hasNext()){
            Events st=(Events)itr.next();


            CardModel cards= new CardModel(R.drawable.event_icon, st.getTitle(), st.getDescritption());
            ShowEventsListActivity.adapter.addAll(cards);

        }

    }

}
