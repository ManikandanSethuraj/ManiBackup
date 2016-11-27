package com.rt7mobilereward.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragTabRewardsPage fragTabRewardsPage;
    public FragTabOrdersPage fragTabOrdersPage;
    public FragTabGiftPage fragTabGiftPage;
    private Bundle bundle;
    private String tokenid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        bundle = new Bundle();
        FragTabRewardsPage fragTabRewardsPage = new FragTabRewardsPage();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();

            if (intent != null){
                if (intent.getExtras() != null) {
                    String email_bundle = intent.getExtras().getString("Email");
                    tokenid = intent.getExtras().getString("Token");
                    String username = intent.getExtras().getString("UserName");
                    String cardnumber = intent.getExtras().getString("CardNumber");
                    String rewardbalance = intent.getExtras().getString("RewardBalance");
                    String giftbalance = intent.getExtras().getString("GiftBalance");
                    String city =  intent.getExtras().getString("City");
                    String state = intent.getExtras().getString("State");
                    String zip =  intent.getExtras().getString("Zip");
                    String dob = intent.getExtras().getString("dob");
                    String address1 =  intent.getExtras().getString("Address1");
                    String address2 = intent.getExtras().getString("Address2");
                    String phone = intent.getExtras().getString("Phone");
                    String fname = intent.getExtras().getString("F_Name");
                    String lname = intent.getExtras().getString("L_name");
                    Log.d("Toekn::", tokenid);
                    Log.d("Email::", email_bundle);

                    bundle.putString("Email", email_bundle);
                    String rewardss = "";
                    bundle.putString("Token", tokenid);
                    bundle.putString("UserName", username);
                  //  bundle.putString("",);
                    bundle.putString("CardNumber", cardnumber);
                    bundle.putString("RewardsBalance", rewardbalance);
                    bundle.putString("GiftBalance", giftbalance);
                    bundle.putString("city",city);
                    bundle.putString("state", state);
                    bundle.putString("zip",zip);
                    bundle.putString("dob",dob);
                    bundle.putString("address1",address1);
                    bundle.putString("address2",address2);
                    bundle.putString("phone",phone);
                    bundle.putString("fname",fname);
                    bundle.putString("lname",lname);

                    Log.d("Rewards::::", rewardbalance);
                    rewardss = bundle.getString("RewardsBalance");
                    Log.d("Rewewewe:::", rewardss);
                    //  fragTabRewardsPage.setArguments(bundle);
                }

            }








    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Log.d("All in All","All in All");
        adapter.addFragment(new FragTabOrdersPage(), "STORES");
        adapter.addFragment(new FragTabRewardsPage(), "REWARDS");
        Log.d("All in All","All in All");
        adapter.addFragment(new FragTabGiftPage(), "GIFT");
        adapter.addFragment(new FragTabOrdersPage(), "ORDERS");
        Log.d("All in All","All in All");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            fragment.setArguments(bundle);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder alertConnection = new AlertDialog.Builder(
                MainActivity.this,R.style.AppCompatAlertDialogStyle);
        alertConnection.setTitle("Confirmation");
        alertConnection.setMessage("Do you Wish to Signout?");
        alertConnection.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                 signoutnow();
            }
        });
        alertConnection.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertConnection.show();




    }



    public void signoutnow(){

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
                            Toast.makeText(MainActivity.this, Message, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this, WelcomePage.class);
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

        SignoutRequest signoutRequest = new SignoutRequest(tokenid, responseListener, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(signoutRequest);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
