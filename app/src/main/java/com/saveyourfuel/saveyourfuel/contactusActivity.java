package com.saveyourfuel.saveyourfuel;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class contactusActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    String name,ph;
    LinearLayout copy;
    TextView tv_ph;
    EditText query;
    String query_data;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
            window.setStatusBarColor(Color.parseColor("#003240"));
        }
        setContentView(R.layout.activity_contactus);


        toolbar = findViewById(R.id.toolbar_contactus);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.contact_us);
        toolbar.setSubtitle(R.string.name);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));

        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contactusActivity.this,home.class);
                i.putExtra("Name",name);
                i.putExtra("ph",ph);
                startActivity(i);
                contactusActivity.this.finish();
            }
        });

        copy = findViewById(R.id.copy_phno);
        copy.setOnClickListener(this);
        tv_ph = findViewById(R.id.tv_phoneus);
        send= findViewById(R.id.send);
        send.setOnClickListener(this);

        query = findViewById(R.id.et_query);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(contactusActivity.this,home.class);
        i.putExtra("Name",name);
        i.putExtra("ph",ph);
        startActivity(i);
        contactusActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.copy_phno:
                String ph2 = tv_ph.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Phone number", ph2);
                clipboard.setPrimaryClip(clip);
                String snack_text = ph2 + getString(R.string.phonecopy);
                Snackbar.make(findViewById(android.R.id.content),snack_text,Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.send:
                String data = name + " " + ph;
                query_data = query.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setType("text/plain");
                i.setData(Uri.parse("saveyourfuel2@gmail.com"));
                i.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"saveyourfuel2@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, data);
                i.putExtra(Intent.EXTRA_TEXT   , query_data);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.support_app_notinstalled,Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
