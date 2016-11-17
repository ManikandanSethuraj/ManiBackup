package com.rt7mobilereward.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Codabar;
import com.onbarcode.barcode.android.Code39;
import com.onbarcode.barcode.android.IBarcode;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-16.
 */


public class BarCodeImage extends View {

    public static String barcodevalue = "";
    public BarCodeImage(Context context, String barcodevalues) {
        super(context);
        barcodevalue = barcodevalues;
        Log.d("Barcode",barcodevalue);
     }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            //testCODABAR(canvas);
            //testCODE11(canvas);
            //testCODE2OF5(canvas);
              testCODE39(canvas);
            //testCODE93(canvas);
            //testEAN8(canvas);
            //testEAN13(canvas);
            //testISBN(canvas);
            //testISSN(canvas);
            //testITF14(canvas);
            //testINTERLEAVED25(canvas);
            //testIDENTCODE(canvas);
            //testLEITCODE(canvas);
            //testMSI(canvas);
            //testONECODE(canvas);
            //testPLANET(canvas);
            //testPOSTNET(canvas);
            //testRM4SCC(canvas);
            //testUPCA(canvas);
            //testUPCE(canvas);
            //testCODE128(canvas);
            //testEAN128(canvas);

            //testDataMatrix(canvas);
            //testPDF417(canvas);
            // testCODABAR(canvas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testCODE39(Canvas canvas) throws Exception
    {
        Code39 barcode = new Code39();

        /*
           Code39 Valid data char set:
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9 (Digits)
                A - Z (Uppercase letters)
                - (Dash), $ (Dollar), % (Percentage), (Space), . (Point), / (Slash), + (Plus)

           Code39 extension Valid data char set:
                All ASCII 128 characters
        */
        // Code39 encodes upper case chars only, for lower case chars, use Code 39 extension
        barcode.setData(barcodevalue);

        barcode.setExtension(true);

        barcode.setAddCheckSum(false);

        // Code 39 Wide Narrow bar Ratio
        // Valid value is from 2.0 to 3.0 inclusive.
        barcode.setN(3.0f);
        // The space between 2 characters in code 39; This a multiple of X; The default is 1.;
        // Valid value is from 1.0 (inclusive) to 5.3 (exclusive)
        barcode.setI(2.0f);
        barcode.setShowStartStopInText(false);

        // Unit of Measure, pixel, cm, or inch
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode bar module width (X) in pixel
        barcode.setX(5.7f);
       //  barcode.setX(3f);
        // barcode bar module height (Y) in pixel
        barcode.setY(230f);
       // barcode.setBarcodeHeight(25f);
      //  barcode.setBarcodeWidth(25f);
       // barcode.setAutoResize(true);

        // barcode image margins
        barcode.setLeftMargin(15f);
        barcode.setRightMargin(15f);
        barcode.setTopMargin(15f);
        barcode.setBottomMargin(15f);

        // barcode image resolution in dpi
        barcode.setResolution(124);

        // disply barcode encoding data below the barcode
        barcode.setShowText(true);
        // barcode encoding data font style
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 50));
        // space between barcode and barcode encoding data
        barcode.setTextMargin(10);
        barcode.setTextColor(AndroidColor.black);

        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);

        /*
        specify your barcode drawing area
	    */
        Rect bound = new Rect(20,20,20,20);
        RectF bounds = new RectF(0, 0, 20, 20);
        barcode.drawBarcode(canvas, bounds);

    }

    private static void testCODABAR(Canvas canvas) throws Exception
    {
        Codabar barcode = new Codabar();

        // barcode data to encode
        /*
	           Codabar Valid data char set:
		            - (Dash), $ (Dollar), : (Colon), / (Slash), . (Point), + (Plus)
		            0, 1, 2, 3, 4, 5, 6, 7, 8, 9
	        */
        barcode.setData(barcodevalue);

        // Codabar Start & Stop Char, Valid values are 'A', 'B', 'C', 'D'
        barcode.setStartChar('A');
        barcode.setStopChar('A');

        // Unit of Measure, pixel, cm, or inch
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode bar module width (X) in pixel
        barcode.setX(7f);
        // barcode bar module height (Y) in pixel
        barcode.setY(250f);

        // barcode image margins
        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);

        // barcode image resolution in dpi
        barcode.setResolution(72);

        // disply barcode encoding data below the barcode
        barcode.setShowText(true);
        // barcode encoding data font style
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 45));
        // space between barcode and barcode encoding data
        barcode.setTextMargin(6);
        barcode.setTextColor(AndroidColor.black);

        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);

        /*
          specify your barcode drawing area
        */
        RectF bounds = new RectF(0, 0, 0, 0);
        barcode.drawBarcode(canvas, bounds);

//        int startX= (canvas.getWidth()-)/2;//for horisontal position
//        int startY=(canvas.getHeight()-gBall.getHeight())/2;//for vertical position
//        canvas.drawBitmap(gball, startX, startY, null);
    }

}
