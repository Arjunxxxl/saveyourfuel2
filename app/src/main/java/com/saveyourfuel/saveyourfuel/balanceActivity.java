package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class balanceActivity extends AppCompatActivity {

    Toolbar toolbar;
    Switch aSwitch;
    Button balance;
    String name,ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        toolbar = findViewById(R.id.toolbarkok);
        toolbar.setTitle("Add Balance");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Save Your Fuel");
        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(balanceActivity.this,home.class);
                i.putExtra("Name",name);
                i.putExtra("ph",ph);
                startActivity(i);
                balanceActivity.this.finish();
            }
        });

        aSwitch = findViewById(R.id.switch2);
        balance = findViewById(R.id.button6);
        balance.setText("Add Balance");
        aSwitch.setChecked(false);


        aSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // do something, the isChecked will be
                        // true if the switch is in the On position
                        if(isChecked == false) balance.setText("Add Balance");
                        else if(isChecked==true) balance.setText("Retrieve Balance");
                    }
                }
        );
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
        i.putExtra("Name",name);
        i.putExtra("ph",ph);
        startActivity(i);
        balanceActivity.this.finish();
    }


}
