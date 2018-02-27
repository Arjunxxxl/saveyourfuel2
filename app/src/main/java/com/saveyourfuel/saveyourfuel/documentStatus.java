package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class documentStatus extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    CardView cardView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_status);


        toolbar = findViewById(R.id.toolbarU);
        toolbar.setTitle("Upload Your Documents");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#128C7E"));
        setSupportActionBar(toolbar);
        setView();
    }

    void setView(){
        cardView = findViewById(R.id.upload_doc_cardview);
        cardView.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(documentStatus.this, home.class);
        i.putExtra("Name", home.name);
        i.putExtra("ph", home.ph);
        startActivity(i);
        documentStatus.this.finish();
    }


    @Override
    public void onClick(View v) {
        Log.d("debugger","here inside onclick");
        switch (v.getId()){
            case R.id.upload_doc_cardview:
                Intent i = new Intent("com.saveyourfuel.saveyourfuel.uploadActivity");
                i.putExtra("Name",home.name);
                Log.d("debugger","cardview clicked");
                i.putExtra("ph",home.ph);
                startActivity(i);
                break;
            default:
                Log.d("debugger","here inside onclick");
                break;
        }
    }
}
