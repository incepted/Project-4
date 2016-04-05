package com.example.boldanadspicy.dreamalarm.Activities;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boldanadspicy.dreamalarm.Objects.AlarmObj;
import com.example.boldanadspicy.dreamalarm.R;
import com.example.boldanadspicy.dreamalarm.Receiver.AlarmReceiver;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AlarmRingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mRingCharacterImageView, mRingBackgroundImageView;
    TextView mCharacterScript, mCharacterName, mRingClockTimeTextView, mRingClockAmPmTextView, mRingClockDateTextView;
    Button mDismissButton, mSnoozeButton;
    int alarmId, repeatedTimeSoFar;
    AlarmObj alarmObj;
    SimpleDateFormat mTimeFormatter, mAmPmFormatter, mDateFormatter;

    PowerManager.WakeLock wakeLock;

    MediaPlayer ringtonePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmRingActivity");
        wakeLock.acquire();

        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_alarm_ring);

        initializeRingActivityViews();
        setImages();

            initiateMediaPlayer();


        initializeTextClock();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(wakeLock.isHeld()){
            wakeLock.release();
        }
    }

    public void initializeRingActivityViews(){
        mRingCharacterImageView = (ImageView) findViewById(R.id.ring_character_image_view);
        mRingBackgroundImageView = (ImageView) findViewById(R.id.ring_background_image_view);

        mCharacterScript = (TextView) findViewById(R.id.ring_char_script_text_view);
        mCharacterName = (TextView) findViewById(R.id.ring_char_name_text_view);

        mRingClockTimeTextView = (TextView) findViewById(R.id.ring_clock_time_text_view);
        mRingClockAmPmTextView = (TextView) findViewById(R.id.ring_clock_ampm_text_view);
        mRingClockDateTextView = (TextView) findViewById(R.id.ring_clock_date_text_view);

        mDismissButton = (Button) findViewById(R.id.ring_dismiss_button);
        mSnoozeButton = (Button) findViewById(R.id.ring_snooze_button);

        mDismissButton.setOnClickListener(this);
        mSnoozeButton.setOnClickListener(this);

        alarmId = getIntent().getIntExtra(AlarmReceiver.TO_RINGING_ACTIVITY_ALARM_ID_KEY, -1);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(AlarmRingActivity.this).deleteRealmIfMigrationNeeded().build();
        Realm realm = Realm.getInstance(realmConfig);
        RealmResults<AlarmObj> mAlarmSets = realm.where(AlarmObj.class).equalTo("mId",alarmId).findAll();
        if(mAlarmSets.size() == 0){
            alarmObj = mAlarmSets.get(0);
        }

        mTimeFormatter = new SimpleDateFormat("hh:mm");
        mAmPmFormatter = new SimpleDateFormat("a");
        mDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
    }

    public void setImages(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Picasso.with(AlarmRingActivity.this)
                .load(R.drawable.bedroom)
                .resize(size.x, size.y)
                .centerCrop()
                .into(mRingBackgroundImageView);
        Picasso.with(AlarmRingActivity.this)
                .load(R.drawable.kotori_morning_closeup)
                .resize(size.x, size.y)
                .centerCrop()
                .into(mRingCharacterImageView);
        display = null;
        size = null;
    }

    public void initiateMediaPlayer() {
        ringtonePlayer = MediaPlayer.create(this, R.raw.nyan_cat_ringtone);
        ringtonePlayer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ring_dismiss_button:
                ringtonePlayer.stop();
                if(alarmObj == null){
                    break;
                }else {
                    dismissAlarm();
                }
                break;

            case R.id.ring_snooze_button:
                ringtonePlayer.stop();
                if(alarmObj == null){
                    break;
                }else {
                    if (repeatedTimeSoFar >= alarmObj.getmRepeatingTimes()) {
                        dismissAlarm();
                    } else {
                        Intent myIntent = new Intent(this, AlarmReceiver.class);
                        PendingIntent piSnooze = PendingIntent.getBroadcast(this, alarmObj.getmRequestCode(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManagerSnooze = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            alarmManagerSnooze.setExact(AlarmManager.RTC_WAKEUP, calcNextEvent(), piSnooze);
                            repeatedTimeSoFar++;
                        } else {
                            repeatedTimeSoFar++;
                        }
                    }
                    break;
                }
        }
    }

    public void dismissAlarm(){
        Intent myIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(this,alarmObj.getmRequestCode(),myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmObj.getmTriggerTime(), pi);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmObj.getmTriggerTime(), alarmObj.getmInterval(), pi);
        }
    }

    public long calcNextEvent(){
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long nextAlarmTime = currentTime + alarmObj.getmInterval();
        return nextAlarmTime;
    }

    public void initializeTextClock(){
        Date currentDate = new Date();
        mRingClockTimeTextView.setText(mTimeFormatter.format(currentDate));
        mRingClockAmPmTextView.setText(mAmPmFormatter.format(currentDate));
        mRingClockDateTextView.setText(mDateFormatter.format(currentDate));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ringtonePlayer.stop();
    }
}
