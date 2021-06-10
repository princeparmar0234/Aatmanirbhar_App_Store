package  ;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;

import java.util.HashMap;

public class Contact_us extends AppCompatActivity {


    EditText name,email,message;
    Button submit;
    AdvanceDrawerLayout drawerLayout;
    ActionBarDrawerToggle t;
    Toolbar toolbar;

    NavigationView navigationView;
   // ProgressDialog progressDialog=new ProgressDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        message=findViewById(R.id.message);
        submit=findViewById(R.id.submit);


       navigation();
        mainMethod();
    }
    public void mainMethod(){


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()){
                    name.setError("Please Enter Name..");
                }
                else if (email.getText().toString().isEmpty()){
                    email.setError("Please Enter Email Address..");
                }
                else if (message.getText().toString().isEmpty()){
                    message.setError("Please Enter Your Message");
                }
                else {
                    ProgressDialog progressDialog=new ProgressDialog(com.example.aatmanirbhar.Contact_us.this);
                    progressDialog.show();
                    progressDialog.setMessage("Please Wait...");
                    String a,b,c,d;
                    a=name.getText().toString();
                    b=email.getText().toString();
                    c=message.getText().toString();

                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",a);
                    map.put("email",b);
                    map.put("message",c);
                    FirebaseDatabase.getInstance().getReference().child("contact_us").push()
                            .setValue(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    closeKeyboard();
                                    progressDialog.dismiss();
                                    Snackbar.make(view,"I Am Contact Short Time..", Snackbar.LENGTH_LONG).show();
                                    clearData();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            closeKeyboard();
                            progressDialog.dismiss();
                            Snackbar.make(view,""+e, Snackbar.LENGTH_LONG).show();

                        }
                    });
                }


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
    private void navigation() {

        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");
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
                        Intent intent=new Intent(com.example.aatmanirbhar.Contact_us.this, com.example.aatmanirbhar.RetriveActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.install:{
                        Intent intent=new Intent(com.example.aatmanirbhar.Contact_us.this, com.example.aatmanirbhar.Install_application.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.contact:
                    {
                        Intent intent=new Intent(com.example.aatmanirbhar.Contact_us.this, com.example.aatmanirbhar.Contact_us.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    }
                    case R.id.rate:{
                        Intent intent=new Intent(com.example.aatmanirbhar.Contact_us.this, com.example.aatmanirbhar.About_us.class);
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


    public void clearData(){
        name.setText("");
        email.setText("");
        message.setText("");
    }
    @Override
    public void onBackPressed() {

        drawerLayout.closeDrawers();
       Intent intent=new Intent(com.example.aatmanirbhar.Contact_us.this, com.example.aatmanirbhar.RetriveActivity.class);
       startActivity(intent);
       finishAffinity();
    }
}