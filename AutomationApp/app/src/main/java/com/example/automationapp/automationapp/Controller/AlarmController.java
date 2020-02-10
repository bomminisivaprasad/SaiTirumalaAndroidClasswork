package com.example.automationapp.automationapp.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.automationapp.automationapp.TriggerService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import DTO.Function;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 02/05/2017.
 */

public class AlarmController {
    private TriggerService mService;
    private Context mContext;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Function function;
    private Intent intent;
    private static final String TAG = "AlarmController";
    private long millsec;
    private boolean repeat;

    public AlarmController(TriggerService service, Function function){
        mService=service;
        mContext=service.getApplicationContext();
        this.function=function;
        intent=new Intent();
        intent.setAction("com.Automation.AlarmController");
        alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);
        setAlarm(function);

    }

    public void stop(){
        alarmManager.cancel(pendingIntent);
    }

    public void update(Function function){
        pendingIntent = PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.function=function;
        setAlarm(function);
    }

    private void setAlarm(final Function function){
        FunctionTrigger trigger=function.getTrigger();
        HashMap<String,String> parameters=trigger.getParameter();

        if(parameters.containsKey("hour") && parameters.containsKey("min") && parameters.containsKey("sec") && parameters.containsKey("type")) {
            int hour=Integer.parseInt(parameters.get("hour"));
            int min=Integer.parseInt(parameters.get("min"));
            int sec=Integer.parseInt(parameters.get("sec"));
            String type=parameters.get("type");

            if(type.equalsIgnoreCase("timer")){
                min+=hour*60;
                sec+=min*60;
                millsec=sec*1000;
                millsec = System.currentTimeMillis()+millsec;
                alarmManager.set(AlarmManager.RTC_WAKEUP, millsec, PendingIntent.getBroadcast(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
            }else if(type.equalsIgnoreCase("time") && parameters.containsKey("repeat")){
                repeat=Boolean.parseBoolean(parameters.get("repeat"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
                calendar.set(Calendar.SECOND,sec);
                millsec=calendar.getTimeInMillis();
                if(repeat) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millsec,  AlarmManager.INTERVAL_DAY,pendingIntent);
                }else{
                    alarmManager.set(AlarmManager.RTC_WAKEUP, millsec, PendingIntent.getBroadcast(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                }
            }
        }

    }

    public boolean isRepeat(){
        return repeat;
    }

    public long getTime(){
        return millsec;
    }

    public Function getFunction(){
        return function;
    }
}
