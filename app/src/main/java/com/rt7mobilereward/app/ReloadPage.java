package com.rt7mobilereward.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReloadPage extends AppCompatActivity implements FragReloadAmount.onSendData
{
    FragReloadAmount fragReloadAmount;
    FragCreditCardRelaod fragCreditCardRelaod;
    Button ReloadClick;
    TextView textViewReload;
    TextView textViewRoload2;
    private String rewardBalance;
    private String timeValue;
    private String cardNumber;
    LinearLayout clickReloadLayout;
    LinearLayout clickForCreditCard;
    TextView showRelaodData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        View view = getLayoutInflater().inflate(R.layout.title_bar, null);
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.MATCH_PARENT,
//                Gravity.CENTER);
//        getSupportActionBar().setCustomView(view,params);
//        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Intent reloadIntent = getIntent();
        if (reloadIntent != null){

        }

        textViewReload = (TextView)findViewById(R.id.reload_detail_text);
        textViewRoload2 = (TextView)findViewById(R.id.reload_detail_text2);
        clickReloadLayout = (LinearLayout) findViewById(R.id.reload_amount_click);
        clickForCreditCard = (LinearLayout)findViewById(R.id.click_for_creditcard);
        fragReloadAmount = new FragReloadAmount();
        fragCreditCardRelaod = new FragCreditCardRelaod();
        showRelaodData = (TextView)findViewById(R.id.reload_amount_getdata);
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.commit();
        if (reloadIntent != null) {



            rewardBalance = reloadIntent.getExtras().getString("BalanceValue");
            timeValue = reloadIntent.getExtras().getString("Time");
            cardNumber = reloadIntent.getExtras().getString("CardNumber");
            if (cardNumber != null){
                if (rewardBalance != null){
                    cardNumber = "My Card(" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)) + ")";
                    Log.d("Last Values::", cardNumber);
                    textViewReload.setText(cardNumber);
                    Log.d("Time Value", timeValue);
                    timeValue = timeValue.substring(13, timeValue.length());
                    timeValue = timeValue.substring(0, timeValue.length() - 4);
                    timeValue = rewardBalance + " as of " + timeValue + " ago";
                    Log.d("Time Value", timeValue);
                    textViewRoload2.setText(timeValue);
                }else {
                    cardNumber = "My Card(" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)) + ")";
                    Log.d("Last Values::", cardNumber);
                    textViewReload.setText(cardNumber);
                    Log.d("Time Value", timeValue);
                    timeValue = timeValue.substring(13, timeValue.length());
                    timeValue = timeValue.substring(0, timeValue.length() - 4);
                    timeValue = rewardBalance + " as of " + timeValue + " ago";
                    Log.d("Time Value", timeValue);
                    textViewRoload2.setText(timeValue);
                }


            }else {
                textViewReload.setText("You do not have card");
                textViewRoload2.setText(" ");
            }



        }

        clickReloadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

              if (fragReloadAmount.isHidden()){
                  fm.beginTransaction().setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down)
                          .replace(R.id.add_amount_in_reload,fragReloadAmount).show(fragReloadAmount)
                          .commit();
              }else
              {
                  fm.beginTransaction()
                          .hide(fragReloadAmount)
                          .commit();
              }

            }
        });

        clickForCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm1 = getSupportFragmentManager();
                if (fragCreditCardRelaod.isHidden()){
                    fm1.beginTransaction().setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down)
                            .replace(R.id.replace_creditcard,fragCreditCardRelaod).show(fragCreditCardRelaod)
                            .commit();

                }else {
                    fm1.beginTransaction()
                            .hide(fragCreditCardRelaod)
                            .commit();

                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void sendData(String data) {
         showRelaodData.setText(data);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .hide(fragReloadAmount)
                .commit();

    }

}
