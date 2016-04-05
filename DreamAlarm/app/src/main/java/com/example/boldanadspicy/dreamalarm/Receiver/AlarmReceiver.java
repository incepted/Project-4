package com.example.boldanadspicy.dreamalarm.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.boldanadspicy.dreamalarm.Activities.AlarmRingActivity;
import com.example.boldanadspicy.dreamalarm.Services.AlarmService;

/**
 * Created by Wasabi on 4/4/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String TO_RINGING_ACTIVITY_ALARM_ID_KEY =  "TO_RINGING_ACTIVITY_ALARM_ID_KEY";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmRecerver", "Received!"+intent.getIntExtra(AlarmService.TO_RECEIVER_ALARM_ID_KEY,-1));
        Intent ringingIntent = new Intent(context, AlarmRingActivity.class);
        ringingIntent.putExtra(TO_RINGING_ACTIVITY_ALARM_ID_KEY, intent.getIntExtra(AlarmService.TO_RECEIVER_ALARM_ID_KEY,-1));
        ringingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(ringingIntent);
    }
}
