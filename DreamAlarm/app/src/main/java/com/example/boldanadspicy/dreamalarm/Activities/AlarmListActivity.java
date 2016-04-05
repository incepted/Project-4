package com.example.boldanadspicy.dreamalarm.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.boldanadspicy.dreamalarm.ListAdapter.AlarmListAdapter;
import com.example.boldanadspicy.dreamalarm.Objects.AlarmObj;
import com.example.boldanadspicy.dreamalarm.Objects.AlarmSingleton;
import com.example.boldanadspicy.dreamalarm.Objects.Girl;
import com.example.boldanadspicy.dreamalarm.R;
import com.example.boldanadspicy.dreamalarm.Services.AlarmService;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AlarmListActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String TO_SERVICE_ALARM_ID_KEY = "TO_SERVICE_ALARM_ID_KEY";

    private RecyclerView mRecyclerView;
    private AlarmListAdapter mAdapter;

    private int mRawHour, mHour, mMinute,
            mSelectedSnoozeIntervalPosition, mSelectedSnoozeRepeatPosition, mSnoozeRepeats;
    long mSnoozeInterval;
    private String mAmPm;

    boolean sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    //    TimePicker mTimePicker;
    CheckBox mDaySundayTv, mDayMondayTv, mDayTuesdayTv, mDayWednesdayTv,
            mDayThursdayTv, mDayFridayTv, mDaySaturdayTv, mSnoozeCheckBox;
    Spinner mSnoozeIntervalSpinner, mSnoozeTimeSpinner;

    ArrayList<AlarmObj> mAlarms;

    BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.alarm_list_recycler_view);
        FloatingActionButton alarmAddFab = (FloatingActionButton) findViewById(R.id.alarm_list_add_fab);
        alarmAddFab.setOnClickListener(this);

        populateRecyclerView();
    }

    public void populateRecyclerView() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(AlarmListActivity.this).deleteRealmIfMigrationNeeded().build();
        Realm realm = Realm.getInstance(realmConfig);
        RealmResults<AlarmObj> mAlarmSets = realm.where(AlarmObj.class).findAll();
        AlarmSingleton.getInstance().copyToSingletonAlarmList(mAlarmSets);
        mAlarms = AlarmSingleton.getInstance().getAlarms();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mAdapter = new AlarmListAdapter(mAlarms, AlarmListActivity.this, size.x, size.y);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(AlarmListActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        display = null;
        size = null;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alarm_list_add_fab:
                //TODO Launch alarm setting dialog
                launchAlarmSettingDialog();
                break;
            case R.id.alarm_setting_second_dialog_main_text_view:
                launchAlarmSettingDialog();
                break;
            default:
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.alarm_setting_sun_text_view:
                if (isChecked) {
                    mDaySundayTv.setTextColor(Color.parseColor("#4caf50"));
                    sunday = true;
                } else {
                    mDaySundayTv.setTextColor(Color.parseColor("#000000"));
                    sunday = false;
                }
                break;
            case R.id.alarm_setting_mon_text_view:
                if (isChecked) {
                    mDayMondayTv.setTextColor(Color.parseColor("#4caf50"));
                    monday = true;
                } else {
                    mDayMondayTv.setTextColor(Color.parseColor("#000000"));
                    monday = false;
                }
                break;
            case R.id.alarm_setting_tue_text_view:
                if (isChecked) {
                    mDayTuesdayTv.setTextColor(Color.parseColor("#4caf50"));
                    tuesday = true;
                } else {
                    mDayTuesdayTv.setTextColor(Color.parseColor("#000000"));
                    tuesday = false;
                }
                break;
            case R.id.alarm_setting_wed_text_view:
                if (isChecked) {
                    mDayWednesdayTv.setTextColor(Color.parseColor("#4caf50"));
                    wednesday = true;
                } else {
                    mDayWednesdayTv.setTextColor(Color.parseColor("#000000"));
                    tuesday = false;
                }
                break;
            case R.id.alarm_setting_thurs_text_view:
                if (isChecked) {
                    mDayThursdayTv.setTextColor(Color.parseColor("#4caf50"));
                    thursday = true;
                } else {
                    mDayThursdayTv.setTextColor(Color.parseColor("#000000"));
                    thursday = false;
                }
                break;
            case R.id.alarm_setting_fri_text_view:
                if (isChecked) {
                    mDayFridayTv.setTextColor(Color.parseColor("#4caf50"));
                    friday = true;
                } else {
                    mDayFridayTv.setTextColor(Color.parseColor("#000000"));
                    friday = false;
                }
                break;
            case R.id.alarm_setting_sat_text_view:
                if (isChecked) {
                    mDaySaturdayTv.setTextColor(Color.parseColor("#4caf50"));
                    sunday = true;
                } else {
                    mDaySaturdayTv.setTextColor(Color.parseColor("#000000"));
                    sunday = false;
                }
                break;
            case R.id.alarm_setting_snooze_check_box:
                if (isChecked) {
                    mSnoozeTimeSpinner.setClickable(false);
                    mSnoozeIntervalSpinner.setClickable(false);
                } else {
                    mSnoozeTimeSpinner.setClickable(true);
                    mSnoozeIntervalSpinner.setClickable(true);
                }
        }
    }

    public void launchAlarmSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmListActivity.this);
        View dialogView = AlarmListActivity.this
                .getLayoutInflater()
                .inflate(R.layout.alarm_setting_dialog_layout, null, false);
        builder.setView(dialogView);

        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.alarm_setting_dialog_timePicker);

        builder.setTitle("Add an alarm");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mRawHour = timePicker.getHour();
                    mHour = mRawHour;
                    mMinute = timePicker.getMinute();
                    getAmPm(mRawHour);
                } else {
                    timePicker.clearFocus();
                    mRawHour = timePicker.getCurrentHour();
                    mHour = mRawHour;
                    mMinute = timePicker.getCurrentMinute();
                    getAmPm(mRawHour);
                }
                launchSecondAlarmSettingDialog();
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

    public void launchSecondAlarmSettingDialog() {
        AlertDialog.Builder secondBuilder = new AlertDialog.Builder(AlarmListActivity.this);
        View secondDialogView = AlarmListActivity.this.getLayoutInflater().inflate(R.layout.alarm_setting_dialog_layout_second, null, false);
        secondBuilder.setView(secondDialogView);
        secondBuilder.setTitle("Alarm options");

        LinearLayout mainTv = (LinearLayout) secondDialogView.findViewById(R.id.alarm_setting_second_dialog_main_text_view);
        mainTv.setOnClickListener(this);

        TextView timeTv = (TextView) secondDialogView.findViewById(R.id.alarm_setting_second_dialog_time_text_view);
        TextView ampmTv = (TextView) secondDialogView.findViewById(R.id.alarm_setting_second_dialog_ampm_text_view);

        mDaySundayTv = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_sun_text_view);
        mDayMondayTv = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_mon_text_view);
        mDayTuesdayTv = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_tue_text_view);
        mDayWednesdayTv = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_wed_text_view);
        mDayThursdayTv = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_thurs_text_view);
        mDayFridayTv = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_fri_text_view);
        mDaySaturdayTv = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_sat_text_view);
        mSnoozeCheckBox = (CheckBox) secondDialogView.findViewById(R.id.alarm_setting_snooze_check_box);

        mDaySundayTv.setOnCheckedChangeListener(this);
        mDayMondayTv.setOnCheckedChangeListener(this);
        mDayTuesdayTv.setOnCheckedChangeListener(this);
        mDayWednesdayTv.setOnCheckedChangeListener(this);
        mDayThursdayTv.setOnCheckedChangeListener(this);
        mDayFridayTv.setOnCheckedChangeListener(this);
        mDaySaturdayTv.setOnCheckedChangeListener(this);

        mSnoozeIntervalSpinner = (Spinner) secondDialogView.findViewById(R.id.alarm_setting_snooze_interval_spinner);
        mSnoozeTimeSpinner = (Spinner) secondDialogView.findViewById(R.id.alarm_setting_snooze_time_spinner);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(this, R.array.snooze_interval_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.snooze_time_array, android.R.layout.simple_spinner_dropdown_item);
        mSnoozeIntervalSpinner.setAdapter(intervalAdapter);
        mSnoozeTimeSpinner.setAdapter(timeAdapter);
        mSnoozeIntervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedSnoozeIntervalPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSnoozeTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedSnoozeRepeatPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //TODO Retrieve the time that the TimePicker indicates, and set it into the TextView
        timeTv.setText(mHour + ":" + mMinute);
        ampmTv.setText(mAmPm);

        secondBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //TODO Retrieve the spinner data
                if (!mSnoozeCheckBox.isChecked()) {
                    mSelectedSnoozeIntervalPosition = -1;
                    mSelectedSnoozeRepeatPosition = -1;
                }
                retrieveSpinnerValues();

                //TODO Get snooze interval milliseconds

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, mRawHour);
                c.set(Calendar.MINUTE, mMinute);
                c.set(Calendar.SECOND, 0);

                //TODO Make an alarm object and stuff it into the Realm database

