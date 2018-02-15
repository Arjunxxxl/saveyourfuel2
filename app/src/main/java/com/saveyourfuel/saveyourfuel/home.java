package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
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
import com.saveyourfuel.saveyourfuel.adapters.cardAdapter;
import com.saveyourfuel.saveyourfuel.models.card;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    public static String name, ph;
    TextView nameT, phT;
    Bitmap profileImage = null;
    ImageView pic;
    String profile_image="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.mainViewHolder);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<card> cards = new ArrayList<>();
        cards.add(new card("Buy", "description", R.mipmap.buybag));
        cards.add(new card("Add Funds", "description", R.mipmap.creditcard));
        cards.add(new card("Upload", "description", R.mipmap.uploaddoc));

        recyclerView.setAdapter(new cardAdapter(cards,getApplicationContext()));


        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        setImage();

//        String imageString = profile_image;
//        if (!imageString.isEmpty()) {
//            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
//            profileImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        }

        toolbar.setTitle("Save Your Fuel");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Your Profile");

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#00000000"));

        nameT = findViewById(R.id.user_name);
        phT = findViewById(R.id.phone_user);
        nameT.setText(name);
        phT.setText(ph);

       pic = findViewById(R.id.user_profile_pic);
//        pic.setImageBitmap(profileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutus:
                Intent i = new Intent(home.this, aboutusActivity.class);
                i.putExtra("Name", name);
                i.putExtra("ph", ph);
                startActivity(i);
                break;
            case R.id.setting:
                Intent i1 = new Intent(home.this, settingsActivity.class);
                i1.putExtra("Name", name);
                i1.putExtra("ph", ph);
                startActivity(i1);
                break;
            case R.id.money:
                Intent i2 = new Intent(home.this, balanceActivity.class);
                i2.putExtra("Name", name);
                i2.putExtra("ph", ph);
                startActivity(i2);
                break;
            case R.id.upload:
                Intent i4 = new Intent(home.this, uploadActivity.class);
                i4.putExtra("Name", name);
                i4.putExtra("ph", ph);
                startActivity(i4);
                break;

            case R.id.logout:

                Toast.makeText(getApplicationContext(), "You have being logout", Toast.LENGTH_LONG).show();
                SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
                sharedPref.edit().clear().apply();
                startActivity(new Intent(home.this, loginActivity.class));
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setImage() {

        String passwordText, emailText;

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String pass = sharedPref.getString("password", "");
        Log.d("usemane", username + "");
        Log.d("pass", pass + "");
        if (username.isEmpty() || pass.isEmpty()) {
            Intent i1 = new Intent(home.this, loginActivity.class);
            startActivity(i1);
            home.this.finish();
        } else {
            passwordText = pass;
            emailText = username;
            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "http://139.59.29.124:3000/authentication?id=" + emailText + "&pwd=" + passwordText;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);

                        Log.d("response", "response " + res);
                        if (res.getString("code").contentEquals("800")) {

                            profile_image = res.getString("profile");
                            String imageString = profile_image;
                            if (!imageString.isEmpty()) {
                                byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                                profileImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            }
                            pic.setImageBitmap(profileImage);

                        } else {
                            Toast.makeText(getBaseContext(), "Login Please!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(home.this, loginActivity.class));
                            home.this.finish();
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

                }
            });
            queue.add(stringRequest);
//        StringRequest stringRequest = new Str
        }
    }

}
