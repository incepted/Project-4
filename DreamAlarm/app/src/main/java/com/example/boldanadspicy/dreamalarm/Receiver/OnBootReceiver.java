package com.example.boldanadspicy.dreamalarm.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.boldanadspicy.dreamalarm.Services.AlarmService;

/**
 * Created by Wasabi on 4/4/2016.
 */
public class OnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AlarmService.class);
        serviceIntent.setAction(AlarmService.CREATE_EXISTING_ALARMS);
        context.startService(serviceIntent);
    }
}
