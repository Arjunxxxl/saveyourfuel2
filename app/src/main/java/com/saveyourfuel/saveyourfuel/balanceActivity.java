package com.saveyourfuel.saveyourfuel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class balanceActivity extends AppCompatActivity {

    Toolbar toolbar;
    Switch aSwitch;
    Button balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Your Balance");
        toolbar.setSubtitle("Arjun Sankhala");

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

}
