package com.example.findevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ShowEventDetailActivity extends AppCompatActivity {

    private ImageButton mDetailBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_detail);
        mDetailBackBtn=(ImageButton)findViewById(R.id.imageBtnBackDetail);
    }

    public void detailBackonClick(View view){
        ShowEventDetailActivity.this.finish();

    }
}
