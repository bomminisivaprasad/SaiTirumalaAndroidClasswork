package com.example.automationapp.automationapp.Trigger;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.automationapp.automationapp.Action.ActionActivity;
import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;
import com.example.automationapp.automationapp.UnLockDetailReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DTO.Function;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 02/05/2017.
 */

public class UnlockTriggerActivity extends MainActivity {

    private int SHAKE_TAG=7;
    private String UNLOCK_ID="trigger_2";
    private Function function;
    private boolean functionEdit;

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
        CURRENT_ACTIVITY = SHAKE_TAG;
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
                    Intent intent = new Intent(UnlockTriggerActivity.this, TriggerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.activity_unlock);
        View inflatedView = viewStub.inflate();
        final SeekBar seekBar=(SeekBar)inflatedView.findViewById(R.id.seekBar);
        final TextView value=(TextView) inflatedView.findViewById(R.id.seekBarValue);
        AppCompatButton go=(AppCompatButton) inflatedView.findViewById(R.id.goAction);
        int maxFailed=4;
        seekBar.setMax(maxFailed);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value.setText("Value : "+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        if(functionEdit){
            FunctionTrigger functionTrigger=function.getTrigger();
            HashMap<String,String> map=functionTrigger.getParameter();
            if(map.containsKey("times")) {
                int progress =Integer.parseInt(map.get("times"));
                value.setText("Value : " + progress);
                seekBar.setProgress(progress);
            }

        }

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seekBar.getProgress()<1) {
                    Toast.makeText(getApplicationContext(),"The value need at least 1 !",Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("times", seekBar.getProgress() + "");
                    FunctionTrigger functionTrigger = new FunctionTrigger(UNLOCK_ID, map);
                    Intent intent = new Intent(UnlockTriggerActivity.this, ActionActivity.class);
                    intent.putExtra("trigger", functionTrigger);
                    if (functionEdit) {
                        intent.putExtra("functionEdit", functionEdit);
                        intent.putExtra("function", function);
                    }
                    startActivity(intent);
                }
            }
        });
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
