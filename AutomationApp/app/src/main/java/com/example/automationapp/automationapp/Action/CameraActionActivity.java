package com.example.automationapp.automationapp.Action;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.Function.FunctionCreateActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;
import com.example.automationapp.automationapp.Trigger.TriggerActivity;

import DTO.Function;
import DTO.FunctionAction;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 02/05/2017.
 */

public class CameraActionActivity extends MainActivity {

    private int CAMERA_TAG=9;
    private String CAMERA_ID="action_3";
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
        CURRENT_ACTIVITY=CAMERA_TAG;

        FunctionAction action=new FunctionAction(CAMERA_ID,null);
        Intent intent=new Intent(CameraActionActivity.this,FunctionCreateActivity.class);
        intent.putExtra("trigger",trigger);
        intent.putExtra("action",action);
        if(functionEdit){
            intent.putExtra("functionEdit",functionEdit);
            intent.putExtra("function",function);
        }
        startActivity(intent);
        finish();
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
