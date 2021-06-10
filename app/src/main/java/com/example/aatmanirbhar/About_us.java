package com.example.aatmanirbhar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;

import java.util.HashMap;


public class About_us extends AppCompatActivity {


    AdvanceDrawerLayout drawerLayout;
    ActionBarDrawerToggle t;
    Toolbar toolbar;

    NavigationView navigationView;

    EditText name,improve;
    RatingBar ratingBar;
    Button submit;
    LinearLayout linearLayout;
    //ProgressDialog progressDialong=new ProgressDialog(About_us.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        navigation();

        name=findViewById(R.id.name);
        improve=findViewById(R.id.help_to_improve);
        ratingBar=findViewById(R.id.rating);
        linearLayout=findViewById(R.id.layout);
        submit=findViewById(R.id.submit);
        linearLayout.setVisibility(View.GONE);
        ratingBar.setNumStars(5);
        //progressDialong.setMessage("Please Wait...");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog=new ProgressDialog(com.example.aatmanirbhar.About_us.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                String a,b;
                a=name.getText().toString();
                b=improve.getText().toString();

                HashMap<String, String> map=new HashMap<>();
                map.put("Rate", String.valueOf(ratingBar.getRating()));
                map.put("Nam",a);
                map.put("Help",b);
                FirebaseDatabase.getInstance().getReference().child("Rate").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    closeKeyboard();
                                    Snackbar.make(view,"You Have Provide "+ratingBar.getRating()+" Star", BaseTransientBottomBar.LENGTH_LONG).show();
                                    clearData();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(view,""+e, BaseTransientBottomBar.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });

    }

    private void navigation() {
        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rate ");
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
                        Intent intent=new Intent(com.example.aatmanirbhar.About_us.this, com.example.aatmanirbhar.RetriveActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.install:{
                        Intent intent=new Intent(com.example.aatmanirbhar.About_us.this, com.example.aatmanirbhar.Install_application.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.contact:
                    {
                        Intent intent=new Intent(com.example.aatmanirbhar.About_us.this,Contact_us.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    }
                    case R.id.rate:{
                        Intent intent=new Intent(com.example.aatmanirbhar.About_us.this, com.example.aatmanirbhar.About_us.class);
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

    private void closeKeyboard(){
        View view=this.getCurrentFocus();
        if (view!=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    public void clearData(){
        ratingBar.setRating(1);
        name.setText("");
        improve.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(com.example.aatmanirbhar.About_us.this, com.example.aatmanirbhar.RetriveActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}