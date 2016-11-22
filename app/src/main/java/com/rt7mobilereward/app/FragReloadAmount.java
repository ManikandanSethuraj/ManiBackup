package com.rt7mobilereward.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-17.
 */

public class FragReloadAmount extends Fragment {


    TextView ReloadAmount;
    ArrayList<String> items = new ArrayList<String>();
    TwoWayView lvTest;
    ArrayAdapter<String> aItems;
    onSendData onSendingData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_relaod_amount,container,false);

        lvTest = (TwoWayView)v.findViewById(R.id.reloadamount_items);
        return v;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        items.add("$5");
        items.add("$10");
        items.add("$15");
        items.add("$20");
        items.add("$25");
        items.add("$30");
        items.add("$35");
        items.add("$40");
        items.add("$45");
        items.add("$50");
        items.add("$55");
        items.add("$60");
        items.add("$65");
        items.add("$70");
        items.add("$75");
        items.add("$80");
        items.add("$85");
        items.add("$90");
        items.add("$95");
        items.add("$100");
        aItems = new ArrayAdapter<String>(getActivity(), R.layout.reload_amount_textview, items);
        lvTest.setAdapter(aItems);

        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valuetobeSent = items.get(position);
                Log.d("SentData:::",valuetobeSent);
                onSendingData.sendData(valuetobeSent);


            }
        });


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onSendingData = (onSendData)activity;

    }
    public interface onSendData{

        public void sendData(String data);

    }

}
