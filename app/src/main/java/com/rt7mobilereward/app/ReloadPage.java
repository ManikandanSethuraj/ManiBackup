package com.rt7mobilereward.app;


import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

public class ReloadPage extends AppCompatActivity implements FragReloadAmount.onSendData,FragCreditCardRelaod.onSendCreditCardData
{
    FragReloadAmount fragReloadAmount;
    FragCreditCardRelaod fragCreditCardRelaod;
    Button ReloadClick;
    TextView textViewReload;
    TextView textViewRoload2;
    private String tokenRelaod;
    private String rewardBalance;
    private String timeValue;
    private String cardNumber;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String giftcard;
    LinearLayout clickReloadLayout;
    LinearLayout clickForCreditCard;
    TextView showRelaodData;
    ObjectAnimator moveDownAnim;
    ObjectAnimator moveUpAnim;
    ObjectAnimator moveUpFirst;

    private static String creditCardNumber ="";
    private static int monthcc = 0;
    private static int yearcc = 0;
    private static String cvccc = "";
    private static Double amountToReload = 0.0;
    private JSONObject jsonBodycredit;
    private JSONObject jsonBodyuser;
    private String jsonBody;

    private String giftbalanceShow;
    private String transactionIdShow;
    private String authCodeShow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


//        View view = getLayoutInflater().inflate(R.layout.title_bar, null);
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.MATCH_PARENT,
//                Gravity.CENTER);
//        getSupportActionBar().setCustomView(view,params);
//        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Intent reloadIntent = getIntent();
        if (reloadIntent != null){

        }

