package com.rt7mobilereward.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {

    private Button btnJoinNow;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(R.color.colorBlack));

        }


        btnJoinNow = (Button) findViewById(R.id.join_now);
        btnSignIn = (Button) findViewById(R.id.sign_in);


    }

    public void ButtonOnClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.join_now:
                intent = new Intent(this,SignUpCardAcceptancePage.class);
                break;
            case R.id.sign_in:
                 intent = new Intent(this,LoginPage.class);
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();


       // ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.Theme_Sphinx_Dialog_Alert);
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomePage.this,R.style.AppCompatAlertDialogStyle);
        builder.setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }
}
