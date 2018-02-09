package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class loginActivity extends AppCompatActivity implements  View.OnClickListener{

//    CheckBox cemail,cpass;
    TextView reg;
    TextInputLayout temail;
    Button login;
    TextInputEditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        temail = findViewById(R.id.editText4);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        reg = findViewById(R.id.textView4);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.textView4:
                Intent i = new Intent(loginActivity.this, selectTyleActivity.class);
                startActivity(i);
                break;

            case R.id.loginButton:
                checkLogin();
                break;
        }
    }

    private void checkLogin(){

        String passwordText, emailText;
        login.setEnabled(false);
        passwordText = password.getText().toString();
        emailText = email.getText().toString();
        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "http://139.59.29.124:3000/authentication?id="+emailText+"&pwd="+passwordText;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","response "+response);
                if(response.contentEquals("800")){
                    login.setEnabled(true);
                    startActivity(new Intent("com.saveyourfuel.saveyourfuel.home"));
                }
                else{
                    Toast.makeText(getBaseContext(),"WRONG CREDENTIALS!",Toast.LENGTH_SHORT).show();
                    login.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
                Toast.makeText(getBaseContext(),"check your connection...",Toast.LENGTH_SHORT).show();
                login.setEnabled(true);
            }
        });
        queue.add(stringRequest);
//        StringRequest stringRequest = new Str
    }
}
