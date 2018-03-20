package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.InputStream;

public class aboutusActivity extends AppCompatActivity {

    Toolbar toolbar;
    String name,ph;
    private TextView about_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aboutus);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.name);
        toolbar.setSubtitle(R.string.about_us);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));



        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sharedPref.getString("Name","");
        ph = sharedPref.getString("ph","");

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

        about_tv = findViewById(R.id.tv_aboutus);
        SimpleText();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(aboutusActivity.this,home.class);
        startActivity(i);
        aboutusActivity.this.finish();
    }

    private void SimpleText(){

        try {


            InputStream is = this.getResources().openRawResource(R.raw.aboutus1);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String jsontext = new String(buffer);



            about_tv.setText(jsontext);

        } catch (Exception e) {

            Log.e("Text: ", ""+e.toString());
        }

    }

}
