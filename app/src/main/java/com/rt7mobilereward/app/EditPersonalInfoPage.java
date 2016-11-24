package com.rt7mobilereward.app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class EditPersonalInfoPage extends AppCompatActivity {

    private EditText firstNameEpi, lastNameEpi, address1Epi, address2Epi, cityEpi, stateEpi, zipCodeEpi, countryEpi, phoneEpi, dateEpi, emailEpi, passEpi, cardEpi;
    private Button updateEpi;
    private String firstNameepi = "", lastNameepi ="", address1epi ="", address2epi ="", cityepi ="", stateepi ="", zipCodeepi ="", countryepi ="",dobepi ="",emailepi ="",passepi = "", cardepi ="";
    private String phoneepi = "";
    final Calendar myCalendarepi = Calendar.getInstance();
    private String lastChar = " ";
    private static String tokenEditInfo;
    private static String getTokenValue;
    private static String tokenString;
    private static String tokenvalue = "rt7login auth=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstNameEpi = (EditText)findViewById(R.id.firstname_editinfo);
        lastNameEpi = (EditText)findViewById(R.id.lastname_editinfo);
        address1Epi = (EditText)findViewById(R.id.addline1_editinfo);
        address2Epi = (EditText)findViewById(R.id.addline2_editinfo);
        cityEpi = (EditText)findViewById(R.id.city_editinfo);
        stateEpi = (EditText)findViewById(R.id.state_editinfo);
        zipCodeEpi = (EditText)findViewById(R.id.zipcode_editinfo);
        countryEpi = (EditText)findViewById(R.id.country_editinfo);
        phoneEpi = (EditText)findViewById(R.id.phone_editinfo);
        dateEpi = (EditText)findViewById(R.id.dob_editinfo);
        emailEpi = (EditText)findViewById(R.id.email_editinfo);
        passEpi = (EditText)findViewById(R.id.password_editinfo);
        cardEpi = (EditText)findViewById(R.id.card_editinfo);
        updateEpi = (Button)findViewById(R.id.update_editinfo);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            Intent getEditInfo = getIntent();
            if (getEditInfo.getExtras() != null){

                tokenEditInfo = getEditInfo.getExtras().getString("EditInfoToken");
                final ProgressDialog progressbar;

                progressbar = new ProgressDialog(EditPersonalInfoPage.this);
                progressbar.setTitle("Please Wait!!");
                progressbar.setMessage("Loading Update User");
                progressbar.setCancelable(false);
                progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressbar.show();




                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressbar.dismiss();
                        String LoginError = error.toString();
                        Log.d("Login Error::",LoginError);
                        android.app.AlertDialog alertConnection = new android.app.AlertDialog.Builder(
                                EditPersonalInfoPage.this).create();
                        alertConnection.setTitle("Login Failed !!");
                        alertConnection.setMessage("Try Again");
                        alertConnection.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertConnection.show();

                    }
                };


                final Response.Listener<String> responseListener = new Response.Listener<String>() {



                    @Override
                    public void onResponse(String response) {
                        progressbar.dismiss();
                        if (response != null) {
                            try {
                                Log.d("Editinfo_Look:::",response.toString());
                                JSONObject jsonObjectResponse = new JSONObject(response);
                                getTokenValue = jsonObjectResponse.getString("at");
                                Log.d("AT::::::::::",getTokenValue);
                                JSONObject firstObject = jsonObjectResponse.getJSONObject("all");
                                int jsonResponse = firstObject.getInt("statusCode");

                                if (jsonResponse == 0) {


                                    JSONObject jsonAnother = firstObject.getJSONObject("data");
                                    JSONObject jsonOther = jsonAnother.getJSONObject("user");
                                    String F_NameEpi = jsonOther.optString("firstname"," ");
                                    String S_NameEpi = " " + jsonOther.optString("lastname"," ");
                                    String NameEpi = F_NameEpi.concat(S_NameEpi);
                                    String EmailEpi = jsonOther.optString("email"," ");
                                    String CardNumberEpi = jsonOther.optString("customer_card_number"," ");
                                    String RewardBalanceEpi = jsonOther.optString("reward_balance"," ");
                                    String GiftbalanceEpi = jsonOther.optString("gift_balance"," ");
                                    String CityEpi = jsonOther.optString("city"," ");
                                    String StateEpi = jsonOther.optString("state"," ");
                                    String zipEpi = jsonOther.optString("zip"," ");
                                    String dobEpi = jsonOther.optString("date_of_birth"," ");
                                    String Address1Epi = jsonOther.optString("address1"," ");
                                    String Address2Epi = jsonOther.optString("address2"," ");
                                    String PhoneEpi = jsonOther.optString("phone"," ");

                                    Log.d("Response Value:::::::", response);
                                    Log.d("StatusCode:::::::", String.valueOf(jsonResponse));
                                    Log.d("Name", NameEpi);

                                    firstNameEpi.setText(F_NameEpi);
                                    lastNameEpi.setText(S_NameEpi);
                                    emailEpi.setText(EmailEpi);
                                    cardEpi.setText(CardNumberEpi);
                                    cityEpi.setText(CityEpi);
                                    stateEpi.setText(StateEpi);
                                    zipCodeEpi.setText(zipEpi);
                                    dateEpi.setText(dobEpi);
                                    address1Epi.setText(Address1Epi);
                                    address2Epi.setText(Address2Epi);
                                    phoneEpi.setText(PhoneEpi);




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



                tokenString = tokenvalue.concat(tokenEditInfo);
                GetUserRequest getUserRequest = new GetUserRequest(tokenString, responseListener, errorListener);
                RequestQueue requestQueueUser = Volley.newRequestQueue(EditPersonalInfoPage.this);
                requestQueueUser.getCache().clear();
                requestQueueUser.add(getUserRequest);
                // String va = loginRequest.getHeaderFile();
                Log.d("The Value back:::","This is nothing");
            }
        }else
         {
             Toast.makeText(this,"No Internet Connection !!",Toast.LENGTH_LONG);

        }



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarepi.set(Calendar.YEAR, year);
                myCalendarepi.set(Calendar.MONTH, monthOfYear);
                myCalendarepi.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        dateEpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(EditPersonalInfoPage.this, date, myCalendarepi
                        .get(Calendar.YEAR), myCalendarepi.get(Calendar.MONTH),
                        myCalendarepi.get(Calendar.DAY_OF_MONTH)).show();
                address1Epi.requestFocus();

            }
        });














        updateEpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final ProgressDialog progressbar;

                progressbar = new ProgressDialog(EditPersonalInfoPage.this);
                progressbar.setTitle("Please Wait!!");
                progressbar.setMessage("Loading Update User");
                progressbar.setCancelable(false);
                progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressbar.show();
                dobepi= dateEpi.getText().toString().concat(" 00:00:00");
                firstNameepi = firstNameEpi.getText().toString();
                lastNameepi = lastNameEpi.getText().toString();
                cardepi = cardEpi.getText().toString();
                emailepi = emailEpi.getText().toString();
                address1epi = address1Epi.getText().toString();
                address2epi = address2Epi.getText().toString();
                cityepi = cityEpi.getText().toString();
                stateepi = stateEpi.getText().toString();
                zipCodeepi = zipCodeEpi.getText().toString();
                phoneepi = phoneEpi.getText().toString();





                Response.ErrorListener updateEpierrorListener = new Response.ErrorListener() {
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

                                } else if (networkResponse.statusCode == 500){
                                    if(error.networkResponse.data!=null) {
                                        try {
                                            String body = new String(error.networkResponse.data,"UTF-8");
                                            Log.d("Boby:::::::::",body);
                                            finish();

                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    //  showDialog("500 Server Error", "Try Again", "OK", null, null, null);
                                }else
                                {
                                    Toast.makeText(EditPersonalInfoPage.this,"Other Error",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }else {
                            Toast.makeText(EditPersonalInfoPage.this,"Server Error",Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                };


                final Response.Listener<String> updateEpiresponseListener = new Response.Listener<String>() {



                    @Override
                    public void onResponse(String response) {
                        progressbar.dismiss();
                        if (response != null) {
                            try {
                                Log.d("Editinfo_Look:::",response.toString());
                                JSONObject jsonObjectResponse = new JSONObject(response);
                                getTokenValue = jsonObjectResponse.getString("at");
                                Log.d("AT::::::::::",getTokenValue);
                                JSONObject firstObject = jsonObjectResponse.getJSONObject("all");
                                int jsonResponse = firstObject.getInt("statusCode");

                                if (jsonResponse == 0) {


                                    String jsonAnother = firstObject.getString("data");
                                    Log.d("Sucess in Update::",jsonAnother);
                                    Toast.makeText(EditPersonalInfoPage.this,"Updated Successfully!!", Toast.LENGTH_LONG).show();
                                    onBackPressed();

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







                tokenString = tokenvalue.concat(tokenEditInfo);
                Log.d("TokenValueEdit",tokenString);
                Log.d("firstName",firstNameepi);
                Log.d("firstName",lastNameepi);
                Log.d("firstName",cardepi);
                Log.d("firstName",emailepi);
                Log.d("firstName",cityepi);
                Log.d("firstName", zipCodeepi);
                Log.d("firstName",phoneepi);
                Log.d("firstName",dobepi);
                final JSONObject jsonBody;

                try {
                     jsonBody = new JSONObject(" {\"customer_card_number\":\""+cardepi+"\"," +
                            "\"name\":\""+firstNameepi+"\"," +
                            "\"firstname\":\""+firstNameepi+"\"," +
                            "\"lastname\":\""+lastNameepi+"\"," +
                            "\"email\":\""+emailepi+"\"," +
                            "\"address1\":\""+address1epi+"\"," +
                            "\"address2\":\""+address2epi+"\"," +
                            "\"city\":\""+cityepi+"\"," +
                            "\"state\":\""+stateepi+"\"," +
                            "\"zip\":\""+zipCodeepi+"\"," +
                            "\"phone\":\""+phoneepi+"\"," +
                            "\"date_of_birth\":\""+dobepi+"\"}}");
                    Log.d("JSON Object",jsonBody.toString());
                    UpdateUserRequest updateUserRequest = new UpdateUserRequest(tokenString, jsonBody,updateEpiresponseListener, updateEpierrorListener );
//                UpdateUserRequest updateUserRequest = new UpdateUserRequest(tokenString, firstNameepi,lastNameepi,
//                        cardepi, emailepi, address1epi, address2epi, cityepi, stateepi, zipCodeepi,phoneepi, dobepi
//                        ,updateEpiresponseListener, updateEpierrorListener);
                    RequestQueue requestQueue = Volley.newRequestQueue(EditPersonalInfoPage.this);
                    requestQueue.getCache().clear();
                    requestQueue.add(updateUserRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //{"user":
                }






            }
        });

    }



    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        // SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateEpi.setText(dateFormat.format(myCalendarepi.getTime()));

    }

}
