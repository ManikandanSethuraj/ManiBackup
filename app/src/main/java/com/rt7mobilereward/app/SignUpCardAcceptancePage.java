package com.rt7mobilereward.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpCardAcceptancePage extends AppCompatActivity {

    Button yesHaveCard;
    Button noDontHaveCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_card_acceptance_page);
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

