package com.rt7mobilereward.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {

    Button btnJoinNow;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        btnJoinNow = (Button) findViewById(R.id.join_now);
        btnSignIn = (Button) findViewById(R.id.sign_in);


    }

    public void ButtonOnClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.join_now:
               // intent = new Intent(this,);
                break;
            case R.id.sign_in:
                // intent = new Intent(this,);
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
