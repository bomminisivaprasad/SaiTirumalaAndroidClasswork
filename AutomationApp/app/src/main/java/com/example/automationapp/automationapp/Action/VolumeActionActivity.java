package com.example.automationapp.automationapp.Action;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.automationapp.automationapp.Controller.VolumeController;
import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.Function.FunctionCreateActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;

import java.util.HashMap;

import DTO.Function;
import DTO.FunctionAction;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 03/05/2017.
 */

public class VolumeActionActivity extends MainActivity{

    private int VOLUME_TAG=12;
    private String VOLUME_ID="action_4";
    private int music;
    private int ring;
    private int alarm;
    private FunctionTrigger trigger;
    private Function function;
    private boolean functionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null){
            Bundle extras=getIntent().getExtras();
            if(extras!=null  && extras.containsKey("trigger")) {
                trigger = (FunctionTrigger) extras.getSerializable("trigger");
            }
        }
        if(savedInstanceState==null){
            Bundle extras=getIntent().getExtras();
            if(extras!=null  && extras.containsKey("functionEdit") && extras.containsKey("function")) {
                function = (Function) extras.getSerializable("function");
                functionEdit=true;
            }
        }
        getSupportActionBar().setTitle("Action Set Up");
        CURRENT_ACTIVITY = VOLUME_TAG;
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
                if(functionEdit){
                    finish();
                }else {
                    Intent intent = new Intent(VolumeActionActivity.this, ActionActivity.class);
                    intent.putExtra("trigger", trigger);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.activity_volume);
        View inflatedView = viewStub.inflate();
        SeekBar music_bar = (SeekBar) inflatedView.findViewById(R.id.music);
        SeekBar ring_bar = (SeekBar) inflatedView.findViewById(R.id.ring);
        SeekBar alarm_bar = (SeekBar) inflatedView.findViewById(R.id.alarm);

        final TextView music_value=(TextView) inflatedView.findViewById(R.id.music_value);
        final TextView ring_value=(TextView) inflatedView.findViewById(R.id.ring_value);
        final TextView alarm_value=(TextView) inflatedView.findViewById(R.id.alarm_value);
        AppCompatButton go = (AppCompatButton) inflatedView.findViewById(R.id.goFunction);
        VolumeController volumeController=new VolumeController(getApplicationContext());
        music_bar.setMax(volumeController.getMusicMaxVolume());
        ring_bar.setMax(volumeController.getRingMaxVolume());
        alarm_bar.setMax(volumeController.getAlarmMaxVolume());

        if(functionEdit){
            FunctionAction functionAction=function.getAction();
            HashMap<String,String> parameter=functionAction.getParameter();
            if(parameter.containsKey("music") && parameter.containsKey("ring") && parameter.containsKey("alarm")){
                music=Integer.parseInt(parameter.get("music"));
                ring=Integer.parseInt(parameter.get("ring"));
                alarm=Integer.parseInt(parameter.get("alarm"));
                music_bar.setProgress(music);
                ring_bar.setProgress(ring);
                alarm_bar.setProgress(alarm);
                music_value.setText("Value : "+music);
                ring_value.setText("Value : "+ring);
                alarm_value.setText("Value : "+alarm);
            }
        }else{
            music=volumeController.getMusicVolume();
            ring=volumeController.getRingVolume();
            alarm=volumeController.getAlarmVolume();
            music_bar.setProgress(music);
            ring_bar.setProgress(ring);
            alarm_bar.setProgress(alarm);
            music_value.setText("Value : "+music);
            ring_value.setText("Value : "+ring);
            alarm_value.setText("Value : "+alarm);
        }

        music_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                music_value.setText("Value : "+progress);
                setMusic(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ring_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ring_value.setText("Value : "+progress);
                setRing(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        alarm_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alarm_value.setText("Value : "+progress);
                setAlarm(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("music",getMusic()+"");
                map.put("ring",getRing()+"");
                map.put("alarm",getAlarm()+"");
                FunctionAction action=new FunctionAction(VOLUME_ID,map);
                Intent intent=new Intent(VolumeActionActivity.this,FunctionCreateActivity.class);
                intent.putExtra("trigger",trigger);
                intent.putExtra("action",action);
                if(functionEdit){
                    intent.putExtra("functionEdit",functionEdit);
                    intent.putExtra("function",function);
                }
                startActivity(intent);
            }
        });
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
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
