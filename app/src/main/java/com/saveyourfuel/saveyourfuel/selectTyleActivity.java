package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class selectTyleActivity extends AppCompatActivity implements View.OnClickListener {

    Button consumer, trasnsport;
    Toolbar toolbar;

    public static String FACEBOOK_URL = "https://www.facebook.com/iitp.ac.in/";
    public static String FACEBOOK_PAGE_ID = "iitp.ac.in";
    ImageButton facebookbutton, website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

        setContentView(R.layout.activity_select_tyle);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        toolbar.setSubtitle(" ");
        toolbar.setBackgroundColor(Color.parseColor("#00000000"));
        consumer = findViewById(R.id.button);
        trasnsport = findViewById(R.id.button1);
        consumer.setOnClickListener(this);
        trasnsport.setOnClickListener(this);
        facebookbutton = findViewById(R.id.imageButton2);
        facebookbutton.setOnClickListener(this);
        website = findViewById(R.id.imageButton);
        website.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(selectTyleActivity.this, loginActivity.class);
                startActivity(i);
                selectTyleActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent mainIntent = new Intent(selectTyleActivity.this, maincomsumerREGActivity.class);
                startActivity(mainIntent);
                selectTyleActivity.this.finish();
                break;
            case R.id.button1:
                Intent mainIntent1 = new Intent(selectTyleActivity.this, consumerREGActivity.class);
                startActivity(mainIntent1);
                selectTyleActivity.this.finish();
                break;

            case R.id.imageButton2:
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
                break;

            case R.id.imageButton:
                Uri uri = Uri.parse(FACEBOOK_URL); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

}
