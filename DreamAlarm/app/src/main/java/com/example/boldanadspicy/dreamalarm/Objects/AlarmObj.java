package com.example.boldanadspicy.dreamalarm.Objects;

import io.realm.RealmObject;

/**
 * Created by Wasabi on 4/4/2016.
 */
public class AlarmObj extends RealmObject {
    private int mId;
    private long mTriggerTime;
    private int mTriggerTimeHour;
    private int mTriggerTimeMinute;
    private String mTriggerTimeAmPm;
    private long mInterval;
    private int mRepeatingTimes;
    private int mRequestCode;
    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private Girl mGirl;
    //private PendingIntent mPendingIntent;
    private boolean isActive;

    public AlarmObj() {
    }

    public AlarmObj(Girl mGirl, int mId, long mInterval,
                    int mRepeatingTimes, int mRequestCode, long mTriggerTime,
                    String mTriggerTimeAmPm, int mTriggerTimeHour,
                    int mTriggerTimeMinute, boolean sunday, boolean monday,
                    boolean tuesday,  boolean wednesday, boolean thursday,
                    boolean friday, boolean saturday) {

        this.mGirl = mGirl;
        this.mId = mId;
        this.mInterval = mInterval;
        this.mRepeatingTimes = mRepeatingTimes;
        this.mRequestCode = mRequestCode;
        this.mTriggerTime = mTriggerTime;
        this.mTriggerTimeAmPm = mTriggerTimeAmPm;
        this.mTriggerTimeHour = mTriggerTimeHour;
        this.mTriggerTimeMinute = mTriggerTimeMinute;
        this.isActive = true;

        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmRequestCode() {
        return mRequestCode;
    }

    public void setmRequestCode(int mRequestCode) {
        this.mRequestCode = mRequestCode;
    }

    public String getmTriggerTimeAmPm() {
        return mTriggerTimeAmPm;
    }

    public void setmTriggerTimeAmPm(String mTriggerTimeAmPm) {
        this.mTriggerTimeAmPm = mTriggerTimeAmPm;
    }

    public int getmTriggerTimeHour() {
        return mTriggerTimeHour;
    }

    public void setmTriggerTimeHour(int mTriggerTimeHour) {
        this.mTriggerTimeHour = mTriggerTimeHour;
    }

    public int getmTriggerTimeMinute() {
        return mTriggerTimeMinute;
    }

    public void setmTriggerTimeMinute(int mTriggerTimeMinute) {
        this.mTriggerTimeMinute = mTriggerTimeMinute;
    }

    public Girl getmGirl() {
        return mGirl;
    }

    public void setmGirl(Girl mGirl) {
        this.mGirl = mGirl;
    }

    public long getmInterval() {
        return mInterval;
    }

    public void setmInterval(long mInterval) {
        this.mInterval = mInterval;
    }
//
//    public PendingIntent getmPendingIntent() {
//        return mPendingIntent;
//    }
//
//    public void setmPendingIntent(PendingIntent mPendingIntent) {
//        this.mPendingIntent = mPendingIntent;
//    }

    public int getmRepeatingTimes() {
        return mRepeatingTimes;
    }

    public void setmRepeatingTimes(int mRepeatingTimes) {
        this.mRepeatingTimes = mRepeatingTimes;
    }

    public long getmTriggerTime() {
        return mTriggerTime;
    }

    public void setmTriggerTime(long mTriggerTime) {
        this.mTriggerTime = mTriggerTime;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }
}
