package com.example.findevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShowEventDetailActivity extends AppCompatActivity {

    private ImageButton mDetailBackBtn;
    private TextView textViewTitle;
    private String title;
    private Button commentaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_detail);
        mDetailBackBtn=(ImageButton)findViewById(R.id.imageBtnBackDetail);
        textViewTitle=(TextView)findViewById(R.id.tvEventTitle);
        commentaire=(Button) findViewById(R.id.commentaire_button);
        commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCommentaire =new Intent(ShowEventDetailActivity.this, Commentaire.class);
                startActivity(addCommentaire);
            }
        });
        /*Intent myintent=getIntent();
        title=myintent.getStringExtra("ti");
        textViewTitle.setText(title);*/
    }

    public void detailBackonClick(View view){
        ShowEventDetailActivity.this.finish();

    }
}
