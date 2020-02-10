package com.example.automationapp.automationapp.Trigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.example.automationapp.automationapp.Action.ActionActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;

import java.util.HashMap;

import DTO.Function;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 03/05/2017.
 */

public class SMSTriggerActivity extends MainActivity {
    private int SMS_TAG=11;
    private String SMS_ID="trigger_4";
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
        CURRENT_ACTIVITY = SMS_TAG;
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
                    Intent intent = new Intent(SMSTriggerActivity.this, TriggerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.activity_message);
        View inflatedView = viewStub.inflate();
        final TextView text=(TextView)inflatedView.findViewById(R.id.text);
        AppCompatButton go=(AppCompatButton)inflatedView.findViewById(R.id.goAction);
        if(functionEdit){
            FunctionTrigger functionTrigger=function.getTrigger();
            HashMap<String,String> map=functionTrigger.getParameter();
            if(map.containsKey("text")) {
                text.setText(map.get("text"));
            }
        }
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"The value need at least 5 !",Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("text", text.getText().toString().trim());
                    FunctionTrigger functionTrigger = new FunctionTrigger(SMS_ID, map);
                    Intent intent = new Intent(SMSTriggerActivity.this, ActionActivity.class);
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
}
