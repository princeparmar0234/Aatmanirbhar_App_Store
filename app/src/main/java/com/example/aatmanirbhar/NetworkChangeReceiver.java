package com.example.aatmanirbhar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        try
        {
            if (isOnline(context)){
              //  Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(context, "Not Internet Connection", Toast.LENGTH_LONG).show();
            }
        }catch (NullPointerException e){
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
    public boolean isOnline(Context context){
        try {
            ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=cm.getActiveNetworkInfo();
            return (networkInfo!=null && networkInfo.isConnected());
        }catch (NullPointerException e){
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}