package com.rt7mobilereward.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-10.
 */

public class FragTabRewardsPage extends Fragment {

    String rewardBalance = "";
    String rewardCardNumber = "";
    String rewardtoken = "";
    TextView Balance;
    TextView Time;
    Button showBarcode;
    Button Reload;
    Button Settings;
    Button Refresh;
    Handler handler = new Handler();
    long startTime = 0L, timeSwapBuff = 0L, updateTime=0L, timeInMilliSec = 0L;

    public FragTabRewardsPage() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rewardBalance = getArguments().getString("RewardsBalance");
        rewardCardNumber = getArguments().getString("CardNumber");
        rewardtoken = getArguments().getString("Token");

        Log.d("RewardBal::",rewardBalance);
        View v =  inflater.inflate(R.layout.frag_tab_rewards_page, container, false);
        Balance = (TextView) v.findViewById(R.id.rewards_balance);
        showBarcode = (Button)v.findViewById(R.id.rewards_showbarcode_btn);
        Reload = (Button)v.findViewById(R.id.rewards_reload_btn);
        Settings = (Button)v.findViewById(R.id.rewards_settings_btn);
        Refresh =(Button)v.findViewById(R.id.detail_refresh_btn);
        Time = (TextView)v.findViewById(R.id.time_update_rewards);
        Log.d("RewardBal::",rewardBalance);
        rewardBalance = "$"+rewardBalance;
        Balance.setText(rewardBalance);
        final String GET_BALANCE_REQUEST_URL = "https://devdbcenter.rt7.net:7293/api/mobile/user/getbalance?cardnum=".concat(rewardCardNumber);
        Refresh.setOnClickListener(new View.OnClickListener() {

            AnimationDrawable d=(AnimationDrawable)Refresh.getCompoundDrawables()[1];
            @Override
            public void onClick(View v) {

                d.start();
                try{


                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // d.stop();
                            String LoginError = error.toString();
                            Log.d("Login Error::",LoginError);
                            android.app.AlertDialog alertConnection = new android.app.AlertDialog.Builder(
                                    getActivity()).create();
                            alertConnection.setTitle("Failed to Load!!");
                            alertConnection.setMessage("Try Again");
                            alertConnection.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
//
                            alertConnection.show();

                        }
                    };


                    final Response.Listener<String> responseListener = new Response.Listener<String>() {



                        @Override
                        public void onResponse(String response) {

                            if (response != null) {
                                try {
                                    Log.d("ResponseatFragReward:::",response.toString());
                                    JSONObject jsonObjectResponse = new JSONObject(response);
                                    String rewardTokenResponse = jsonObjectResponse.getString("at");
                                    Log.d("ATRespnseFragRe::::",rewardtoken);
//                                    if (rewardtoken.equals(rewardTokenResponse) ){
//
//                                    }else {
//                                        rewardtoken = rewardTokenResponse;
//                                    }
                                    JSONObject firstObject = jsonObjectResponse.getJSONObject("all");
                                    int jsonResponse = firstObject.getInt("statusCode");
                                    if (jsonResponse == 0) {
                                        JSONObject jsonAnother = firstObject.getJSONObject("data");
                                        String rewardBalanceResponse = jsonAnother.getString("reward_balance");
                                        rewardBalance = "$"+rewardBalanceResponse;
                                        Balance.setText(rewardBalance);


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

                    GetBalanceRequest loginRequest = new GetBalanceRequest(GET_BALANCE_REQUEST_URL,rewardtoken, responseListener, errorListener);
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(loginRequest);

                }catch (Exception e){

                }finally {
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimeThread,0);
                    d.stop();

                }



            }

        });
        return v;

    }

    Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliSec = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff+timeInMilliSec;
            int secs = (int)(updateTime/1000);
            int mins = secs/60;
            int hour = mins/60;
            secs%=60;
            String showTime = "0";
            if (hour <= 0 && mins <= 0) {
                showTime = "Last updated "+secs+"s "+"ago";
                Time.setText(showTime);
            }else if (hour <= 0 && mins > 0){
                showTime = "Last updated "+mins+"m "+"ago";
                Time.setText(showTime);
            }else if (hour > 0){
                showTime = "Last updated "+hour+"h "+"ago";
                Time.setText(showTime);
            }
            handler.postDelayed(this,0);
        }
    };



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      //  outState.putString("BalanceRestore",Balance.getText().toString());
       // outState.putString("someVarB", someVarB);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            String RestoreBalance = savedInstanceState.getString("BalanceRestore");
//            Balance.setText(RestoreBalance);
//        }

    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();

    showBarcode.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),BarcodePage.class);
           // String balanceValueBarcode = Balance.getText().toString();
            intent.putExtra("BalanceValue",rewardBalance);
            String timeValue = Time.getText().toString();
            intent.putExtra("Time",timeValue);
            intent.putExtra("RewardCardNumber",rewardCardNumber);
            startActivity(intent);

        }
    });




    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
