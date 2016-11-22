package com.rt7mobilereward.app;

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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class EditPersonalInfoPage extends AppCompatActivity {

    private EditText firstNameEpi, lastNameEpi, address1Epi, address2Epi, cityEpi, stateEpi, zipCodeEpi, countryEpi, phoneEpi, dateEpi, emailEpi, passEpi, cardEpi;
    private Button updateEpi;
    private String firstName = "", lastName ="", address1 ="", address2 ="", city ="", state ="", zipCode ="", country ="",dob ="",email ="",pass = "", card ="";
    private String phone = "";
    final Calendar myCalendar = Calendar.getInstance();
    private String lastChar = " ";
    private static String token;
    private static String getTokenValue;

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

                token = getEditInfo.getExtras().getString("EditInfoToken");
                final ProgressDialog progressbar;

                progressbar = new ProgressDialog(EditPersonalInfoPage.this);
                progressbar.setTitle("Please Wait!!");
                progressbar.setMessage("Logging In");
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


                String tokenvalue = "rt7login auth=";
                String tokenString = tokenvalue.concat(token);
                GetUserRequest getUserRequest = new GetUserRequest(tokenString, responseListener, errorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(EditPersonalInfoPage.this);
                requestQueue.add(getUserRequest);
                // String va = loginRequest.getHeaderFile();
                Log.d("The Value back:::","This is nothing");
            }
        }else
         {
             Toast.makeText(this,"No Internet Connection !!",Toast.LENGTH_LONG);

        }


        updateEpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
