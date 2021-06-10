package com.example.aatmanirbhar;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;


import java.util.ArrayList;
import java.util.List;

public class RetriveActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;


    BroadcastReceiver broadcastReceiver;
    AdvanceDrawerLayout drawerLayout;
    ActionBarDrawerToggle t;
    NavigationView navigationView;
    private InterstitialAd mInterstitialAd;

    private static final int TIME_DELEY=2000;
    private static long back_prassed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive);

        broadcastReceiver=new NetworkChangeReceiver();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3903791531866996/9268938018");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);


        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Best Apps");
        arrayList.add("indian");
        arrayList.add("top_10");
        arrayList.add("game");
        arrayList.add("Government");
        arrayList.add("Free");
        arrayList.add("Entertainment");

        preparreViewPager(viewPager,arrayList);
        tabLayout.setupWithViewPager(viewPager);
        navigation();

        registerNetwordBrodcastReceiver();
    }

    protected void registerNetwordBrodcastReceiver(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected  void unRegistered(){
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistered();
    }

    public void  navigation(){
        toolbar=findViewById(R.id.toolbar);

        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");
        drawerLayout.setRadius(GravityCompat.START, 25);
        drawerLayout.setViewScale(Gravity.START, 0.9f);
        drawerLayout.setViewElevation(Gravity.START,20);

        drawerLayout.useCustomBehavior(GravityCompat.START); //assign custom behavior for "Left" drawer
        drawerLayout.useCustomBehavior(GravityCompat.END);


        t=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);


        drawerLayout.addDrawerListener(t);
        t.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.home:{
                        Intent intent=new Intent(com.example.aatmanirbhar.RetriveActivity.this, com.example.aatmanirbhar.RetriveActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.install:{
                        Intent intent=new Intent(com.example.aatmanirbhar.RetriveActivity.this, com.example.aatmanirbhar.Install_application.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.contact:
                    {
                        Intent intent=new Intent(com.example.aatmanirbhar.RetriveActivity.this,Contact_us.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    }
                    case R.id.rate:{
                        Intent intent=new Intent(com.example.aatmanirbhar.RetriveActivity.this,About_us.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    }
                    case R.id.exit:{
                        finishAffinity();
                        break;
                    }
                    default:{
                        return true;
                    }
                }
                return true;
            }
        });


    }
    private void preparreViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter=new MainAdapter(getSupportFragmentManager());

        com.example.aatmanirbhar.MainFragment fragment=new com.example.aatmanirbhar.MainFragment();
        for (int i=0;i<arrayList.size();i++){
            Bundle bundle=new Bundle();
            bundle.putString("title",arrayList.get(i));

            fragment.setArguments(bundle);
            adapter.addFragment(fragment,arrayList.get(i));
            fragment=new com.example.aatmanirbhar.MainFragment();

        }

        viewPager.setAdapter(adapter);
    }




    private class MainAdapter extends FragmentPagerAdapter {
        ArrayList<String> arrayList=new ArrayList<>();
        List<Fragment> fragmentList=new ArrayList<>();


        public void addFragment(Fragment fragment, String title){
            arrayList.add(title);
            fragmentList.add(fragment);

        }
        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }
    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            finishAffinity();
        }else
        {
            finishAffinity();
        }
        View view = null;
        if (back_prassed+TIME_DELEY>System.currentTimeMillis()){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            Snackbar.make(drawerLayout,"Press Again To Exit...", BaseTransientBottomBar.LENGTH_LONG).show();
        }
        back_prassed=System.currentTimeMillis();

    }
}