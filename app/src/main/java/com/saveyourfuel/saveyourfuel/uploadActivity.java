package com.saveyourfuel.saveyourfuel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Debug;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Pick_image1) {
            String path = getRealPathFromURI(data.getData());
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
            t1.setText(path);
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image2) {
            String path2 = getRealPathFromURI(data.getData());
            Toast.makeText(getApplicationContext(), path2, Toast.LENGTH_LONG).show();
            t2.setText(path2);
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image3) {
            String path3 = getRealPathFromURI(data.getData());
            Toast.makeText(getApplicationContext(), path3, Toast.LENGTH_LONG).show();
            t3.setText(path3);
        }

        if (resultCode == RESULT_OK && requestCode == Pick_image4) {
            String path4 = getRealPathFromURI(data.getData());
            Toast.makeText(getApplicationContext(), path4, Toast.LENGTH_LONG).show();
            t4.setText(path4);
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
}
