package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class buyActivity extends AppCompatActivity implements  View.OnClickListener{
    Toolbar toolbar;
    String name,ph;
    Button back;
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
            window.setStatusBarColor(Color.parseColor("#003240"));
        }
        setContentView(R.layout.activity_buy);

        toolbar = findViewById(R.id.toolbar_buy);
        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        toolbar.setTitle(R.string.buy);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(R.string.name);

        back = findViewById(R.id.button_back2);
        back.setOnClickListener(this);

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(buyActivity.this,home.class);
                i.putExtra("Name",name);
                i.putExtra("ph",ph);
                startActivity(i);
                buyActivity.this.finish();
            }
        });

        Snackbar.make(findViewById(android.R.id.content),R.string.coming_soon,Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(buyActivity.this,home.class);
        i.putExtra("Name",name);
        i.putExtra("ph",ph);
        startActivity(i);
        buyActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button_back2: Intent i = new Intent(buyActivity.this,home.class);
                i.putExtra("Name", home.name);
                i.putExtra("ph", home.ph);
                startActivity(i);
                buyActivity.this.finish();
        }
    }
}
