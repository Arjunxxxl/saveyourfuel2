package com.saveyourfuel.saveyourfuel;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    //    CheckBox cemail,cpass;
    TextView reg;
    TextInputLayout temail;
    Button login;
    TextInputEditText email, password;

    public static String FACEBOOK_URL = "https://www.facebook.com/iitp.ac.in/";
    public static String FACEBOOK_PAGE_ID = "iitp.ac.in";
    ImageButton facebookbutton,website;

    SharedPreferences sharedPref ;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
       //         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("User Login");
//        toolbar.setSubtitle("Save Your Fuel Pvt. Ltd.");

        temail = findViewById(R.id.editText4);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        reg = findViewById(R.id.textView4);
        reg.setOnClickListener(this);
        facebookbutton = findViewById(R.id.imageButton2);
        facebookbutton.setOnClickListener(this);
        website = findViewById(R.id.imageButton);
        website.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView4:
                Intent i = new Intent(loginActivity.this, selectTyleActivity.class);
                startActivity(i);
                break;

            case R.id.loginButton:
                checkLogin();
                break;

            case R.id.imageButton2:
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
                break;

            case R.id.imageButton:
                Uri uri = Uri.parse(FACEBOOK_URL); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }

    private void checkLogin() {

        final String passwordText, emailText;
        login.setEnabled(false);
        passwordText = password.getText().toString();
        emailText = email.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://139.59.29.124:3000/authentication?id=" + emailText + "&pwd=" + passwordText;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    Log.d("response", "response " + res);
                    if (res.getString("code").contentEquals("800")) {
                        login.setEnabled(true);

                        sharedPref  = getSharedPreferences("data",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref .edit();
                        editor.putString("username",emailText);
                        editor.putString("password",passwordText);
                        editor.putString("id",res.getString("id"));
                        editor.apply();

                        Intent i = new Intent(loginActivity.this,home.class);
                        i.putExtra("Name",res.getString("name"));
                        i.putExtra("ph",res.getString("phone"));
                        startActivity(i);

                        loginActivity.this.finish();
                    } else {
                        Toast.makeText(getBaseContext(), "WRONG CREDENTIALS!", Toast.LENGTH_SHORT).show();
                        login.setEnabled(true);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Toast.makeText(getBaseContext(), "check your connection...", Toast.LENGTH_SHORT).show();
                login.setEnabled(true);
            }
        });
        queue.add(stringRequest);
//        StringRequest stringRequest = new Str
    }



    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

}
