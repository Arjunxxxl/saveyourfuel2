package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class settingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    String name,ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
         name = i.getExtras().getString("Name", "");
         ph = i.getExtras().getString("ph", "");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settingsActivity.this,home.class);
                i.putExtra("Name",name);
                i.putExtra("ph",ph);
                startActivity(i);
                settingsActivity.this.finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(settingsActivity.this,home.class);
        i.putExtra("Name",name);
        i.putExtra("ph",ph);
        startActivity(i);
        settingsActivity.this.finish();
    }
}
