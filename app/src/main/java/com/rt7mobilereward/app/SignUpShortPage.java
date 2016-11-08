package com.rt7mobilereward.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpShortPage extends AppCompatActivity {

    EditText email, password, passConfirm;
    Button submitShotSignUp;
    String emailStr = null;
    String passStr = null, passConStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_short_page);
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

        email = (EditText)findViewById(R.id.email_shortsignup_edit);
        password = (EditText)findViewById(R.id.pass_shortsignup_edit);
        passConfirm = (EditText)findViewById(R.id.passcon_shortsignup_edit);
        submitShotSignUp = (Button)findViewById(R.id.btn_shortsignup_submit);


        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String email_bundle = extras.getString("email");
            email.setText(email_bundle);
            email.setEnabled(false);

        }

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager)SignUpShortPage.this.
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });

        submitShotSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailStr = email.getText().toString();
                passStr = password.getText().toString();
                passConStr = passConfirm.getText().toString();
                if (emailStr == null || passStr == null || passConStr == null){
                    showDialog("Error !!","Enter All Details","OK",null,null,null);
                }else {
                    if (passConStr.equals(passStr)){

                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                String LoginError = error.toString();
                              Intent intent = new Intent(SignUpShortPage.this,WelcomePage.class);
                                showDialog("Sign Up Error !!","Something Went Wrong","TryAgain","Cancel",null,intent);
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
                                        String cardNumber = null;
                                        Log.d("All Details::",response);
                                        JSONObject jsonObjectOther = jsonObjectResponse.getJSONObject("data");
                                        Boolean foundCustomer = jsonObjectOther.getBoolean("isFoundCustomer");
                                        Boolean hasAccount = jsonObjectOther.getBoolean("isHasAccount");

                                        if (foundCustomer == false && hasAccount == false){

                                            checkTheLogic(foundCustomer, hasAccount, null, null);

                                        } else {
                                            JSONObject jsonObjectAnother = jsonObjectOther.getJSONObject("foundCustomer");
                                            emailAddress = jsonObjectAnother.getString("email");
                                            cardNumber = jsonObjectAnother.getString("customer_card_number");


                                            Log.d("foundinServer:::", foundCustomer.toString());
                                            Log.d("foundinOrderSite:::", hasAccount.toString());

                                            Log.d("Response Value:::::::", response);
                                            Log.d("StatusCode:::::::", String.valueOf(jsonResponse));
                                            Log.d("Email Address::::",emailAddress);



                                            checkTheLogic(foundCustomer, hasAccount, emailStr, passStr);

                                        }

                                    }else {


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

//
                        CheckAccountEmailRequest checkAccountCardRequest = new CheckAccountEmailRequest(emailStr,responseListener,errorListener);
                        RequestQueue checkCardQueue = Volley.newRequestQueue(SignUpShortPage.this);
                        checkCardQueue.add(checkAccountCardRequest);

                    }else {
                        showDialog("Error !!","Passwords Don't Match","Try Again",null,null,null);
                    }
                }
            }
        });


    }

    private void checkTheLogic(Boolean foundinServer, Boolean foundinOrderSite, String email, String pass) {

        Intent intentpositive,intentnegative = null;

        if (foundinServer){
            if (foundinOrderSite){
                // RTServer = 1, Order Site = 1
                intentpositive = new Intent(SignUpShortPage.this,LoginPage.class);
                intentpositive.putExtra("Email",email);
                showDialog("Account Exists !!","Log In","OK",null,intentpositive,null);


            }else {
                // RTServer = 1, Order Site = 0


                intentpositive = new Intent(SignUpShortPage.this,SignUpShortPage.class);
                intentpositive.putExtra("Email",email);
                intentpositive.putExtra("Password",pass);
                startActivity(intentpositive);


            }
        }else{
            if (foundinOrderSite){
                // RTServer = 0, Order Site = 1
                intentnegative = new Intent(SignUpShortPage.this,LoginPage.class);
                showDialog("Server Error","Something Went Wrong","Try Again","Cancel",null,intentnegative);


            }else {
                // RTServer = 0, Order Site = 0
                intentnegative = new Intent(SignUpShortPage.this,SignUpFullCusAccPage.class);
                intentnegative.putExtra("Email",email);
                intentnegative.putExtra("Password",pass);
                startActivity(intentnegative);

                // showDialog("Sign Up Error !!","Card Details Not Available","Try Again","SignUp Anyway",null,intentnegative);

            }

        }

    }


    public void showDialog(String title, CharSequence message, String positiveButton, String negativeButton, final Intent positiveIntent , final Intent negativeIntent ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpShortPage.this, R.style.AppCompatAlertDialogStyle);
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
