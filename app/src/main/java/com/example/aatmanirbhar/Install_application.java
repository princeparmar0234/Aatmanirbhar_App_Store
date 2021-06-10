package com.example.aatmanirbhar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Install_application extends AppCompatActivity {

    
    

    AdvanceDrawerLayout drawerLayout;
    ActionBarDrawerToggle t;
    Toolbar toolbar;

    NavigationView navigationView;

    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;
    ListView userInstalledApps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_application);
        userInstalledApps = (ListView) findViewById(R.id.installed_app_list);

        installedApps = getInstalledApps();
        installedAppAdapter = new AppAdapter(Install_application.this, installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
        userInstalledApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                String[] colors = {" Open App", " App Info"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Install_application.this);
                builder.setTitle("Choose Action")
                        .setItems(colors, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                if (which==0){
                                    Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                                    if(intent != null){
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(Install_application.this, installedApps.get(i).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (which==1){
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
                                    Toast.makeText(Install_application.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                        });
                builder.show();

            }
        });

        //Total Number of Installed-Apps(i.e. List Size)
        String  abc = userInstalledApps.getCount()+"";


        Snackbar.make(userInstalledApps,abc+" Apps Install On Device",Snackbar.LENGTH_LONG).show();
        navigation();
    }

    private void navigation() {
        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Install App");
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
                        Intent intent=new Intent(com.example.aatmanirbhar.Install_application.this,RetriveActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.install:{
                        Intent intent=new Intent(com.example.aatmanirbhar.Install_application.this, com.example.aatmanirbhar.Install_application.class);
                        startActivity(intent);
                        finishAffinity();
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.contact:
                    {
                        Intent intent=new Intent(com.example.aatmanirbhar.Install_application.this,Contact_us.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    }
                    case R.id.rate:{
                        Intent intent=new Intent(com.example.aatmanirbhar.Install_application.this,About_us.class);
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
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(com.example.aatmanirbhar.Install_application.this,RetriveActivity.class);
        startActivity(intent);
        finishAffinity();
    }
    private List<AppList> getInstalledApps() {
        PackageManager pm = getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new AppList(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public class AppAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;

        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.app_list_layout, parent, false);

                listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView=(TextView)convertView.findViewById(R.id.app_package);
                convertView.setTag(listViewHolder);
            }else{
                listViewHolder = (ViewHolder)convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            return convertView;
        }

        class ViewHolder{
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
        }
    }

    public class AppList {
        private String name;
        Drawable icon;
        private String packages;
        public AppList(String name, Drawable icon, String packages) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
        }
        public String getName() {
            return name;
        }
        public Drawable getIcon() {
            return icon;
        }
        public String getPackages() {
            return packages;
        }

    }


}