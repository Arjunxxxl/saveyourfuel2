package com.saveyourfuel.saveyourfuel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Debug;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class uploadActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Button c1, c2, c3, c4;
    Button finalupload;
    TextView t1, t2, t3, t4;
    private static final int Pick_image1 = 100;
    private static final int Pick_image2 = 200;
    private static final int Pick_image3 = 300;
    private static final int Pick_image4 = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        toolbar = findViewById(R.id.toolbarU);
        toolbar.setTitle("Upload Your Documents");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        c1 = findViewById(R.id.upload1);
        c1.setOnClickListener(this);

        c2 = findViewById(R.id.buttonc2);
        c2.setOnClickListener(this);

        c3 = findViewById(R.id.buttonc3);
        c3.setOnClickListener(this);

        c4 = findViewById(R.id.buttonc4);
        c4.setOnClickListener(this);

        finalupload = findViewById(R.id.finalupload);
        finalupload.setOnClickListener(this);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(uploadActivity.this, home.class);
                startActivity(i);
                uploadActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload1:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, Pick_image1);
                break;
            case R.id.buttonc2:
                Intent i2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i2, Pick_image2);
                break;
            case R.id.buttonc3:
                Intent i3 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i3, Pick_image3);
                break;
            case R.id.buttonc4:
                Intent i4 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i4, Pick_image4);
                break;
            case R.id.finalupload:
                uploadFilesToServer();
                break;
        }
    }

    Bitmap adharCard, license, insurance, rc;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Pick_image1) {
            String path = getRealPathFromURI(data.getData());
            try {
                adharCard = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
                t1.setText(path);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error while selecting", Toast.LENGTH_LONG).show();
            }

        }

        if (resultCode == RESULT_OK && requestCode == Pick_image2) {
            String path2 = getRealPathFromURI(data.getData());
            try {
                license = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                Toast.makeText(getApplicationContext(), path2, Toast.LENGTH_LONG).show();
                t2.setText(path2);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error while selecting", Toast.LENGTH_LONG).show();
            }
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image3) {
            String path3 = getRealPathFromURI(data.getData());

            try {
                insurance = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                Toast.makeText(getApplicationContext(), path3, Toast.LENGTH_LONG).show();
                t3.setText(path3);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error while selecting", Toast.LENGTH_LONG).show();
            }
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image4) {
            String path4 = getRealPathFromURI(data.getData());
            try {
                adharCard = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                Toast.makeText(getApplicationContext(), path4, Toast.LENGTH_LONG).show();
                t4.setText(path4);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error while selecting", Toast.LENGTH_LONG).show();
            }
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

    void uploadFilesToServer(){
        final ProgressDialog progressDialog = new ProgressDialog(uploadActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //converting image to base64 string

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        adharCard.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("size",imageBytes.length+"");

        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "http://139.59.29.124:3000/upload_documents";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            progressDialog.cancel();

                            Toast.makeText(getBaseContext(),"uploaded successfully",Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            progressDialog.cancel();
                            Toast.makeText(getBaseContext(),"cant upload",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(getBaseContext(),"Check your connection..",Toast.LENGTH_SHORT).show();
            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("image", imageString);
                SharedPreferences preferences = getSharedPreferences("data",Context.MODE_PRIVATE);
                params.put("id",preferences.getString("id",""));


                return params;
            }
        };
        queue.add(stringRequest);
    }
}
