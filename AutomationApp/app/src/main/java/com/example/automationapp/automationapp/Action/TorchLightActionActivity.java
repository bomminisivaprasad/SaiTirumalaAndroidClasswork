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
import android.widget.Switch;
;
import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.Function.FunctionCreateActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;


import java.util.HashMap;

import DTO.Function;
import DTO.FunctionAction;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 02/05/2017.
 */

public class TorchLightActionActivity extends MainActivity {

    private int TORCHLIGHT_TAG=8;
    private String TORCHLIGHT_ID="action_2";
    private Function function;
    private boolean functionEdit;
    private FunctionTrigger trigger;
    private boolean torchlight_check;
    private boolean reverse_check;

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
        CURRENT_ACTIVITY = TORCHLIGHT_TAG;
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
                    Intent intent = new Intent(TorchLightActionActivity.this, ActionActivity.class);
                    intent.putExtra("trigger", trigger);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.activity_torchlight);
        View inflatedView = viewStub.inflate();
        Switch torchlight_switch = (Switch) inflatedView.findViewById(R.id.Torchlight_switch);
        Switch reverse_switch = (Switch) inflatedView.findViewById(R.id.Reverse_switch);
        AppCompatButton go = (AppCompatButton) inflatedView.findViewById(R.id.goFunction);

        if(functionEdit){
            FunctionAction functionAction=function.getAction();
            HashMap<String,String> parameter=functionAction.getParameter();
            if(parameter.containsKey("reverse") && parameter.containsKey("status")){
                boolean reverse=Boolean.parseBoolean(parameter.get("reverse"));
                boolean status=parameter.get("status").equalsIgnoreCase("ON")?true:false;
                setTorchlight_check(status);
                setReverse_check(reverse);
                torchlight_switch.setChecked(status);
                reverse_switch.setChecked(reverse);
            }
        }

        torchlight_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setTorchlight_check(isChecked);
            }
        });

        reverse_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setReverse_check(isChecked);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map=new HashMap<String, String>();
                String wifi=isTorchlight_check()?"on":"off";
                map.put("reverse",isReverse_check()+"");
                map.put("status",wifi+"");
                FunctionAction action=new FunctionAction(TORCHLIGHT_ID,map);
                Intent intent=new Intent(TorchLightActionActivity.this,FunctionCreateActivity.class);
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

    public boolean isTorchlight_check() {
        return torchlight_check;
    }

    public void setTorchlight_check(boolean torchlight_check) {
        this.torchlight_check = torchlight_check;
    }

    public boolean isReverse_check() {
        return reverse_check;
    }

    public void setReverse_check(boolean reverse_check) {
        this.reverse_check = reverse_check;
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
