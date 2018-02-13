package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class maincomsumerREGActivity extends AppCompatActivity implements View.OnClickListener{

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincomsumer_reg);
        setView();
    }
    void setView(){
        back = findViewById(R.id.backbutton1);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton1:
                Intent i = new Intent(maincomsumerREGActivity.this, selectTyleActivity.class);
                startActivity(i);
                this.finish();
                break;
        }
    }
}
