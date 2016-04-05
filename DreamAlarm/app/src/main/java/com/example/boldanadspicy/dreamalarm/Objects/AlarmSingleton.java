package com.example.boldanadspicy.dreamalarm.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasabi on 4/4/2016.
 */
public class AlarmSingleton {
    private static AlarmSingleton instance;
    private static ArrayList<AlarmObj> alarms;

    private AlarmSingleton() {
        alarms = new ArrayList<>();
    }

    public static AlarmSingleton getInstance(){
        if(instance == null){
            instance = new AlarmSingleton();
        }
        return instance;
    }

    public ArrayList<AlarmObj> getAlarms() {
        return alarms;
    }

    public void setAlarms(ArrayList<AlarmObj> alarms) {
        AlarmSingleton.alarms = alarms;
    }

    public void addAlarm(AlarmObj newAlarm){
        alarms.add(newAlarm);
    }
    public void copyToSingletonAlarmList(List list){
        alarms.addAll(list);
    }

    public void clearAlarmList() {
        alarms.clear();
    }
    public void removeAlarm(int index) {
        alarms.remove(index);
    }
    public int getAlarmListSize(){
        return alarms.size();
    }
    public AlarmObj getNewlyAddedAlarm(){
        return alarms.get(alarms.size()-1);
    }


}
