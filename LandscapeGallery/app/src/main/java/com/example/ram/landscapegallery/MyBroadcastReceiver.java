package com.example.ram.landscapegallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ram_n on 10/31/2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver
{
    // Broadcast Receiver that is responsible for listening to Chicago Landmark app


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent i=new Intent(context,MainActivity.class);
        context.startActivity(i);
        //Toast.makeText(context,"Data Received from External App",Toast.LENGTH_SHORT).show();
    }
}