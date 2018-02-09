package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

public class consumerREGActivity extends AppCompatActivity {

   android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_reg);

        toolbar =  findViewById(R.id.tootbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Consumer Registration");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
    }

}
