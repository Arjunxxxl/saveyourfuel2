package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class balanceActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;

    String name,ph;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        toolbar = findViewById(R.id.toolbarkok);
        toolbar.setTitle(R.string.balance);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(R.string.name);

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sharedPref.getString("Name","");
        ph = sharedPref.getString("ph","");


        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(balanceActivity.this,home.class);
                startActivity(i);
                balanceActivity.this.finish();
            }
        });

        back = findViewById(R.id.button_back);
        back.setOnClickListener(this);

        Snackbar.make(findViewById(android.R.id.content),R.string.coming_soon,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.balance_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.balance_money:
                Toast.makeText(getApplicationContext(),"you have 0.00 Rs. in you account",Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(balanceActivity.this,home.class);
        startActivity(i);
        balanceActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_back :
                Intent i4 = new Intent(balanceActivity.this,home.class);
                startActivity(i4);
                balanceActivity.this.finish();
                break;
        }
    }
}
