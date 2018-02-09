package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class consumerREGActivity extends AppCompatActivity implements View.OnClickListener{

    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_reg);

        reg = findViewById(R.id.button3);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button3:
                Intent i = new Intent(consumerREGActivity.this,loginActivity.class);
                consumerREGActivity.this.startActivity(i);
                consumerREGActivity.this.finish();
                break;
        }
    }
}
