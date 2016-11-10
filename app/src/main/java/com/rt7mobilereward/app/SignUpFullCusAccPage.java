package com.rt7mobilereward.app;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpFullCusAccPage extends AppCompatActivity {

   private EditText firstNameSuf, lastNameSuf, address1Suf, address2Suf, citySuf, stateSuf, zipCodeSuf, countrySuf, phoneSuf, dateSuf, emailSuf, passSuf, cardSuf;
    private Button submitSuf;
    private String firstName = "", lastName ="", address1 ="", address2 ="", city ="", state ="", zipCode ="", country ="",dob ="",email ="",pass = "", card ="";
    private String phone = "";
    final Calendar myCalendar = Calendar.getInstance();
    private String lastChar = " ";
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_full_cus_acc_page);


        firstNameSuf = (EditText)findViewById(R.id.firstname_signupfull);
        lastNameSuf =(EditText)findViewById(R.id.lastname_signupfull);
        address1Suf = (EditText)findViewById(R.id.addline1_signupfull);
        address2Suf = (EditText)findViewById(R.id.addline2_signupfull);
        citySuf = (EditText)findViewById(R.id.city_signupfull);
        stateSuf = (EditText)findViewById(R.id.state_signupfull);
        zipCodeSuf = (EditText)findViewById(R.id.zipcode_signupfull);
        countrySuf = (EditText)findViewById(R.id.country_signupfull);
        phoneSuf = (EditText)findViewById(R.id.phone_signupfull);
        dateSuf = (EditText)findViewById(R.id.dob_signupfull);
        emailSuf = (EditText)findViewById(R.id.email_signupfull);
        passSuf = (EditText)findViewById(R.id.password_signupfull);
        cardSuf = (EditText)findViewById(R.id.card_signupfull);

        submitSuf = (Button)findViewById(R.id.submit_signupfull);

        countrySuf.setEnabled(false);