        textViewReload = (TextView)findViewById(R.id.reload_detail_text);
        textViewRoload2 = (TextView)findViewById(R.id.reload_detail_text2);
        clickReloadLayout = (LinearLayout) findViewById(R.id.reload_amount_click);
        clickForCreditCard = (LinearLayout)findViewById(R.id.click_for_creditcard);
        fragReloadAmount = new FragReloadAmount();
        fragCreditCardRelaod = new FragCreditCardRelaod();
        showRelaodData = (TextView)findViewById(R.id.reload_amount_getdata);
        ReloadClick = (Button)findViewById(R.id.reload_amount_button);
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.commit();
        if (reloadIntent != null) {

            moveDownAnim = ObjectAnimator.ofFloat(clickForCreditCard, "translationY", 0.F, -70);
            moveUpAnim = ObjectAnimator.ofFloat(clickForCreditCard, "translationY", 100, 100);
            moveUpFirst = ObjectAnimator.ofFloat(clickForCreditCard,"translationY", 0.F, -70 );

            rewardBalance = reloadIntent.getExtras().getString("BalanceValue");
            timeValue = reloadIntent.getExtras().getString("Time");
            cardNumber = reloadIntent.getExtras().getString("CardNumber");
            giftcard = reloadIntent.getExtras().getString("CardNumber");
            email = reloadIntent.getExtras().getString("Email");
            lastName = reloadIntent.getExtras().getString("LastName");
            firstName = reloadIntent.getExtras().getString("FirstName");
            phone = reloadIntent.getExtras().getString("Phone");
            tokenRelaod = reloadIntent.getExtras().getString("Token");
            if (cardNumber != null){
                if (rewardBalance != null){
                    cardNumber = "My Card(" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)) + ")";
                    Log.d("Last Values::", cardNumber);
                    textViewReload.setText(cardNumber);
                    Log.d("Time Value", timeValue);
                    timeValue = timeValue.substring(13, timeValue.length());
                    timeValue = timeValue.substring(0, timeValue.length() - 4);
                    timeValue = rewardBalance + " as of " + timeValue + " ago";
                    Log.d("Time Value", timeValue);
                    textViewRoload2.setText(timeValue);
                }else {
                    cardNumber = "My Card(" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)) + ")";
                    Log.d("Last Values::", cardNumber);
                    textViewReload.setText(cardNumber);
                    Log.d("Time Value", timeValue);
                    timeValue = timeValue.substring(13, timeValue.length());
                    timeValue = timeValue.substring(0, timeValue.length() - 4);
                    timeValue = rewardBalance + " as of " + timeValue + " ago";
                    Log.d("Time Value", timeValue);
                    textViewRoload2.setText(timeValue);
                }


            }else {
                textViewReload.setText("You do not have card");
                textViewRoload2.setText(" ");
            }

           moveUpFirst.start();

        }

        clickReloadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

              if (fragReloadAmount.isHidden()){

                  moveUpAnim.start();
                  fm.beginTransaction().setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down)
                          .replace(R.id.add_amount_in_reload,fragReloadAmount).show(fragReloadAmount)
                          .commit();
              }else
              {
                  fm.beginTransaction()
                          .hide(fragReloadAmount)
                          .commit();
                  moveDownAnim.start();
              }

            }
        });

        clickForCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm1 = getSupportFragmentManager();
                if (fragCreditCardRelaod.isHidden()){

                    fm1.beginTransaction().setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down)
                            .replace(R.id.replace_creditcard,fragCreditCardRelaod).show(fragCreditCardRelaod)
                            .commit();

                }else {
                    fm1.beginTransaction()
                            .hide(fragCreditCardRelaod)
                            .commit();

                }
            }
        });

        ReloadClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 if(amountToReload != 0.0){
                     if (creditCardNumber != null && cvccc != null && monthcc != 0 && yearcc != 0){
                         if (creditCardNumber.length() == 14 || creditCardNumber.length() ==15 || creditCardNumber.length() ==16){
                             if (cvccc.length() == 3){

                                 SendReloadData(creditCardNumber,cvccc,monthcc,yearcc,amountToReload,lastName, firstName, email, phone, giftcard);


                             }else {
                                 Toast.makeText(ReloadPage.this," Error !! Check your CVC Number",Toast.LENGTH_LONG).show();
                             }

                         }else{
                             Toast.makeText(ReloadPage.this," Error !! Check your Card Number",Toast.LENGTH_LONG).show();
                         }

                     }else {
                         Toast.makeText(ReloadPage.this," Enter All the Details of the Credit Card",Toast.LENGTH_LONG).show();
                     }
                 }else
                 {
                     Toast.makeText(ReloadPage.this,"Reload Amount",Toast.LENGTH_LONG).show();
                 }




            }
        });






    }

    private void SendReloadData(String ccardNumber, String CVC, int month, int year, Double amount,  String lastname, String firstname, String sendemail,
                                String phone, String card) {


        final ProgressDialog progressbar;

        progressbar = new ProgressDialog(ReloadPage.this,R.style.AppCompatAlertDialogStyle);
        progressbar.setTitle("Please Wait!!");
        progressbar.setMessage("Logging In");
        progressbar.setCancelable(false);
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.show();
        Response.ErrorListener topupgifterrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error != null )
                {
                    progressbar.dismiss();
                    // && error.toString().length() > 0
                    String LoginError = error.toString();
                    // String errordata = error.getMessage().toString();
                    // Log.d("TheMessagefromServer",errordata);
                    Log.d("Login Error Details:", LoginError);
                    Log.d("Login Error::", String.valueOf(error.networkResponse));

                    //int  statusCode = error.networkResponse.statusCode;

                    NetworkResponse networkResponse = error.networkResponse;
                   // int statusCode = error.networkResponse.statusCode;
                    //  NetworkResponse networkResponse = error.networkResponse;
                 //   Log.d("testerror", ":::" + statusCode + "::::" + networkResponse.data);
                  //  Log.d("testerror", ":::" + statusCode + "::::" + networkResponse);

                    if (networkResponse != null) {
                        //  int statusCode = error.networkResponse.statusCode;
                        //  NetworkResponse networkResponse = error.networkResponse;
                     //   Log.d("testerror", ":::" + statusCode + "::::" + networkResponse.data);
                    //    Log.d("testerror", ":::" + statusCode + "::::" + networkResponse);

                        if (String.valueOf(networkResponse.statusCode) == "I028") {



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
                            Toast.makeText(ReloadPage.this,"Server Error",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }else {
                    Toast.makeText(ReloadPage.this,"Server Error",Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        };


        final Response.Listener<String> topupgiftresponseListener = new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                progressbar.dismiss();
                if (response != null) {
                    try {
                        Log.d("Editinfo_Look:::",response.toString());
                        JSONObject jsonObjectResponse = new JSONObject(response);
                        String getTokenValue = jsonObjectResponse.getString("at");
                        Log.d("AT::::::::::",getTokenValue);
                        JSONObject firstObject = jsonObjectResponse.getJSONObject("all");
                        int jsonResponse = firstObject.getInt("statusCode");

                        if (jsonResponse == 0) {
                               JSONObject secondObject = firstObject.getJSONObject("data");
                              giftbalanceShow = secondObject.getString("gift_balance");
                            transactionIdShow = secondObject.getString("transactionId");
                            authCodeShow = secondObject.getString("authCode");

                            android.app.AlertDialog alertConnection = new android.app.AlertDialog.Builder(
                                    ReloadPage.this,R.style.AppCompatAlertDialogStyle).create();
                            alertConnection.setTitle("Transaction Success !!");
                            alertConnection.setMessage("Gift Balance: $ "+giftbalanceShow+"."+"\n"+
                                    "Transaction Id: "+transactionIdShow+"."+"\n"+
                                    "Authorization Code: "+authCodeShow+".");
                            alertConnection.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertConnection.show();

//                            String jsonAnother = firstObject.getString("data");
//                            Log.d("Sucess in Update::",jsonAnother);
//                            Toast.makeText(ReloadPage.this,"Updated Successfully!!", Toast.LENGTH_LONG).show();
//                            onBackPressed();

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










        try {
            jsonBodycredit = new JSONObject("{\"ccNum\":\""+
                    ccardNumber+"\"," +
                    "\"ccCvv\":\""+CVC+"\"," +
                    "\"expMonth\":\""+month+"\"," +
                    "\"expYear\":\""+year+"\"," +
                    "\"amount\":\""+amount+"\"}");

            jsonBodyuser = new JSONObject("{\"firstName\":\""+
                    firstname+"\"," +
                    "\"lastname\":\""+lastname+"\"," +
                    "\"email\":\""+sendemail+"\"," +
                    "\"phone\":\""+phone+"\"}");
            jsonBody = card;



            Log.d("JSON Object",jsonBody.toString());
            TopupGiftWithCreditRequest topupGiftWithCreditRequest = new TopupGiftWithCreditRequest(jsonBodycredit, jsonBodyuser, jsonBody,tokenRelaod, topupgiftresponseListener, topupgifterrorListener );
            RequestQueue requestQueue = Volley.newRequestQueue(ReloadPage.this);
            requestQueue.getCache().clear();
            requestQueue.add(topupGiftWithCreditRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void sendData(String data) {
         showRelaodData.setText(data);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .hide(fragReloadAmount)
                .commit();
        String setValueforData = "RELOAD "+data;
        ReloadClick.setText(setValueforData);
        amountToReload = Double.valueOf(data.substring(1));
        moveDownAnim.start();

    }



    @Override
    public void sendData(String cardNumber, int month, int year, String cvc) {

        creditCardNumber = cardNumber;
        monthcc = month;
        yearcc = year;
        cvccc = cvc;
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .hide(fragCreditCardRelaod)
                .commit();

    }
}
