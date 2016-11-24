package com.rt7mobilereward.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {


    private EditText emailAddress;
    private EditText passWord;
    private Button cancel;
    private Button signIn;
    public static String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

        View view = getLayoutInflater().inflate(R.layout.title_bar, null);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);



        TextView Title = (TextView) view.findViewById(R.id.myTitle);
        Title.setText(R.string.str_sign_in);

//
        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        emailAddress = (EditText)findViewById(R.id.email_id_edit);
        passWord = (EditText)findViewById(R.id.password_edit);
        cancel = (Button)findViewById(R.id.btn_login_cancel);
        signIn = (Button)findViewById(R.id.btn_signin);

        emailAddress.setText("testing3@testing.com");
        passWord.setText("testing3");

        emailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailAddress.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager)LoginPage.this.
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(emailAddress, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        emailAddress.requestFocus();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(LoginPage.this,WelcomePage.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if (isInternetAvailable()){
                final ProgressDialog progressbar;

                progressbar = new ProgressDialog(LoginPage.this);
                progressbar.setTitle("Please Wait!!");
                progressbar.setMessage("Logging In");
                progressbar.setCancelable(false);
                progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressbar.show();

                final String userName = emailAddress.getText().toString();
              final String password = passWord.getText().toString();


                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressbar.dismiss();
                        String LoginError = error.toString();
                        Log.d("Login Error::",LoginError);
                        android.app.AlertDialog alertConnection = new android.app.AlertDialog.Builder(
                                LoginPage.this).create();
                        alertConnection.setTitle("Login Failed !!");
                        alertConnection.setMessage("Try Again");
                        alertConnection.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
//                        alertConnection.setButton("SIGN UP??", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(LoginPage.this,SignUpCardDetail.class);
//                                startActivity(intent);
//                            }
//                        });
                        alertConnection.show();

                    }
                };


                final Response.Listener<String> responseListener = new Response.Listener<String>() {



                    @Override
                    public void onResponse(String response) {
                        progressbar.dismiss();
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
                                    String F_Name = jsonOther.optString("firstname"," ");
                                    String S_Name = " " + jsonOther.optString("lastname"," ");
                                    String Name = F_Name.concat(S_Name);
                                    String Email = jsonOther.optString("email"," ");
                                    String CardNumber = jsonOther.optString("customer_card_number"," ");
                                    String RewardBalance = jsonOther.optString("reward_balance","0");
                                    String Giftbalance = jsonOther.optString("gift_balance","0");
                                    String City = jsonOther.optString("city"," ");
                                    String State = jsonOther.optString("state"," ");
                                    String zip = jsonOther.optString("zip"," ");
                                    String dob = jsonOther.optString("date_of_birth"," ");
                                    String Address1 = jsonOther.optString("address1"," ");
                                    String Address2 = jsonOther.optString("address2"," ");
                                    String Phone = jsonOther.optString("phone"," ");

                                    Log.d("Response Value:::::::", response);
                                    Log.d("StatusCode:::::::", String.valueOf(jsonResponse));
                                    Log.d("Name", Name);
                                    Intent intent = new Intent(LoginPage.this,MainActivity.class);
                                    intent.putExtra("UserName",Name);
                                    intent.putExtra("Email",Email);
                                    intent.putExtra("Token",token);
                                    intent.putExtra("CardNumber",CardNumber);
                                   if (RewardBalance != null){
                                       intent.putExtra("RewardBalance",RewardBalance);
                                   }

                                    intent.putExtra("GiftBalance",Giftbalance);
                                    intent.putExtra("City",City);
                                    intent.putExtra("State",State);
                                    intent.putExtra("Zip",zip);
                                    intent.putExtra("dob",dob);
                                    intent.putExtra("Address1",Address1);
                                    intent.putExtra("Address2",Address2);
                                    intent.putExtra("Phone",Phone);
                                    intent.putExtra("F_Name",F_Name);
                                    intent.putExtra("L_name",S_Name);


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



                LoginRequest loginRequest = new LoginRequest(userName, password, responseListener, errorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
                requestQueue.add(loginRequest);
               // String va = loginRequest.getHeaderFile();
                Log.d("The Value back:::","This is nothing");
            }else {
                  Toast.makeText(LoginPage.this,"No Internet!! Set your Connection!!",Toast.LENGTH_LONG).show();
              }

            }
        });

    }

    public boolean isInternetAvailable() {
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        {
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            if (netInfo == null)
            {

                return false;

            }
            else
            {
                return true;

            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
