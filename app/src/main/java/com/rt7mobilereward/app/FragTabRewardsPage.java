package com.rt7mobilereward.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-10.
 */

public class FragTabRewardsPage extends Fragment {

    String rewardBalance = "";
    TextView Balance;
    public FragTabRewardsPage() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rewardBalance = getArguments().getString("RewardsBalance");
        Log.d("RewardBal::",rewardBalance);
        View v =  inflater.inflate(R.layout.frag_tab_rewards_page, container, false);
        Balance = (TextView) v.findViewById(R.id.rewards_balance);
        Log.d("RewardBal::",rewardBalance);
        rewardBalance = "$"+rewardBalance;
        Balance.setText(rewardBalance);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onStart() {
        super.onStart();
     //   Balance.setText(rewardBalance);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
