package com.rt7mobilereward.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SettingsPage extends AppCompatActivity {

    LinearLayout EditPersonalInfo;
    LinearLayout PaymentMethods;
    LinearLayout Messages;
    LinearLayout Receipts;
    LinearLayout Help;
    LinearLayout TermsOfUse;
    LinearLayout PrivacyStatement;
    Button signOut;
    TextView viewNameSetttings;
    TextView viewEmailSettings;
    ImageView showPhoto;

    String userNameSettings;
    String usernameEmailSettings;
    String tokenSettings;
    ImageView userPhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View view = getLayoutInflater().inflate(R.layout.title_bar, null);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);



        TextView Title = (TextView) view.findViewById(R.id.myTitle);
        Title.setText(R.string.str_settings_btn_text);

//
        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Intent settingsIntent = getIntent();

//        intentSettings.putExtra("usernameRewards",userNameRewards);
//        intentSettings.putExtra("userEmailRewards",userEmailRewards);
//        intentSettings.putExtra("RewardToken",rewardtoken);
//        intentSettings.putExtra("CardNumber",rewardCardNumber);


        EditPersonalInfo = (LinearLayout)findViewById(R.id.edit_personal_info_settings);
        PaymentMethods = (LinearLayout)findViewById(R.id.payment_methods_settings);
        Messages = (LinearLayout)findViewById(R.id.messages_settings);
        Receipts = (LinearLayout)findViewById(R.id.receipts_settings);
        Help = (LinearLayout)findViewById(R.id.help_settings);
        TermsOfUse = (LinearLayout)findViewById(R.id.terms_of_use_settings);
        PrivacyStatement = (LinearLayout)findViewById(R.id.privacy_statement_settings);
        signOut = (Button)findViewById(R.id.sign_out_settings);
        viewNameSetttings = (TextView)findViewById(R.id.show_name_settings);
        viewEmailSettings = (TextView)findViewById(R.id.show_email_settings);
        showPhoto = (ImageView)findViewById(R.id.show_photo_settings);



        if (settingsIntent.getExtras() !=null){
            userNameSettings = settingsIntent.getExtras().getString("usernameRewards");
            usernameEmailSettings = settingsIntent.getExtras().getString("userEmailRewards");
            tokenSettings = settingsIntent.getExtras().getString("RewardToken");
            Log.d("Name",userNameSettings);
            Log.d("Email",usernameEmailSettings);
            Log.d("RewardToken",tokenSettings);
        }
        viewNameSetttings.setText(userNameSettings);
        viewEmailSettings.setText(usernameEmailSettings);



        EditPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent editPersonalInfoIntent = new Intent(SettingsPage.this,EditPersonalInfoPage.class);
                editPersonalInfoIntent.putExtra("EditInfoToken",tokenSettings);
                startActivity(editPersonalInfoIntent);
            }
        });



        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

                            if (error != null) {
                                //  int statusCode = error.networkResponse.statusCode;
                                //  NetworkResponse networkResponse = error.networkResponse;
                                Log.d("testerror", ":::" + statusCode + "::::" + networkResponse.data);
                                Log.d("testerror", ":::" + statusCode + "::::" + networkResponse);

                                if (String.valueOf(networkResponse.statusCode) == "O017") {
                                   // showToast("Account created successfully !! But failed to update profile, you can update it later");
                                } else if (networkResponse.statusCode == 500){
                                    if(error.networkResponse.data!=null) {
                                        try {
                                            String body = new String(error.networkResponse.data,"UTF-8");
                                            Log.d("Boby:::::::::",body);
                                         //   showToast("Your Account is created, but unable to update your profile, You Can Update it later");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    //  showDialog("500 Server Error", "Try Again", "OK", null, null, null);
                                }else
                                {
                                   // showToast("Other Error");
                                    finish();
                                }
                            }
                        }else {
                         //   showToast("Server Error, Try later !!");
                            finish();
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
                                String token = jsonObjectResponse.getString("at");
                                Log.d("AT::::::::::",token);
                                JSONObject firstObject = jsonObjectResponse.getJSONObject("all");
                                int jsonResponse = firstObject.getInt("statusCode");
                                if (jsonResponse == 0) {

                                     String Message = firstObject.getString("data");
                                  //  JSONObject jsonAnother = firstObject.getJSONObject("data");
                                    Toast.makeText(SettingsPage.this, Message, Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(SettingsPage.this, WelcomePage.class);
                                   // intent.putExtra("Token",token);

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

                SignoutRequest signoutRequest = new SignoutRequest(tokenSettings, responseListener, errorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(SettingsPage.this);
                requestQueue.add(signoutRequest);


            }
        });

    }



}
