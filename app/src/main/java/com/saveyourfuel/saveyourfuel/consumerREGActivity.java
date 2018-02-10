package com.saveyourfuel.saveyourfuel;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class consumerREGActivity extends AppCompatActivity implements View.OnClickListener{

    Button reg;
    TextInputEditText name, dob,address, phone,email,password,repass;
    TextInputLayout nameL,dobL,addressL,phoneL,emailL,passwordL,repassL;

    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_reg);
//
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Consumer Registration");


        setView();
        setListeners();

    }

    void setView(){
        name = findViewById(R.id.nameReg);
        dob = findViewById(R.id.dobReg);
        address = findViewById(R.id.addressReg);
        phone = findViewById(R.id.phoneReg);
        email = findViewById(R.id.emailReg);
        password = findViewById(R.id.passReg);
        repass = findViewById(R.id.repassReg);
        reg = findViewById(R.id.register);

        nameL = findViewById(R.id.nameRegL);
        dobL = findViewById(R.id.dobRegL);
        addressL = findViewById(R.id.addressRegL);
        phoneL = findViewById(R.id.phoneRegL);
        emailL = findViewById(R.id.emailRegL);
        passwordL = findViewById(R.id.passRegL);
        repassL = findViewById(R.id.repassRegL);
    }

    boolean checkName=false,checkAddress=false, checkDob=false, checkEmail=false, checkPassword=false, checkPhone=false;

    void setListeners(){
        reg.setOnClickListener(this);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                    nameL.setError("this field cannot be empty");
                else{
                    nameL.setErrorEnabled(false);
                    checkName = true;
                }

            }
        });

        dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                    dobL.setError("this field cannot be empty");
                else{
                    String str[] = s.toString().split(Pattern.quote("/"));
                    int tr =1;
                    try{
                        if(!(Integer.parseInt(str[0])<=31))
                            tr=0;
                        else if(!(Integer.parseInt(str[1])<=12))
                            tr=0;
                        else if(!(str[2].length()==4))
                            tr=0;
                        if(tr==0){
                            dobL.setError("invalid format");
                        }
                        else{
                            dobL.setErrorEnabled(false);
                            checkDob=true;
                        }


                    }catch (Exception e){
                        dobL.setError("invalid format");
                    }


                }

            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                    addressL.setError("this field cannot be empty");
                else {
                    addressL.setErrorEnabled(false);
                    checkAddress = true;
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                    phoneL.setError("this field cannot be empty");

                if(s.toString().length()!=10){
                    phoneL.setError("invalid format");
                }
                else{
                    try{
                        Long t = Long.parseLong(s.toString());
                        phoneL.setErrorEnabled(false);
                        checkPhone = true;

                    }
                    catch (Exception e){
                        phoneL.setError("invalid format");
                    }

                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                    emailL.setError("this field cannot be empty");

                String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                java.util.regex.Matcher m = p.matcher(s.toString());
                if(!m.matches()){
                    emailL.setError("invalid format");
                }
                else{
                    emailL.setErrorEnabled(false);
                    checkEmail = true;
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                    passwordL.setError("password cannot be empty");
                else{
                    passwordL.setErrorEnabled(false);
                    checkPassword = true;
                }


                if(!s.toString().contentEquals(repass.getText().toString()))
                    repassL.setError("password doesn't match");
                else
                    repassL.setErrorEnabled(false);
            }
        });
        repass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().contentEquals(password.getText().toString()))
                    repassL.setError("password doesn't match");
                else
                    repassL.setErrorEnabled(false);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.register:
                postData();
                break;
        }
    }


    void postData(){

        final String named, dobd, addressd, phoned,emaild,passwordd,repassd;
        named = name.getText().toString();
        dobd = dob.getText().toString();
        addressd = address.getText().toString();
        phoned = phone.getText().toString();
        emaild = email.getText().toString();
        passwordd = password.getText().toString();
        repassd = repass.getText().toString();

        final String dobSubstring[] = dobd.split(Pattern.quote("/"));

        if(repassd.contentEquals(passwordd) && checkAddress&& checkEmail && checkName && checkDob && checkPassword && checkPhone){

            RequestQueue queue= Volley.newRequestQueue(this);

            String url = "http://139.59.29.124:3000/register";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                int code = Integer.parseInt(response);
                                if(code==1062){
                                    Toast.makeText(getBaseContext(),"email or phone already exist",Toast.LENGTH_SHORT).show();
                                    email.setText("");
                                    phone.setText("");
                                }
                                if(code==900){
                                    Toast.makeText(getBaseContext(),"SUCCESSFULLY REGISTERED",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(consumerREGActivity.this,loginActivity.class);
                                    consumerREGActivity.this.startActivity(i);
                                    consumerREGActivity.this.finish();
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getBaseContext(),"Unknown error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext(),"Check your connection..",Toast.LENGTH_SHORT).show();
                }

            }) {
                //adding parameters to the request
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", named);
                    params.put("email", emaild);
                    params.put("dob", dobSubstring[2]+dobSubstring[1]+dobSubstring[0]);
                    params.put("address", addressd);
                    params.put("phone", phoned);
                    params.put("password", passwordd);

                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }
}
