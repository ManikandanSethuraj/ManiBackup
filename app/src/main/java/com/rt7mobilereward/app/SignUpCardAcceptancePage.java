package com.rt7mobilereward.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpCardAcceptancePage extends AppCompatActivity {

    private Button yesHaveCard;
    private Button noDontHaveCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_sign_up_card_acceptance_page);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
//       View view = getLayoutInflater().inflate(R.layout.title_bar, null);
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

        yesHaveCard = (Button) findViewById(R.id.btn_yes_card);
        noDontHaveCard = (Button) findViewById(R.id.btn_no_card);


    }

    public void ButtonOnClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_yes_card:
                intent = new Intent(this,SignUpCardDetail.class);
                break;
            case R.id.btn_no_card:
                intent = new Intent(this,SignUpShortPage.class);
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }

    }

