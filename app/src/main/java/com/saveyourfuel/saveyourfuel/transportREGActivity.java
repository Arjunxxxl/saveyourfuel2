package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class transportREGActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    Button Treg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_reg);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Transportation Registration");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));

        Treg = findViewById(R.id.button3);
        Treg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
         case R.id.button3 :
             Intent i = new Intent(transportREGActivity.this,loginActivity.class);
             transportREGActivity.this.startActivity(i);
             transportREGActivity.this.finish();
             break;
        }
    }
}
