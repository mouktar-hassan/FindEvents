package com.example.findevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShowEventDetailActivity extends AppCompatActivity {

    private ImageButton mDetailBackBtn;
    private TextView textViewTitle;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_detail);
        mDetailBackBtn=(ImageButton)findViewById(R.id.imageBtnBackDetail);
        textViewTitle=(TextView)findViewById(R.id.tvEventTitle);

        /*Intent myintent=getIntent();
        title=myintent.getStringExtra("ti");
        textViewTitle.setText(title);*/
    }

    public void detailBackonClick(View view){
        ShowEventDetailActivity.this.finish();

    }
}
