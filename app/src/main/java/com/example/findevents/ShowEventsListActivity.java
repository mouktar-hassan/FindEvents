package com.example.findevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ShowEventsListActivity extends AppCompatActivity {

    ListView lvCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events_list);

        lvCards = (ListView) findViewById(R.id.list_cards);
        CardsAdapter adapter = new CardsAdapter(this);

        lvCards.setAdapter(adapter);
        adapter.addAll(new CardModel(R.drawable.event_icon, R.string.cardModelTitle, R.string.cardModelAdresse),
                new CardModel(R.drawable.event_icon, R.string.cardModelTitle, R.string.cardModelAdresse),
                new CardModel(R.drawable.event_icon, R.string.cardModelTitle, R.string.cardModelAdresse),
                new CardModel(R.drawable.event_icon, R.string.cardModelTitle, R.string.cardModelAdresse));


        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                Object  itemValue    = (Object) lvCards.getItemAtPosition(position);

                Intent iDetail=new Intent(ShowEventsListActivity.this,ShowEventDetailActivity.class);
                startActivity(iDetail);

                //Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();


            }
        });
    }
}





