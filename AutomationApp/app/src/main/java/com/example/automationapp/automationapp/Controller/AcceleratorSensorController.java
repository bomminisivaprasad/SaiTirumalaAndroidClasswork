package com.example.automationapp.automationapp.Controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;

import com.example.automationapp.automationapp.TriggerService;

import java.util.Date;
import java.util.HashMap;

import DTO.Function;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 11/03/2017.
 */

public class AcceleratorSensorController implements SensorEventListener {

    private SensorManager sensorMgr;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private Context mContext;
    private FunctionTrigger mTrigger;
    private Function mFunction;
    private TriggerService mService;
    private Date last_date;
    private int accel=12;

    public AcceleratorSensorController(TriggerService service, FunctionTrigger trigger, Function function){
        mService=service;
        mContext=service.getApplicationContext();
        sensorMgr = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorMgr.registerListener(this,  sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        mTrigger=trigger;
        mFunction=function;
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        last_date=new Date();
    }

    public void unregister(){
        sensorMgr.unregisterListener(this,sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        HashMap<String,String> parameters=mTrigger.getParameter();

        if(parameters.containsKey("accelerator")){
            String value=parameters.get("accelerator");
            accel=Integer.parseInt(value);
        }
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter
        Calendar last = Calendar.getInstance();
        last.setTime(last_date);
        long diff =System.currentTimeMillis()- last.getTimeInMillis();
        if(diff >= 2000) {
            if (mAccel > accel) {
                last_date=new Date();
                mService.startAction(mFunction);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