//        phoneSuf.addTextChangedListener(new TextWatcher() {
//            String lastChar = " ";
//                                            @Override
//                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                                int digits = phoneSuf.getText().toString().length();
//                                                if (digits > 1){
//                                                    lastChar = phoneSuf.getText().toString().substring(digits-1);
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                                int digits = phoneSuf.getText().toString().length();
//                                                Log.d("LENGTH",""+digits);
//                                                if (!lastChar.equals("-")) {
//                                                    if (digits == 3 || digits == 7) {
//                                                        phoneSuf.append("-");
//                                                    }
//                                                }
//                                            }
//
//                                            @Override
//                                            public void afterTextChanged(Editable s) {
//
//                                            }
//                                        });

      //  Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        if (intent != null){
            String email_bundle = intent.getExtras().getString("Email");
            String pass_bundle = intent.getExtras().getString("Password");
            String number_bundle = intent.getExtras().getString("CardNumber");
            Log.d("Email:::::::::",email_bundle);
            Log.d("Password::::::",pass_bundle);

            emailSuf.setText(email_bundle);
            passSuf.setText(pass_bundle);
            if (number_bundle.equals("")){

                cardSuf.setText("You don't have Card");
            }else {
                cardSuf.setText(number_bundle);
            }
            emailSuf.setEnabled(false);
            passSuf.setEnabled(false);
            cardSuf.setEnabled(false);

        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        dateSuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new DatePickerDialog(SignUpFullCusAccPage.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    address1Suf.requestFocus();

            }
        });








        submitSuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstNameSuf.getText().toString();
                lastName = lastNameSuf.getText().toString();
                address1 = address1Suf.getText().toString();
                address2 = address2Suf.getText().toString();
                city = citySuf.getText().toString();
                zipCode = zipCodeSuf.getText().toString();
                state = stateSuf.getText().toString();
                phone = (phoneSuf.getText().toString());
                Log.d("Phone Number::", String.valueOf(phone));
                country = "United States";
                email = emailSuf.getText().toString();
                pass = passSuf.getText().toString();
                dob = dateSuf.getText().toString();
                card = cardSuf.getText().toString();
                int lengthOfPhone = Double.valueOf(phone).toString().length();

                if (card.equals("")){
                    if (firstName.equals("") || lastName.equals("")|| address1.equals("") || city.equals("") || zipCode.equals("") || state.equals("")|| country.equals("") ||
                            lengthOfPhone < 0 || lengthOfPhone < 10 || email.equals("") || dob.equals("") || pass.equals("")){
                         if (lengthOfPhone != 10){
                             showToast("Enter the phone number without country code");
                         }else {
                             showToast("Enter All the Mandatory Fields");
                         }
                    }else {
                        callEnrollRequest(firstName,lastName,address1,address2,city,
                                state, zipCode,phone,card, email, pass, dob);


                    }
                }else {
                    if (firstName.equals("") || lastName.equals("")|| address1.equals("") || city.equals("") || zipCode.equals("") || state.equals("")|| country.equals("") ||
                            lengthOfPhone < 0 || lengthOfPhone < 10 || email.equals("") || dob.equals("") || pass.equals("")){
                        if (lengthOfPhone != 10){
                            showToast("Enter the phone number without country code");
                        }else {
                            showToast("Enter All the Mandatory Fields");
                        }
                    }else {
                        String cardName = "";
                        callEnrollRequest(firstName,lastName,address1,address2,city,
                                state, zipCode,phone, cardName, email, pass, dob);

                    }
                }
            }
        });
    }




            private void callEnrollRequest(String firstName, String lastName,String address1,
                                   String address2,String city, String state, String zipcode,
                                   String phone,String card, String email, String pass, String date){

        String fullDate = date.concat(" 00:00:00");
        Log.d("Full Date:",fullDate);

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


             if (error != null )
                {
                   // && error.toString().length() > 0
                    String LoginError = error.toString();
                   // String errordata = error.getMessage().toString();
                   // Log.d("TheMessagefromServer",errordata);
                    Log.d("Login Error Details:", LoginError);
                    Log.d("Login Error::", String.valueOf(error.networkResponse));

                    //int  statusCode = error.networkResponse.statusCode;

                    NetworkResponse networkResponse = error.networkResponse;
                    int statusCode = error.networkResponse.statusCode;
                    //  NetworkResponse networkResponse = error.networkResponse;
                    Log.d("testerror", ":::" + statusCode + "::::" + networkResponse.data);
                    Log.d("testerror", ":::" + statusCode + "::::" + networkResponse);

                    if (networkResponse != null) {
                      //  int statusCode = error.networkResponse.statusCode;
                        //  NetworkResponse networkResponse = error.networkResponse;
                        Log.d("testerror", ":::" + statusCode + "::::" + networkResponse.data);
                        Log.d("testerror", ":::" + statusCode + "::::" + networkResponse);

                        if (String.valueOf(networkResponse.statusCode) == "O017") {
                            showToast("Account created successfully !! But failed to update profile, you can update it later");
                        } else if (networkResponse.statusCode == 500){
                            if(error.networkResponse.data!=null) {
                                try {
                                   String body = new String(error.networkResponse.data,"UTF-8");
                                    Log.d("Boby:::::::::",body);
                                    showToast("Your Account is created, but unable to update your profile, You Can Update it later");
                                    Intent intent = null;
                                    intent = new Intent(SignUpFullCusAccPage.this,LoginPage.class);
                                    startActivity(intent);
                                    finish();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }

                          //  showDialog("500 Server Error", "Try Again", "OK", null, null, null);
                        }else
                        {
                            showToast("Other Error");
                        }
                    }
                }else {
                 showToast("Server Error, Try later !!");
             }
            }

        };

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {

                        Log.d("Response Looking:::",response.toString());
                        JSONObject jsonObjectResponse = new JSONObject(response);
                        token = jsonObjectResponse.getString("at");
                        Log.d("AT::::::::::",token);
                        JSONObject firstObject = jsonObjectResponse.getJSONObject("all");
                        int jsonResponse = firstObject.getInt("statusCode");
                        if (jsonResponse == 0) {

                            JSONObject jsonAnother = firstObject.getJSONObject("data");
                            JSONObject jsonOther = jsonAnother.getJSONObject("user");
                            String emailintent = jsonAnother.getString("email");
                            String Name = jsonOther.getString("firstname");
                            Log.d("Response Value:::::::", response);
                            Log.d("StatusCode:::::::", String.valueOf(jsonResponse));
                            Log.d("Name", Name);

                            Intent intent = new Intent(SignUpFullCusAccPage.this, MainActivity.class);
                            intent.putExtra("Token",token);
                            intent.putExtra("Email",emailintent);
                            startActivity(intent);

                        } else {


                            Log.d("Response Value:::::::", response.toString());
//
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Response is Null::::", "Response is Null");
                }
            }
        };

//

        EnrollRequest enrollRequest = new EnrollRequest(firstName, lastName,address1,
                address2,city, state, zipcode, phone, card, email, pass, fullDate, responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpFullCusAccPage.this);
        requestQueue.add(enrollRequest);

    }

    private void showToast(String message){
        Toast.makeText(SignUpFullCusAccPage.this,message,Toast.LENGTH_LONG).show();

    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
       // SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateSuf.setText(dateFormat.format(myCalendar.getTime()));

    }

    public void showDialog(String title, CharSequence message, String positiveButton, String negativeButton, final Intent positiveIntent , final Intent negativeIntent ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpFullCusAccPage.this, R.style.AppCompatAlertDialogStyle);
        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        if (positiveButton!=null)
        {

            if (positiveIntent!=null){
                builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(positiveIntent);
                    }
                });}
            else {
                builder.setPositiveButton(positiveButton, null);
            }
        }
        if (negativeButton!=null) {
            if (negativeIntent!=null){
                builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(negativeIntent);
                    }
                });}
            else {
                builder.setNegativeButton(negativeButton, null);
            }
        }
        builder.show();
    }
}

