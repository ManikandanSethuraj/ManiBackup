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

    }



}
