package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class selectTyleActivity extends AppCompatActivity implements View.OnClickListener{

    Button consumer,trasnsport;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tyle);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        toolbar.setSubtitle(" ");
        consumer = findViewById(R.id.button);
        trasnsport = findViewById(R.id.button1);
        consumer.setOnClickListener(this);
        trasnsport.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(selectTyleActivity.this,loginActivity.class);
                startActivity(i);
                selectTyleActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button :
                Intent mainIntent = new Intent(selectTyleActivity.this, consumerREGActivity.class);
                startActivity(mainIntent);
                //selectTyleActivity.this.finish();
                break;
            case R.id.button1 :
                Intent mainIntent1 = new Intent(selectTyleActivity.this, consumerREGActivity.class);
                startActivity(mainIntent1);
                //selectTyleActivity.this.finish();
                break;
        }
    }
}
