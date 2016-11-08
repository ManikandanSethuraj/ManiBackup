package com.rt7mobilereward.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpCardDetail extends AppCompatActivity {

    EditText cardDetailSignUp;
    Button checkCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_ACTION_BAR);

       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_sign_up_card_detail);
      //  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);


//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setCustomView(R.layout.title_bar);
//
//        View customActionBarView = actionBar.getCustomView();
        View view = getLayoutInflater().inflate(R.layout.title_bar, null);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);


        TextView Title = (TextView) view.findViewById(R.id.myTitle);
        Title.setText(R.string.str_sign_up);

//
       getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(true);




        cardDetailSignUp = (EditText)findViewById(R.id.cardnumber_detail_edit);
        checkCard = (Button)findViewById(R.id.btn_check_card);


        // Clicked when the User says 'YES' he has a Card with him.
        checkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cardNumber = cardDetailSignUp.getText().toString();


                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String LoginError = error.toString();
                        Intent intent = new Intent(SignUpCardDetail.this,SignUpShortPage.class);
                        showDialog("Card Not Found!!","Want to Enter Again?","OK","Sign Up Without Card",null,intent);
                    }
                };

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            int jsonResponse = jsonObjectResponse.getInt("statusCode");
                            if (jsonResponse==0){
                                String emailAddress = null;
                                Log.d("All Details::",response);
                                JSONObject jsonObjectOther = jsonObjectResponse.getJSONObject("data");
                                Boolean foundCustomer = jsonObjectOther.getBoolean("isFoundCustomer");
                                Boolean hasAccount = jsonObjectOther.getBoolean("isHasAccount");

                                if (foundCustomer == false && hasAccount == false){

                                    checkTheLogic(foundCustomer, hasAccount, cardNumber, null);

                                } else {
                                    JSONObject jsonObjectAnother = jsonObjectOther.getJSONObject("foundCustomer");
                                    emailAddress = jsonObjectAnother.getString("email");

                                    Log.d("foundinServer:::", foundCustomer.toString());
                                    Log.d("foundinOrderSite:::", hasAccount.toString());

                                    Log.d("Response Value:::::::", response);
                                    Log.d("StatusCode:::::::", String.valueOf(jsonResponse));


                                    checkTheLogic(foundCustomer, hasAccount, cardNumber, emailAddress);

                                }

                            }else {


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

//
                CheckAccountCardRequest checkAccountCardRequest = new CheckAccountCardRequest(cardNumber,responseListener,errorListener);
                RequestQueue checkCardQueue = Volley.newRequestQueue(SignUpCardDetail.this);
                checkCardQueue.add(checkAccountCardRequest);

              //  LoginRequest loginRequest = new LoginRequest(userName, password, responseListener);
             //   RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
               // requestQueue.add(loginRequest);
            }
        });




    }

    private void checkTheLogic(Boolean foundinServer, Boolean foundinOrderSite, String number, String email) {

        Intent intentpositive,intentnegative = null;

        if (foundinServer){
            if (foundinOrderSite){
                // RTServer = 1, Order Site = 1
                intentpositive = new Intent(SignUpCardDetail.this,LoginPage.class);
                intentpositive.putExtra("Email",email);
                showDialog("Account Exists !!","Log In","OK",null,intentpositive,null);


            }else {
                // RTServer = 1, Order Site = 0
                intentpositive = new Intent(SignUpCardDetail.this,SignUpShortPage.class);
                intentpositive.putExtra("Email",email);
                startActivity(intentpositive);


            }
        }else{
            if (foundinOrderSite){
                // RTServer = 0, Order Site = 1
                intentnegative = new Intent(SignUpCardDetail.this,LoginPage.class);
                showDialog("Server Error","Something Went Wrong","Try Again","Cancel",null,intentnegative);


            }else {
                // RTServer = 0, Order Site = 0
                 intentnegative = new Intent(SignUpCardDetail.this,SignUpShortPage.class);
                showDialog("Sign Up Error !!","Card Details Not Available","Try Again","SignUp Anyway",null,intentnegative);

            }

        }

    }

    public void showDialog(String title, CharSequence message, String positiveButton, String negativeButton, final Intent positiveIntent , final Intent negativeIntent ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpCardDetail.this, R.style.AppCompatAlertDialogStyle);
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
