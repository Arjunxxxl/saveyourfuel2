package com.saveyourfuel.saveyourfuel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class truckOwnerDetails extends AppCompatActivity implements View.OnClickListener {

    ImageView truckImage;
    TextView name, contact, company, price;
    String p_id;
    Toolbar toolbar;
    FloatingActionButton fab;
    ProgressBar progressBar;
    Bitmap truck = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_owner_details);
        setView();
        getImage();
    }

    void setView() {
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        company = findViewById(R.id.company);
        price = findViewById(R.id.price);
        truckImage = findViewById(R.id.truck_image);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress);
        fab = findViewById(R.id.call_button);
        fab.setOnClickListener(this);

        Intent i = getIntent();
        name.setText(i.getExtras().getString("name", ""));
        contact.setText(i.getExtras().getString("phone", ""));
        price.setText(i.getExtras().getString("price", ""));
        company.setText(i.getExtras().getString("company", ""));
        p_id = i.getExtras().getString("p_id", "");

        toolbar.setTitle("Truck owner Details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                truckOwnerDetails.this.finish();
            }
        });


    }


    void getImage() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://139.59.29.124:3000/truck-image?p_id=" + p_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("code").contentEquals("800")) {

                        String imageString = jsonObject.getString("image");

                        if (!imageString.isEmpty()) {
                            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                            truck = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            truckImage.setImageBitmap(truck);
                            progressBar.setVisibility(View.GONE);

                        }
                    } else {
                        Snackbar.make(findViewById(R.id.contact_container), "Can't retrieve image", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Snackbar.make(findViewById(R.id.contact_container), R.string.check_your_connection, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_button:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getText().toString()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(truckOwnerDetails.this,
                            Manifest.permission.CALL_PHONE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(truckOwnerDetails.this,
                                new String[]{Manifest.permission.CALL_PHONE},100);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }

                }
                else{
                    startActivity(intent);
                }

        }
    }
}
