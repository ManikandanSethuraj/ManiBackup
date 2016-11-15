package com.rt7mobilereward.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragTabRewardsPage fragTabRewardsPage;
    public FragTabOrdersPage fragTabOrdersPage;
    public FragTabGiftPage fragTabGiftPage;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        bundle = new Bundle();
        FragTabRewardsPage fragTabRewardsPage = new FragTabRewardsPage();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();

            if (intent != null){
                String email_bundle = intent.getExtras().getString("Email");
                String tokenid = intent.getExtras().getString("Token");
                String username = intent.getExtras().getString("UserName");
                String cardnumber = intent.getExtras().getString("CardNumber");
                String rewardbalance = intent.getExtras().getString("CardNumber");
                String giftbalance = intent.getExtras().getString("GiftBalance");
                Log.d("Toekn::",tokenid);
                Log.d("Email::",email_bundle);

                bundle.putString("Eamil",email_bundle);
                String rewardss = "";
                bundle.putString("Token",tokenid);
                bundle.putString("UserName",username);
                bundle.putString("CardNumber",cardnumber);
                bundle.putString("RewardsBalance",rewardbalance);
                bundle.putString("GiftBalance",giftbalance);

                Log.d("Rewards::::",rewardbalance);
                rewardss = bundle.getString("RewardsBalance");
                Log.d("Rewewewe:::",rewardss);
              //  fragTabRewardsPage.setArguments(bundle);

            }








    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Log.d("All in All","All in All");
        adapter.addFragment(new FragTabOrdersPage(), "STORES");
        adapter.addFragment(new FragTabRewardsPage(), "REWARDS");
        Log.d("All in All","All in All");
        adapter.addFragment(new FragTabGiftPage(), "GIFT");
        adapter.addFragment(new FragTabOrdersPage(), "ORDERS");
        Log.d("All in All","All in All");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            fragment.setArguments(bundle);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
