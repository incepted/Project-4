package com.example.boldanadspicy.dreamalarm.Objects;

import io.realm.RealmObject;

/**
 * Created by Wasabi on 4/4/2016.
 */
public class Girl extends RealmObject {
    private String mName;
    private long mExp;

    public Girl() {
    }

    public Girl(long mExp, String mName) {
        this.mExp = mExp;
        this.mName = mName;
    }

    public long getmExp() {
        return mExp;
    }

    public void setmExp(long mExp) {
        this.mExp = mExp;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}

