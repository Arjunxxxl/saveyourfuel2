package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class aboutusActivity extends AppCompatActivity {

    Toolbar toolbar;
    String name,ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Save Your Fuel Pvt. Ltd.");
        toolbar.setSubtitle("About Us");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));

        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(aboutusActivity.this,home.class);
                i.putExtra("Name",name);
                i.putExtra("ph",ph);
                startActivity(i);
                aboutusActivity.this.finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(aboutusActivity.this,home.class);
        i.putExtra("Name",name);
        i.putExtra("ph",ph);
        startActivity(i);
        aboutusActivity.this.finish();
    }

}
