package com.saveyourfuel.saveyourfuel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class sell_spare_partsActivity extends AppCompatActivity implements  View.OnClickListener {

    static String url = "http://139.59.29.124:3000/spare-sell";
    EditText name, phone, price, description, spareName;
    ProgressDialog progressDialog;
    RelativeLayout container;
    FloatingActionButton truckList;
    private static final int Pick_image1 = 100, Pick_image2 = 200;
    Uri photo1_uri;

    boolean nameb, phoneb, priceb, descriptionb, spareNameb;

    Toolbar toolbar;
    String priceValue, descriptionValue, spareNameValue;
    TextView photo1, uploadButton;
    String nameValue, phoneValue;
    String ph, nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_spare_parts);
        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        nm = sharedPref.getString("Name", "");
        ph = sharedPref.getString("ph", "");

        setView();

    }

    void setView() {
        container = findViewById(R.id.container);

        toolbar = findViewById(R.id.toolbar);

        photo1 = findViewById(R.id.photo1);
        photo1.setOnClickListener(this);


        toolbar.setTitle("Sell spare items");
        setSupportActionBar(toolbar);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        spareName = findViewById(R.id.spare_part_name);
        phone = findViewById(R.id.phone);
        name.setText(nm);
        phone.setText(ph);

        uploadButton = findViewById(R.id.upload_document_button);
        uploadButton.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sell_spare_partsActivity.this, buyActivity.class);
                i.putExtra("Name", nm);
                i.putExtra("ph", ph);
                startActivity(i);
                sell_spare_partsActivity.this.finish();
            }
        });

        truckList = findViewById(R.id.truck_list);
        truckList.setOnClickListener(this);


        if (name.getText().toString().isEmpty()) {
            nameb = false;
        } else {
            nameb = true;
        }

        if (phone.getText().toString().isEmpty()) {
            phoneb = false;
        } else {
            phoneb = true;
        }

        if (price.getText().toString().isEmpty()) {
            priceb = false;
        } else {
            priceb = true;
        }

        if (description.getText().toString().isEmpty()) {
            descriptionb = false;
        } else {
            descriptionb = true;
        }

        if (spareName.getText().toString().isEmpty()) {
            spareNameb = false;
        } else {
            spareNameb = true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Pick_image1) {
            String path = getRealPathFromURI(data.getData());
            photo1_uri = data.getData();
            photo1.setText(path);

        }


    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.upload_document_button:
                setupRequest();
                break;
            case R.id.photo1:
                i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, Pick_image1);
                break;
            case R.id.rc:
                i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, Pick_image2);
                break;
            case R.id.truck_list:
                i = new Intent(sell_spare_partsActivity.this, spareList.class);
                startActivity(i);
                break;


        }
    }

    private void setupRequest() {

        if (nameb && priceb && phoneb && descriptionb && spareNameb){

            nameValue = name.getText().toString();
        phoneValue = phone.getText().toString();
        priceValue = price.getText().toString();
        descriptionValue = description.getText().toString();
        spareNameValue = spareName.getText().toString();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading documents!!");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                Intent i = new Intent(sell_spare_partsActivity.this, buyActivity.class);
                i.putExtra("Name", nm);
                i.putExtra("ph", ph);
                startActivity(i);
                sell_spare_partsActivity.this.finish();
            }
        });


        StringRequest documentReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("debug", "in response listner");
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            Log.d("response code", code);
                            if (code.contentEquals("900")) {
                                progressDialog.dismiss();
                                Snackbar.make(container, R.string.upload_succedd, Snackbar.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                Snackbar.make(container, "Some error occurred!", Snackbar.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Snackbar.make(container, R.string.upload_error, Snackbar.LENGTH_LONG).show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(container, R.string.check_your_connection, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                progressDialog.dismiss();

            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), photo1_uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    image.recycle();
                    byte[] imageBytes = baos.toByteArray();
                    final String photo1String = Base64.encodeToString(imageBytes, Base64.DEFAULT);


                    Log.d("size", imageBytes.length + "");

                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);

                    params.put("photo1", photo1String);

                    params.put("name", nameValue);
                    params.put("phone", phoneValue);
                    params.put("price", priceValue);
                    params.put("spare_part", spareNameValue);
                    params.put("description", descriptionValue);

                    params.put("id", preferences.getString("id", ""));
                    Log.d("debug", "finished setting parameters");


                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };

        queue.add(documentReq);


    }else{
            Snackbar.make(findViewById(android.R.id.content), R.string.field_empty, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

        }
}

}

