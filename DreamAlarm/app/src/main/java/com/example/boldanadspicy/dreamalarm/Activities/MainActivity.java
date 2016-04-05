package com.example.boldanadspicy.dreamalarm.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boldanadspicy.dreamalarm.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mBackground, mCharImage, mAlarmIcon, mCharSelectIcon, mSettingsIcon;
    TextView mClockTimeText, mClockAmPmText, mClockDateText, mCharNameText, mCharScriptText;
    BroadcastReceiver onMinuteChangedReceiver;
    SimpleDateFormat mTimeFormatter, mAmPmFormatter, mDateFormatter;
    String sharedPrefOpenKey = "OPEN";
    String sharedPrefLaunchKey = "LAUNCH";
    String sharedPrefNameKey = "NAME";

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = MainActivity.this.getSharedPreferences(sharedPrefOpenKey, Context.MODE_PRIVATE);
        boolean firstLaunch = sharedPref.getBoolean(sharedPrefLaunchKey, true);

        if (firstLaunch) {
            launchNameDialog();
            editor = sharedPref.edit();
            editor.remove(sharedPrefLaunchKey);
            editor.putBoolean(sharedPrefLaunchKey, false);
            editor.commit();
        } else {
            name = sharedPref.getString(sharedPrefNameKey, "Default");
        }

        initializeViews();
        setImages();
        linkIconsWithActivities();
        setTextScript();
    }

    public void nameYourGirl(String name){
        mCharNameText.setText(name);
    }

    public void launchNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = MainActivity.this
                .getLayoutInflater()
                .inflate(R.layout.name_dialog, null, false);
        builder.setView(dialogView);

        final EditText nameEdit = (EditText) dialogView.findViewById(R.id.edittext);

        builder.setTitle("Name your girlfriend!");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = nameEdit.getText().toString();
                editor = sharedPref.edit();
                editor.putString(sharedPrefNameKey, name);
                editor.commit();
                nameYourGirl(name);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void initializeViews() {
        mBackground = (ImageView) findViewById(R.id.main_background_image_view);
        mCharImage = (ImageView) findViewById(R.id.main_char_image_view);

        mAlarmIcon = (ImageView) findViewById(R.id.main_alarm_list_icon);
        mCharSelectIcon = (ImageView) findViewById(R.id.main_char_select_icon);
        mSettingsIcon = (ImageView) findViewById(R.id.main_settings_icon);

        mClockTimeText = (TextView) findViewById(R.id.main_clock_time_text_view);
        mClockAmPmText = (TextView) findViewById(R.id.main_clock_ampm_text_view);
        mClockDateText = (TextView) findViewById(R.id.main_clock_date_text_view);
        mCharNameText = (TextView) findViewById(R.id.main_char_name_text_view);
        mCharScriptText = (TextView) findViewById(R.id.main_char_script_text_view);

        mTimeFormatter = new SimpleDateFormat("hh:mm");
        mAmPmFormatter = new SimpleDateFormat("a");
        mDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
    }

    public void initializeTextClock() {
        Date currentDate = new Date();
        mClockTimeText.setText(mTimeFormatter.format(currentDate));
        mClockAmPmText.setText(mAmPmFormatter.format(currentDate));
        mClockDateText.setText(mDateFormatter.format(currentDate));
    }

    public void setImages() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Picasso.with(MainActivity.this)
                .load(R.drawable.bedroom)
                .resize(size.x, size.y)
                .centerCrop()
                .into(mBackground);
        Picasso.with(MainActivity.this)
                .load(R.drawable.kotori_normal)
                .into(mCharImage);
        display = null;
        size = null;
    }

    public void linkIconsWithActivities() {
        mAlarmIcon.setOnClickListener(this);
        mCharSelectIcon.setOnClickListener(this);
        mSettingsIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_alarm_list_icon:
                Intent alarmListIntent = new Intent(MainActivity.this, AlarmListActivity.class);
                startActivity(alarmListIntent);
                break;
            case R.id.main_char_select_icon:
                //TODO Link this icon to char select activity
                break;
            case R.id.main_settings_icon:
                //TODO Link this icon to settings activity
                break;
        }
    }

    public void setTextScript() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);
        if (time <= 10 & time >= 6) {
            mCharScriptText.setText("Good morning!");
        } else if (time <= 13) {
            mCharScriptText.setText("It's lunch time soon! What are you having today?");
        } else if (time <= 18) {
            mCharScriptText.setText("How's it going today? I'm a little bit sleepy... ");
        } else if (time <= 20) {
            mCharScriptText.setText("Please turn on this app more often! I missed you");
        } else if (time <= 24) {
            mCharScriptText.setText("Good night, I can't wait to see you in the morning!");
            Picasso.with(this)
                    .load(R.drawable.kotori_pjs_one)
                    .resize(size.x, size.y)
                    .centerCrop()
                    .into(mCharImage);
        } else if (time > 0 && time < 6) {
            mCharScriptText.setText("Can't sleep? A cup of tea would help.. or check out my outfits!");
            Picasso.with(this)
                    .load(R.drawable.kotori_pjs_three)
                    .resize(size.x, size.y)
                    .centerCrop()
                    .into(mCharImage);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        onMinuteChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    Date date = new Date();
                    mClockTimeText.setText(mTimeFormatter.format(date));
                    mClockAmPmText.setText(mAmPmFormatter.format(date));
                    mClockDateText.setText(mDateFormatter.format(date));
                    date = null;
                }
            }
        };
        registerReceiver(onMinuteChangedReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeTextClock();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (onMinuteChangedReceiver != null) {
            unregisterReceiver(onMinuteChangedReceiver);
        }
    }
}


