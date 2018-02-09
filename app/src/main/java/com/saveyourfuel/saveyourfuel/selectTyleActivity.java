package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectTyleActivity extends AppCompatActivity implements View.OnClickListener{

    Button consumer,trasnsport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tyle);
        consumer = findViewById(R.id.button);
        trasnsport = findViewById(R.id.button1);
        consumer.setOnClickListener(this);
        trasnsport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button :
                Intent mainIntent = new Intent(selectTyleActivity.this, consumerREGActivity.class);
                startActivity(mainIntent);
                //selectTyleActivity.this.finish();
                break;
            case R.id.button1 :
                Intent mainIntent1 = new Intent(selectTyleActivity.this, transportREGActivity.class);
                startActivity(mainIntent1);
                //selectTyleActivity.this.finish();
                break;
        }
    }
}
