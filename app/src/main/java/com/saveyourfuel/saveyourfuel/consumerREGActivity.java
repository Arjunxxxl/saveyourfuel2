package com.saveyourfuel.saveyourfuel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class consumerREGActivity extends AppCompatActivity implements View.OnClickListener {

    Button reg;
    TextInputEditText name, dob, phone, email, password, repass;
    TextInputLayout nameL, dobL, phoneL, emailL, passwordL, repassL;
    android.support.v7.widget.Toolbar toolbar;
    private DatePickerDialog.OnDateSetListener  mDateSetListener;
    LinearLayout dateLayout;
    ImageView datepick;
    String dateString="";

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
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_consumer_reg);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("");


        setView();
        setListeners();
        initError();


    }

    void initError() {
        nameL.setError("This field cannot be empty");
        dobL.setError("This field cannot be empty");
        phoneL.setError("This field cannot be empty");
        emailL.setError("This field cannot be empty");
        passwordL.setError("This field cannot be empty");
        repassL.setError("This field cannot be empty");


    }

    void setView() {
        name = findViewById(R.id.nameReg);
        dob = findViewById(R.id.dobReg);
        dob.setOnClickListener(this);

        datepick = findViewById(R.id.datepicker);
        datepick.setOnClickListener(this);

        phone = findViewById(R.id.phoneReg);
        email = findViewById(R.id.emailReg);
        password = findViewById(R.id.passReg);
        repass = findViewById(R.id.repassReg);
        reg = findViewById(R.id.register);
        dateLayout = findViewById(R.id.reg_date_layout);
        dateLayout.setOnClickListener(this);
        nameL = findViewById(R.id.nameRegL);
        dobL = findViewById(R.id.dobRegL);

        phoneL = findViewById(R.id.phoneRegL);
        emailL = findViewById(R.id.emailRegL);
        passwordL = findViewById(R.id.passRegL);
        repassL = findViewById(R.id.repassRegL);


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String dateS = date+"";
                String monthS = (month+1)+"";

                if(dateS.length()<2){
                    dateS="0"+dateS;
                }
                if(monthS.length()<2){
                    monthS="0"+monthS;
                }

                dateString = year+ "/"+monthS+ "/"+dateS;

                dob.setText(dateString);
            }
        };
    }



    boolean checkName = false, checkDob = false, checkEmail = false, checkPassword = false, checkPhone = false;

    void setListeners() {
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
                if (s.toString().isEmpty()) {
                    nameL.setError("this field cannot be empty");
                    checkName = false;
                } else {
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
                if (s.toString().isEmpty()) {
                    dobL.setError("this field cannot be empty");

                    checkDob = false;
                } else {

                    dobL.setErrorEnabled(false);
                    checkDob = true;


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


                if (s.toString().length() != 10) {
                    phoneL.setError("invalid format");
                    checkPhone = false;
                } else {
                    try {
                        Long t = Long.parseLong(s.toString());
                        phoneL.setErrorEnabled(false);
                        checkPhone = true;

                    } catch (Exception e) {
                        phoneL.setError("invalid format");
                        checkPhone = false;
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
                if (s.toString().isEmpty()) {

                    emailL.setError("this field cannot be empty");
                    checkEmail = false;
                }

                String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                java.util.regex.Matcher m = p.matcher(s.toString());
                if (!m.matches()) {
                    emailL.setError("invalid format");
                    checkEmail = false;
                } else {
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
                String ePattern = "^(?=.*?[a-z])(?=.*?[0-9])(?=\\S+$).{8,}$";
                java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                java.util.regex.Matcher m = p.matcher(s.toString());
                if (s.toString().isEmpty()) {
                    passwordL.setError("password cannot be empty");
                    checkPassword = false;
                }
                else if(!m.matches()){
                    passwordL.setError("Minimum eight characters, at least one letter and one number:");
                    checkPassword = false;

                }
                else{
                    passwordL.setErrorEnabled(false);
                    checkPassword = true;
                }


                if (!s.toString().contentEquals(repass.getText().toString()))
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


                if (!s.toString().contentEquals(password.getText().toString()))
                    repassL.setError("password doesn't match");
                else
                    repassL.setErrorEnabled(false);
            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                postData();
                break;

            case R.id.datepicker:
            case R.id.dobReg:
                Calendar cal = Calendar.getInstance();
                int Year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Date = cal.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(
                        consumerREGActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,Year,Month,Date);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;




        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(consumerREGActivity.this, selectTyleActivity.class);
        startActivity(i);
        this.finish();
    }

    void postData() {

        final String named, dobd, phoned, emaild, passwordd, repassd;
        named = name.getText().toString();
        dobd = dob.getText().toString();
        phoned = phone.getText().toString();
        emaild = email.getText().toString();
        passwordd = password.getText().toString();
        repassd = repass.getText().toString();



        if (repassd.contentEquals(passwordd)  && checkEmail && checkName && checkDob && checkPassword && checkPhone) {

            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "http://139.59.29.124:3000/register";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                int code = Integer.parseInt(response);
                                if (code == 1062) {
                                    Snackbar.make(findViewById(android.R.id.content), "email or phone already exist", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    email.setText("");
                                    phone.setText("");
                                }
                                if (code == 900) {
                                    Toast.makeText(getBaseContext(), "SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(consumerREGActivity.this, loginActivity.class);
                                    consumerREGActivity.this.startActivity(i);
                                    consumerREGActivity.this.finish();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), "An error occurred. Please try again ", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    Intent i = new Intent(consumerREGActivity.this, loginActivity.class);
                                    consumerREGActivity.this.startActivity(i);
                                    consumerREGActivity.this.finish();
                                }
                            } catch (Exception e) {
                                Snackbar.make(findViewById(android.R.id.content), "An error occurred. Please try again ", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.check_your_connection, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }

            }) {
                //adding parameters to the request
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", named);
                    params.put("email", emaild);
                    params.put("dob", dobd);
                    params.put("phone", phoned);
                    params.put("password", passwordd);

                    return params;
                }
            };
            queue.add(stringRequest);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Fill all the fields", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }
    }


}
