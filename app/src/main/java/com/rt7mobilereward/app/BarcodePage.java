package com.rt7mobilereward.app;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BarcodePage extends AppCompatActivity {

    private String barcodeNumber = "";
    private String balanceValue = "";
    private String time = "";
    View imageBarcode;
    TextView showBarcodeCardbal;
    LinearLayout linearLayout;
    Button doneBarcode;
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;
    int Brightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_page);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        cResolver = getContentResolver();

//Get the current window
        window = getWindow();


        Brightness = Settings.System.getInt(cResolver,Settings.System.SCREEN_BRIGHTNESS,0);
        Log.d("Brightness::", String.valueOf(Brightness));
        setBrightness(Brightness);


        linearLayout = (LinearLayout)findViewById(R.id.liner);
        showBarcodeCardbal = (TextView)findViewById(R.id.showcardbalance_barcode);
        doneBarcode = (Button)findViewById(R.id.done_barcode_btn);
       // imageBarcode = findViewById(R.id.barcodeid);
        Intent intent = getIntent();
        if (intent != null){
            barcodeNumber = intent.getExtras().getString("RewardCardNumber");
            balanceValue = intent.getExtras().getString("BalanceValue");
            showBarcodeCardbal.setText(balanceValue);
            BarCodeImage barCodeImage = new BarCodeImage(this,barcodeNumber);
           linearLayout.addView(barCodeImage);

            doneBarcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onBackPressed();
                }
            });


        }


//          BarCodeImage barCodeImage = new BarCodeImage(this);
//          setContentView(barCodeImage);
    }

    public void setBrightness(int brightness){

        //constrain the value of brightness
        if(brightness < 0){
            brightness = 0;}
        else if(brightness > 255) {
            brightness = 255;
        }
        Log.d("BrightSecond", String.valueOf(brightness));
//        ContentResolver cResolver = this.getApplicationContext().getContentResolver();
//        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

    }





}