//                AlarmObj(Girl mGirl, int mId, long mInterval,
//                int mRepeatingTimes, int mRequestCode, long mTriggerTime,
//                String mTriggerTimeAmPm, int mTriggerTimeHour,
//                int mTriggerTimeMinute, boolean sunday, boolean monday,
//                boolean tuesday,  boolean wednesday, boolean thursday,
//                boolean friday, boolean saturday)
//
                AlarmObj createdAlarm = new AlarmObj(new Girl(0, "Kotori"),
                        mAlarms.size() + 1, mSnoozeInterval, mSnoozeRepeats,
                        mAlarms.size() + 1, c.getTimeInMillis(), mAmPm,
                        mHour, mMinute, sunday, monday, tuesday, wednesday, thursday, friday, saturday);

                RealmConfiguration realmConfig = new RealmConfiguration.Builder(AlarmListActivity.this).deleteRealmIfMigrationNeeded().build();
                Realm realm = Realm.getInstance(realmConfig);
                realm.beginTransaction();
                realm.copyToRealm(createdAlarm);
                realm.commitTransaction();

                //TODO Retrieve realm database results
                //TODO Notify dataset change.
                mAlarms.add(createdAlarm);
                mAdapter.notifyDataSetChanged();

                Toast.makeText(AlarmListActivity.this, "Added an alarm. # of alarms: " + mAlarms.size(), Toast.LENGTH_SHORT).show();

                //TODO START THE SERVICE WITH A DIFFERENT FLAG (ADD)
                Intent alarmAddService = new Intent(AlarmListActivity.this, AlarmService.class);
                alarmAddService.setAction(AlarmService.ADD);
                alarmAddService.putExtra(TO_SERVICE_ALARM_ID_KEY,createdAlarm.getmId());
                startService(alarmAddService);
            }
        });
        secondBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        secondBuilder.create().show();
    }

    public void getAmPm(int hour) {
        if (hour == 0) {
            mHour += 12;
            mAmPm = "AM";
        } else if (hour == 12) {
            mHour = hour;
            mAmPm = "PM";
        } else if (hour > 12) {
            mHour -= 12;
            mAmPm = "PM";
        } else {
            mHour = hour;
            mAmPm = "AM";
        }
    }

    public void retrieveSpinnerValues() {
        switch (mSelectedSnoozeIntervalPosition) { // 5, 10, 15, 20, 30
            case 0:
                mSnoozeInterval = 5 * 1000 * 60;
                break;
            case 1:
                mSnoozeInterval = 10 * 1000 * 60;
                break;
            case 2:
                mSnoozeInterval = 15 * 1000 * 60;
                break;
            case 3:
                mSnoozeInterval = 20 * 1000 * 60;
                break;
            case 4:
                mSnoozeInterval = 30 * 1000 * 60;
                break;
            default:
                break;
        }
        switch (mSelectedSnoozeRepeatPosition) { // 3, 5, 10
            case 0:
                mSnoozeRepeats = 3;
                break;
            case 1:
                mSnoozeRepeats = 5;
                break;
            case 2:
                mSnoozeRepeats = 10;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getStringExtra(AlarmService.REQUERY_KEY).equals("REQERY")){
                    mAdapter.notifyDataSetChanged();
                    int receivedId = intent.getIntExtra(AlarmService.TO_ACTIVITY_ALARM_ID_KEY,-1);
                    RealmConfiguration realmConfig = new RealmConfiguration.Builder(AlarmListActivity.this).deleteRealmIfMigrationNeeded().build();
                    Realm realm = Realm.getInstance(realmConfig);
                    RealmResults<AlarmObj> mAlarmToBeDeleted = realm.where(AlarmObj.class).equalTo("mId",receivedId).findAll();

                    realm.beginTransaction();
                    mAlarmToBeDeleted.get(0).removeFromRealm();
                    realm.commitTransaction();

                    RealmResults<AlarmObj> mAlarmSets = realm.where(AlarmObj.class).findAll();
                    AlarmSingleton.getInstance().clearAlarmList();
                    AlarmSingleton.getInstance().copyToSingletonAlarmList(mAlarmSets);
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
