package com.saveyourfuel.saveyourfuel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    //    CheckBox cemail,cpass;
    TextView reg;
    TextInputLayout temail;
    Button login;
    TextInputEditText email, password;
    Button sound;
    static MediaPlayer play_music;

    public static String FACEBOOK_URL = "https://www.facebook.com/Syf-166804867448828/?modal=admin_todo_tour";
    public static String FACEBOOK_PAGE_ID = "https://www.facebook.com/Syf-166804867448828/?modal=admin_todo_tour";
    ImageButton facebookbutton, website;

    SharedPreferences sharedPref;
    ImageView loginFlag;
    LinearLayout changeLanguage;

    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);

        String languageToLoad  = sharedPref.getString("lang","en");

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);

        loginFlag = findViewById(R.id.login_flag);
        if(languageToLoad.contentEquals("en")){
            loginFlag.setImageResource(R.drawable.india);
        }
        else{
            loginFlag.setImageResource(R.drawable.flagusa);
        }
        setView();
    }

    void setView(){

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

        changeLanguage = findViewById(R.id.login_change_language);
        changeLanguage.setOnClickListener(this);

        sound = findViewById(R.id.sound);
        sound.setOnClickListener(this);
        if (play_music == null) {
            play_music = MediaPlayer.create(getApplicationContext(), R.raw.uprising);
            onPrepared(play_music);
            play_music.setLooping(true);
            sound.setBackgroundResource(R.drawable.ic_action_sound);
        }

        if(play_music.isPlaying()){
            sound.setBackgroundResource(R.drawable.ic_action_soundplay);
        }else{
            sound.setBackgroundResource(R.drawable.ic_action_sound);
        }

    }

    public void onPrepared(MediaPlayer player) {
        player.start();
        player.pause();
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
            case R.id.sound:
                if (play_music.isPlaying()) {
                    play_music.pause();
                    sound.setBackgroundResource(R.drawable.ic_action_sound);
                } else if (!play_music.isPlaying()) {
                    play_music.start();
                    sound.setBackgroundResource(R.drawable.ic_action_soundplay);
                }

                break;
            case R.id.login_change_language:
                SharedPreferences sharedPrefer = getSharedPreferences("data", Context.MODE_PRIVATE);


                if (sharedPrefer.getString("lang","en").equals("en")) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("lang", "hi");
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("lang", "en");
                    editor.apply();
                }
                loginActivity.this.finish();
                Intent i5 = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i5);
                break;
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        play_music.stop();
        play_music.release();
        play_music = null;
        loginActivity.this.finish();
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

                        sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", emailText);
                        editor.putString("password", passwordText);
                        editor.putString("id", res.getString("id"));
                        editor.apply();

                        play_music.stop();
                        play_music.release();
                        play_music = null;
                        FirebaseMessaging.getInstance().subscribeToTopic("transporter");

                        SharedPreferences sharedPreferences = getSharedPreferences("data",Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("Name",res.getString("name")).apply();
                        sharedPreferences.edit().putString("ph",res.getString("phone")).apply();

                        Intent i = new Intent(loginActivity.this, home.class);
                        startActivity(i);

                        loginActivity.this.finish();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), R.string.WRONG_CREDENTIALS, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
                Snackbar.make(findViewById(android.R.id.content), R.string.check_your_connection, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
