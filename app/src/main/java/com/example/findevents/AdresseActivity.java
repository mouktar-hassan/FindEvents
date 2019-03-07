package com.example.findevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdresseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button click;
    public  static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adresse);

        click = (Button) findViewById(R.id.button5);
        data = (TextView) findViewById(R.id.textView3);



    }

    public void jsononClick(View view){
        JSONParser parser =new JSONParser();
        parser.execute();

    }
}
