package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity implements  View.OnClickListener{

    CheckBox cemail,cpass;
    TextView reg;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Save Your Fuel Pvt. Ltd.");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setSubtitle("User Login");
        toolbar.setSubtitleTextColor(Color.parseColor("#000000"));

        cemail = findViewById(R.id.checkBox);
        cpass = findViewById(R.id.checkBox2);
        reg = findViewById(R.id.textView4);


        cemail.setVisibility(View.INVISIBLE);
        cpass.setVisibility(View.INVISIBLE);

        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.textView4:
                Intent i = new Intent(loginActivity.this, selectTyleActivity.class);
                startActivity(i);
                break;
        }
    }
}
