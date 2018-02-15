package com.saveyourfuel.saveyourfuel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class uploadActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageButton c1, c2, c3, c4, c5;
    Button finalupload;
    TextView t1, t2, t3, t4, t5;
    CheckBox check1, check2, check3, check4, check5;
    private static final int Pick_image1 = 100;
    private static final int Pick_image2 = 200;
    private static final int Pick_image3 = 300;
    private static final int Pick_image4 = 400;
    private static final int Pick_image5 = 500;

    LinearLayout l1, l2, l3, l4, l5;

    String name, ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        toolbar = findViewById(R.id.toolbarU);
        toolbar.setTitle("Upload Your Documents");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        setSupportActionBar(toolbar);

        setview();
        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(uploadActivity.this, home.class);
                i.putExtra("Name", name);
                i.putExtra("ph", ph);
                startActivity(i);
                uploadActivity.this.finish();
            }
        });
        check1.setVisibility(View.INVISIBLE);
        check2.setVisibility(View.INVISIBLE);
        check3.setVisibility(View.INVISIBLE);
        check4.setVisibility(View.INVISIBLE);
        check5.setVisibility(View.INVISIBLE);
        l1 = findViewById(R.id.layout1);
        l2 = findViewById(R.id.layout2);
        l3 = findViewById(R.id.layout3);
        l4 = findViewById(R.id.layout4);
        l5 = findViewById(R.id.layout5);

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);

    }


    void setview() {
        c1 = findViewById(R.id.buttonc1);
        c1.setOnClickListener(this);

        c2 = findViewById(R.id.buttonc2);
        c2.setOnClickListener(this);

        c3 = findViewById(R.id.buttonc3);
        c3.setOnClickListener(this);

        c4 = findViewById(R.id.buttonc4);
        c4.setOnClickListener(this);

        c5 = findViewById(R.id.uploadProfile);
        c5.setOnClickListener(this);

        finalupload = findViewById(R.id.finalupload);
        finalupload.setOnClickListener(this);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.tProfile);

        check1 = findViewById(R.id.check);
        check2 = findViewById(R.id.check1);
        check3 = findViewById(R.id.check2);
        check4 = findViewById(R.id.check3);
        check5 = findViewById(R.id.check4);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(uploadActivity.this, home.class);
        i.putExtra("Name", name);
        i.putExtra("ph", ph);
        startActivity(i);
        uploadActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonc1:
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
            case R.id.uploadProfile:
                Intent i5 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i5, Pick_image5);
                break;
            case R.id.finalupload:
                uploadFilesToServer();
                break;

            case R.id.layout1:
                Snackbar.make(view, "Upload Your Profile Picture" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                break;
            case R.id.layout2:
                Snackbar.make(view, "Upload Your Aadhar Card" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                break;
            case R.id.layout3:
                Snackbar.make(view, "Upload Your License" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                break;
            case R.id.layout4:
                Snackbar.make(view, "Upload Your Insurance" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                break;
            case R.id.layout5:
                Snackbar.make(view, "Upload Your RC" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                break;
        }
    }

    Uri adhar, license, insurance, rc, profile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Pick_image1) {
            String path = getRealPathFromURI(data.getData());
            adhar = data.getData();
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
            t1.setText(path);
            check2.setVisibility(View.VISIBLE);
            check2.setChecked(true);
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image2) {
            String path2 = getRealPathFromURI(data.getData());
            license = data.getData();
            Toast.makeText(getApplicationContext(), path2, Toast.LENGTH_LONG).show();
            t2.setText(path2);
            check3.setVisibility(View.VISIBLE);
            check3.setChecked(true);
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image3) {
            String path3 = getRealPathFromURI(data.getData());
            insurance = data.getData();
            Toast.makeText(getApplicationContext(), path3, Toast.LENGTH_LONG).show();
            t3.setText(path3);
            check4.setVisibility(View.VISIBLE);
            check4.setChecked(true);
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image4) {
            String path4 = getRealPathFromURI(data.getData());

            rc = data.getData();
            Toast.makeText(getApplicationContext(), path4, Toast.LENGTH_LONG).show();
            t4.setText(path4);
            check5.setVisibility(View.VISIBLE);
            check5.setChecked(true);
        }
        if (resultCode == RESULT_OK && requestCode == Pick_image5) {
            String path5 = getRealPathFromURI(data.getData());

            profile = data.getData();
            Toast.makeText(getApplicationContext(), path5, Toast.LENGTH_LONG).show();
            t5.setText(path5);
            check1.setVisibility(View.VISIBLE);
            check1.setChecked(true);
        }
    }

    StringRequest adharReq, licenseReq, insuranceReq, rcReq, profileReq;

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


    String url = "http://139.59.29.124:3000/upload_documents";


    void uploadFilesToServer() {


        RequestQueue queue = Volley.newRequestQueue(this);
        setRequest();


        if (!t1.getText().toString().contentEquals("File")) {
            queue.add(adharReq);
        }
        if (!t2.getText().toString().contentEquals("File")) {
            queue.add(licenseReq);
        }
        if (!t3.getText().toString().contentEquals("File")) {
            queue.add(insuranceReq);
        }
        if (!t4.getText().toString().contentEquals("File")) {
            queue.add(rcReq);
        }
        if (!t5.getText().toString().contentEquals("File")) {
            queue.add(profileReq);
        }


    }

    void setRequest() {

        final ProgressDialog progressDialog = new ProgressDialog(uploadActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.setCancelable(false);

//        progressDialog.show();

        final Toast toast = new Toast(getBaseContext());
        licenseReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            progressDialog.cancel();
                            check3.setChecked(true);


                            toast.makeText(getBaseContext(), "license uploaded successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
//                            progressDialog.cancel();
                            toast.makeText(getBaseContext(), "cant upload", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.cancel();
                toast.makeText(getBaseContext(), "Check your connection..", Toast.LENGTH_SHORT).show();
            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                progressDialog.setMessage("uploading license image..");
//                progressDialog.show();
//                handler.sendEmptyMessage(0);
                Map<String, String> params = new HashMap<>();
                Bitmap licenseImage = null;
                try {
                    licenseImage = MediaStore.Images.Media.getBitmap(getContentResolver(), license);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    licenseImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    Log.d("size", imageBytes.length + "");
                    params.put("image", imageString);
                    params.put("imageName", "license");
                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    params.put("id", preferences.getString("id", ""));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };
        insuranceReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            progressDialog.cancel();
                            check4.setChecked(true);

                            toast.makeText(getBaseContext(), "insurance uploaded successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
//                            progressDialog.cancel();
                            toast.makeText(getBaseContext(), "cant upload", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.cancel();
                toast.makeText(getBaseContext(), "Check your connection..", Toast.LENGTH_SHORT).show();
            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                progressDialog.setMessage("uploading insurance image..");
//                progressDialog.show();
//                handler.sendEmptyMessage(0);
                Map<String, String> params = new HashMap<>();
                Bitmap insuranceImage = null;
                try {
                    insuranceImage = MediaStore.Images.Media.getBitmap(getContentResolver(), insurance);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    insuranceImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    Log.d("size", imageBytes.length + "");
                    params.put("image", imageString);
                    params.put("imageName", "insurance");
                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    params.put("id", preferences.getString("id", ""));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };


        adharReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            progressDialog.cancel();
                            check2.setChecked(true);

                            toast.makeText(getBaseContext(), "Adhar uploaded successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
//                            progressDialog.cancel();
                            toast.makeText(getBaseContext(), "cant upload", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.cancel();
                toast.makeText(getBaseContext(), "Check your connection..", Toast.LENGTH_SHORT).show();
            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                progressDialog.setMessage("uploading adhar image..");
                //progressDialog.show();
//                handler.sendEmptyMessage(0);
                Map<String, String> params = new HashMap<>();
                Bitmap adharCard = null;
                try {
                    adharCard = MediaStore.Images.Media.getBitmap(getContentResolver(), adhar);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    adharCard.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    Log.d("size", imageBytes.length + "");
                    params.put("image", imageString);
                    params.put("imageName", "adhar");
                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    params.put("id", preferences.getString("id", ""));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };

        rcReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            progressDialog.cancel();
                            check5.setChecked(true);
                            toast.makeText(getBaseContext(), "RC uploaded successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
//                            progressDialog.cancel();
                            toast.makeText(getBaseContext(), "cant upload", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.cancel();final ProgressDialog progressDialog = new ProgressDialog(uploadActivity.this);
                toast.makeText(getBaseContext(), "Check your connection..", Toast.LENGTH_SHORT).show();
            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                progressDialog.setMessage("uploading RC image..");
                //progressDialog.show();
//                handler.sendEmptyMessage(0);
                Map<String, String> params = new HashMap<>();
                Bitmap rcImage = null;
                try {
                    rcImage = MediaStore.Images.Media.getBitmap(getContentResolver(), rc);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    rcImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    Log.d("size", imageBytes.length + "");
                    params.put("image", imageString);
                    params.put("imageName", "RC");
                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    params.put("id", preferences.getString("id", ""));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };


        profileReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            progressDialog.cancel();

                            check1.setChecked(true);
                            toast.makeText(getBaseContext(), "Profile picture uploaded successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
//                            progressDialog.cancel();
                            toast.makeText(getBaseContext(), "cant upload", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.cancel();
                toast.makeText(getBaseContext(), "Check your connection..", Toast.LENGTH_SHORT).show();
            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                progressDialog.setMessage("uploading profile image..");
                //progressDialog.show();
//                handler.sendEmptyMessage(0);
                Map<String, String> params = new HashMap<>();
                Bitmap profileImage = null;
                try {
                    profileImage = MediaStore.Images.Media.getBitmap(getContentResolver(), profile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    profileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    Log.d("size", imageBytes.length + "");
                    params.put("image", imageString);
                    params.put("imageName", "profile");
                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    params.put("id", preferences.getString("id", ""));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };
    }
}
