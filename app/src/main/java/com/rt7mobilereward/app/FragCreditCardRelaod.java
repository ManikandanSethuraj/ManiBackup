package com.rt7mobilereward.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-18.
 */

public class FragCreditCardRelaod extends Fragment {

    Button okButton;
    EditText cardNumber;
    EditText cvc;
    Spinner monthSpinner;
    Spinner yearSpinner;
    Spinner currencySpinner;
    onSendCreditCardData onSendCreditCardData;
    private static final String CURRENCY_UNSPECIFIED = "Unspecified";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_form_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        this.saveButton = (Button) view.findViewById(R.id.save);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveForm(view);
//            }
//        });
        this.cardNumber = (EditText) view.findViewById(R.id.number);
        this.cvc = (EditText) view.findViewById(R.id.cvc);
        this.monthSpinner = (Spinner) view.findViewById(R.id.expMonth);
        this.yearSpinner = (Spinner) view.findViewById(R.id.expYear);
        okButton = (Button) view.findViewById(R.id.ok_creditcard);
      //  this.currencySpinner = (Spinner) view.findViewById(R.id.currency);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendCreditCardData.sendData(getCardNumber(),getExpMonth(),getExpYear(),getCvc());
            }
        });


        return view;


    }


    public String getCardNumber() {
        return this.cardNumber.getText().toString();
    }


    public String getCvc() {
        return this.cvc.getText().toString();
    }


    public Integer getExpMonth() {
        return getInteger(this.monthSpinner);
    }


    public Integer getExpYear() {
        return getInteger(this.yearSpinner);
    }


    public String getCurrency() {
        if (currencySpinner.getSelectedItemPosition() == 0) return null;
        String selected = (String) currencySpinner.getSelectedItem();
        if (selected.equals(CURRENCY_UNSPECIFIED))
            return null;
        else
            return selected.toLowerCase();
    }




    public void saveForm(View button) {
         // String CreditCardNumber = getCardNumber();
        onSendCreditCardData.sendData(getCardNumber(),getExpMonth(),getExpYear(),getCvc());


      //  ((PaymentActivity)getActivity()).saveCreditCard(this);
    }

    private Integer getInteger(Spinner spinner) {
        try {
            return Integer.parseInt(spinner.getSelectedItem().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onSendCreditCardData = (onSendCreditCardData) activity;

    }

    public interface onSendCreditCardData{

        public void sendData(String cardNumber,int month, int year,  String cvc);

    }
}
