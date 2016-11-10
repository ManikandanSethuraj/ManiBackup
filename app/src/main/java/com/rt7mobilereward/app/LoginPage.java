package com.rt7mobilereward.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

        emailAddress = (EditText)findViewById(R.id.email_id_edit);
        passWord = (EditText)findViewById(R.id.password_edit);
        cancel = (Button)findViewById(R.id.btn_login_cancel);
        signIn = (Button)findViewById(R.id.btn_signin);

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
              final String userName = emailAddress.getText().toString();
              final String password = passWord.getText().toString();


                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                        if (response != null) {
                        try {



                                JSONObject jsonObjectResponse = new JSONObject(response);
                                int jsonResponse = jsonObjectResponse.getInt("statusCode");
                                if (jsonResponse == 0) {

                                    JSONObject jsonAnother = jsonObjectResponse.getJSONObject("data");
                                    JSONObject jsonOther = jsonAnother.getJSONObject("user");
                                    String Name = jsonOther.getString("firstname");
                                    String Email = jsonOther.getString("email");
                                    Log.d("Response Value:::::::", response);
                                    Log.d("StatusCode:::::::", String.valueOf(jsonResponse));
                                    Log.d("Name", Name);
                                    Intent intent = new Intent(LoginPage.this,MainActivity.class);
                                    intent.putExtra("UserName",Name);
                                    intent.putExtra("email",Email);
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
             //   String value = loginRequest.getHeaderFile();
            //    Log.d("The Value back:::",value);
            }
        });

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
