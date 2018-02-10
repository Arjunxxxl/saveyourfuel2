package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class home extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Arjun Sankhala");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("7728999684");
        toolbar.setBackgroundColor(Color.parseColor("#80BDBDBD"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.aboutus :
               startActivity(new Intent(home.this,aboutusActivity.class));
                break;
            case R.id.setting :
                startActivity(new Intent(home.this,settingsActivity.class));
                break;
            case R.id.money :
                startActivity(new Intent(home.this,balanceActivity.class));
                break;
            case R.id.upload :
                startActivity(new Intent(home.this,uploadActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
