package com.example.boldanadspicy.dreamalarm.Services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.boldanadspicy.dreamalarm.Activities.AlarmListActivity;
import com.example.boldanadspicy.dreamalarm.Objects.AlarmObj;
import com.example.boldanadspicy.dreamalarm.Objects.AlarmSingleton;
import com.example.boldanadspicy.dreamalarm.Receiver.AlarmReceiver;

import java.util.ArrayList;

/**
 * Created by Wasabi on 4/4/2016.
 */
public class AlarmService extends IntentService {

    public static final String TAG = AlarmService.class.getSimpleName();

    public static final String CREATE_EXISTING_ALARMS = "CREATE_EXISTING_ALARMS";
    public static final String ADD = "ADD";
    public static final String CANCEL = "CANCEL";

    public static final String ALARM_ID_KEY = "ALARM_ID_KEY";
    public static final String TO_RECEIVER_ALARM_ID_KEY = "TO_RECEIVER_NEW_ALARM_ID_KEY";
    public static final String REQUERY_KEY = "REQUERY_KEY";
    public static final String TO_ACTIVITY_ALARM_ID_KEY = "REQUERY_KEY";

    private IntentFilter matcher;
    private ArrayList<PendingIntent> pendingIntents;

    public AlarmService() {
        super(TAG);
        matcher = new IntentFilter();
        matcher.addAction(CREATE_EXISTING_ALARMS);
        matcher.addAction(ADD);
        matcher.addAction(CANCEL);
        pendingIntents = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        int alarmId = intent.getIntExtra(AlarmListActivity.TO_SERVICE_ALARM_ID_KEY, -1);

        if(matcher.matchAction(action)){
            execute(action, alarmId);
        }
    }

    private void execute(String action, int alarmId){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(action.equals(ADD)){

            AlarmObj addedAlam = AlarmSingleton.getInstance().getNewlyAddedAlarm();
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra(TO_RECEIVER_ALARM_ID_KEY, alarmId);
            PendingIntent newAlarmPendingIntent = PendingIntent.getBroadcast(this, addedAlam.getmRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, addedAlam.getmTriggerTime(), newAlarmPendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, addedAlam.getmTriggerTime(), addedAlam.getmInterval(), newAlarmPendingIntent);
            }
            pendingIntents.add(newAlarmPendingIntent);

        } else if (action.equals( CREATE_EXISTING_ALARMS)){

            ArrayList<AlarmObj> alarmObjs = AlarmSingleton.getInstance().getAlarms();

            for (int i = 0; i < alarmObjs.size() ; i++) {
                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.putExtra(TO_RECEIVER_ALARM_ID_KEY, alarmId);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmObjs.get(i).getmRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmObjs.get(i).getmTriggerTime(), alarmObjs.get(i).getmInterval(), pendingIntent);
                pendingIntents.add(pendingIntent);
            }

        } else if (action.equals(CANCEL)){
            //Cancel the alarm
            alarmManager.cancel(pendingIntents.get(alarmId-1));
            pendingIntents.remove(alarmId-1);

            //Update the list (send broadcast to list activity)
            Intent listIntent = new Intent(this, AlarmListActivity.class);
            listIntent.putExtra(REQUERY_KEY,"REQUERY");
            listIntent.putExtra(TO_ACTIVITY_ALARM_ID_KEY,alarmId);
            sendBroadcast(listIntent);
        }
    }
}
