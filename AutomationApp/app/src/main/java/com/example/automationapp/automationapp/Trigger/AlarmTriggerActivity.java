package com.example.automationapp.automationapp.Trigger;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.automationapp.automationapp.Action.ActionActivity;
import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import DTO.Function;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 03/05/2017.
 */

public class AlarmTriggerActivity extends MainActivity {

    private int ALARM_TAG=10;
    private String ALARM_ID="trigger_3";
    private Function function;
    private boolean functionEdit;
    private static TextView timerTime;
    private static TextView dateTime;
    private static int TimerHour;
    private static int TimerMin;
    private static int TimerSec;
    private static int DateHour;
    private static int DateMin;
    private static int DateSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Trigger Set Up");
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey("functionEdit") && extras.containsKey("function")) {
                function = (Function) extras.getSerializable("function");
                functionEdit = true;
            }
        }
        CURRENT_ACTIVITY = ALARM_TAG;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (functionEdit) {
                    finish();
                } else {
                    Intent intent = new Intent(AlarmTriggerActivity.this, TriggerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.activity_alarm);
        View inflatedView = viewStub.inflate();
        timerTime=(TextView)inflatedView.findViewById(R.id.timer_time);
        dateTime=(TextView)inflatedView.findViewById(R.id.date_time);
        AppCompatButton timerButton=(AppCompatButton)inflatedView.findViewById(R.id.timer_button);
        AppCompatButton dateTimeButton=(AppCompatButton)inflatedView.findViewById(R.id.dataTime_button);
        AppCompatButton timerGoButton=(AppCompatButton)inflatedView.findViewById(R.id.timerGoFunction);
        AppCompatButton dateTimeGoButton=(AppCompatButton)inflatedView.findViewById(R.id.DateTimeGoFunction);

        if(functionEdit){
            FunctionTrigger functionTrigger=function.getTrigger();
            HashMap<String,String> map=functionTrigger.getParameter();
            if(map.containsKey("hour") && map.containsKey("min") && map.containsKey("sec") && map.containsKey("type")) {
                int hour =Integer.parseInt(map.get("hour"));
                int min =Integer.parseInt(map.get("min"));
                int sec =Integer.parseInt(map.get("sec"));
                String hours=hour+"";
                String mins=min+"";
                String secs=sec+"";
                String type=map.get("type");
                TimerHour=hour;
                TimerMin=min;
                TimerSec=sec;

                if (hour < 10)
                    hours = "0" + hour;
                if (min < 10)
                    mins = "0" + min;
                if (sec < 10)
                    secs = "0" + sec;


                if(type.equalsIgnoreCase("timer")) {
                    String timer=hours+"h "+mins+"m "+secs+"s";
                    timerTime.setText(timer);
                }else{
                    String time=hours+": "+mins+": "+secs;
                    dateTime.setText(time);
                }
            }
        }

        timerGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerTime.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The value cannot be empty!", Toast.LENGTH_SHORT).show();
                }else if(TimerHour==0 && TimerMin==0 && TimerSec==0){
                    Toast.makeText(getApplicationContext(), "The value cannot be all zero!", Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("hour", TimerHour + "");
                    map.put("min", TimerMin + "");
                    map.put("sec", TimerSec + "");
                    map.put("type", "timer");
                    FunctionTrigger functionTrigger = new FunctionTrigger(ALARM_ID, map);
                    Intent intent = new Intent(AlarmTriggerActivity.this, ActionActivity.class);
                    intent.putExtra("trigger", functionTrigger);
                    if (functionEdit) {
                        intent.putExtra("functionEdit", functionEdit);
                        intent.putExtra("function", function);
                    }
                    startActivity(intent);
                }
            }
        });

        dateTimeGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateTime.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"The value cannot be empty!",Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("hour", DateHour + "");
                    map.put("min", DateMin + "");
                    map.put("sec", DateSec + "");
                    map.put("type", "time");
                    map.put("repeat", "true");
                    FunctionTrigger functionTrigger = new FunctionTrigger(ALARM_ID, map);
                    Intent intent = new Intent(AlarmTriggerActivity.this, ActionActivity.class);
                    intent.putExtra("trigger", functionTrigger);
                    if (functionEdit) {
                        intent.putExtra("functionEdit", functionEdit);
                        intent.putExtra("function", function);
                    }
                    startActivity(intent);
                }
            }
        });
        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPickerDialog();
            }
        });
    }

    private static String timeString;

    private static void setTimeString(int hourOfDay, int minute) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;
        DateHour=hourOfDay;
        DateMin=minute;
        DateSec=00;
        timeString = hour + ":" + min + ":00";
    }


    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void showNumberPickerDialog() {
        DialogFragment newFragment = new NumberPickerFragment();
        newFragment.show(getSupportFragmentManager(), "numberPicker");
    }

    // DialogFragment used to pick a ToDoItem deadline time

    public static class NumberPickerFragment extends DialogFragment implements
            NumberPicker.OnValueChangeListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog, null);

            final NumberPicker hour = (NumberPicker) view.findViewById(R.id.hour);
            hour.setMaxValue(24); // max value 100
            hour.setMinValue(0);   // min value 0
            hour.setWrapSelectorWheel(false);
            hour.setOnValueChangedListener(this);

            final NumberPicker min = (NumberPicker) view.findViewById(R.id.min);
            min.setMaxValue(60); // max value 100
            min.setMinValue(0);   // min value 0
            min.setWrapSelectorWheel(false);
            min.setOnValueChangedListener(this);

            final NumberPicker sec = (NumberPicker) view.findViewById(R.id.sec);
            sec.setMaxValue(60); // max value 100
            sec.setMinValue(0);   // min value 0
            sec.setWrapSelectorWheel(false);
            sec.setOnValueChangedListener(this);


            AlertDialog builder=new AlertDialog.Builder(getContext())
                    .setView(view)
                    .setTitle("Timer")
                    .setCancelable(true)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            String hours=hour.getValue()+"";
                            String mins=min.getValue()+"";
                            String secs=sec.getValue()+"";

                            TimerHour=hour.getValue();
                            TimerMin=min.getValue();
                            TimerSec=sec.getValue();

                            if (hour.getValue() < 10)
                                hours = "0" + hour.getValue();
                            if (min.getValue() < 10)
                                mins = "0" + min.getValue();
                            if (sec.getValue() < 10)
                                secs = "0" + sec.getValue();

                            String timer=hours+"h "+mins+"m "+secs+"s";
                            timerTime.setText(timer);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            return builder;
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    }
    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute);
            dateTime.setText(timeString);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_quit) {
            Intent intent=new Intent(getApplicationContext(),FunctionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
