package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class maincomsumerREGActivity extends AppCompatActivity implements View.OnClickListener{

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincomsumer_reg);
        setView();
        Snackbar.make(findViewById(android.R.id.content), R.string.coming_soon, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

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
