package com.saveyourfuel.saveyourfuel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class uploadActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView showFile, upload_document_button;

    private static final int Pick_image1 = 100;

    String name, ph;
    RelativeLayout container;
    Spinner upload_choice, insurance_select;

    RelativeLayout insurance_layout;

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
        setContentView(R.layout.activity_upload);

        toolbar = findViewById(R.id.toolbarU);
        toolbar.setTitle(R.string.uploadDocuments);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));
        setSupportActionBar(toolbar);

        insurance_layout = findViewById(R.id.insurance_hidden);
        insurance_layout.setVisibility(View.GONE);

        setView();
        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(uploadActivity.this, documentStatus.class);
                i.putExtra("Name", name);
                i.putExtra("ph", ph);
                startActivity(i);
                uploadActivity.this.finish();
            }
        });


    }


    void setView() {
        upload_choice = findViewById(R.id.upload_document_selector);
        insurance_select = findViewById(R.id.upload_document_selector2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.document_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.insurance_type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        upload_choice.setAdapter(adapter);
        upload_choice.setOnItemSelectedListener(this);
        insurance_select.setAdapter(adapter2);
        insurance_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        make_Toast(adapterView,i);
                        break;
                    case 1:
                        make_Toast(adapterView,i);
                        break;
                    case 2:
                        make_Toast(adapterView,i);
                        break;
                    case 3:
                        make_Toast(adapterView,i);
                        break;
                    case 4:
                        make_Toast(adapterView,i);
                        break;
                    case 5:
                        make_Toast(adapterView,i);
                        break;
                    case 6:
                        make_Toast(adapterView,i);
                        break;
                    case 7:
                        make_Toast(adapterView,i);
                        break;
                    case 8:
                        make_Toast(adapterView,i);
                        break;
                    case 9:
                        make_Toast(adapterView,i);
                        break;
                    case 10:
                        make_Toast(adapterView,i);
                        break;
                    case 11:
                        make_Toast(adapterView,i);
                        break;
                    case 12:
                        make_Toast(adapterView,i);
                        break;
                    case 13:
                        make_Toast(adapterView,i);
                        break;
                    case 14:
                        make_Toast(adapterView,i);
                        break;
                    case 15:
                        make_Toast(adapterView,i);
                        break;
                    case 16:
                        make_Toast(adapterView,i);
                        break;
                    case 17:
                        make_Toast(adapterView,i);
                        break;
                    case 18:
                        make_Toast(adapterView,i);
                        break;
                    case 19:
                        make_Toast(adapterView,i);
                        break;
                    case 20:
                        make_Toast(adapterView,i);
                        break;
                    case 21:
                        make_Toast(adapterView,i);
                        break;
                    case 22:
                        make_Toast(adapterView,i);
                        break;
                    case 23:
                        make_Toast(adapterView,i);
                        break;
                    case 24:
                        make_Toast(adapterView,i);
                        break;
                    case 25:
                        make_Toast(adapterView,i);
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        showFile = findViewById(R.id.upload_filename);
        showFile.setOnClickListener(this);

        container = findViewById(R.id.upload_container);

        upload_document_button = findViewById(R.id.upload_document_button);
        upload_document_button.setOnClickListener(this);


    }

    void make_Toast(AdapterView<?> adapterView,int pos)
    {
        Toast.makeText(getApplicationContext(), (CharSequence) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(uploadActivity.this, documentStatus.class);
        i.putExtra("Name", name);
        i.putExtra("ph", ph);
        startActivity(i);
        uploadActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_filename:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, Pick_image1);
                break;
            case R.id.upload_document_button:
                uploadFilesToServer();
                break;
//
//            case R.id.layout1:
//                Snackbar.make(view, "Upload Your Profile Picture" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                break;

        }
    }

    Uri document;
    String imagename;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Pick_image1) {
            String path = getRealPathFromURI(data.getData());
            document = data.getData();
//            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
            showFile.setText(path);

        }

    }

    StringRequest documentReq;

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
        progressDialog = new ProgressDialog(uploadActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        final RequestQueue queue = Volley.newRequestQueue(this);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                Intent i = new Intent(uploadActivity.this, documentStatus.class);
                startActivity(i);
                uploadActivity.this.finish();
            }
        });

        setRequest();


        if (!imagename.isEmpty() && !showFile.getText().toString().contains("File")) {
            queue.add(documentReq);
        } else if (imagename.isEmpty()) {

            Snackbar.make(container, "select the document type", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            progressDialog.dismiss();
        } else {

            Snackbar.make(container, "select the document file", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            progressDialog.dismiss();
        }

    }

    ProgressDialog progressDialog;

    void setRequest() {


        final Toast toast = new Toast(getBaseContext());
        documentReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();

                            toast.makeText(getBaseContext(), "Document uploaded successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            toast.makeText(getBaseContext(), "can't upload", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(container, "check your connection...", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                progressDialog.dismiss();

            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Bitmap licenseImage = null;
                try {
                    licenseImage = MediaStore.Images.Media.getBitmap(getContentResolver(), document);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    licenseImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    licenseImage.recycle();
                    byte[] imageBytes = baos.toByteArray();
                    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    Log.d("size", imageBytes.length + "");
                    params.put("image", imageString);

                    params.put("imageName", imagename);
                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    params.put("id", preferences.getString("id", ""));


                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                imagename = "";
                insurance_layout.setVisibility(View.GONE);
                break;
            case 1:
                imagename = "profile";
                insurance_layout.setVisibility(View.GONE);
                break;
            case 2:
                imagename = "adhar";
                insurance_layout.setVisibility(View.GONE);
                break;
            case 3:
                imagename = "insurance";
                insurance_layout.setVisibility(View.VISIBLE);
                break;
            case 4:
                imagename = "license";
                insurance_layout.setVisibility(View.GONE);
                break;
            case 5:
                imagename = "rc";
                insurance_layout.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
