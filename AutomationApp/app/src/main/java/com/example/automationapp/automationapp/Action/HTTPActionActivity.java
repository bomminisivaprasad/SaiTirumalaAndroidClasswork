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
import android.widget.EditText;

import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.Function.FunctionCreateActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;

import java.util.HashMap;

import DTO.Function;
import DTO.FunctionAction;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 04/05/2017.
 */

public class HTTPActionActivity extends MainActivity {
    private int HTTP_TAG=13;
    private String HTTP_ID="action_5";
    private Function function;
    private boolean functionEdit;
    private FunctionTrigger trigger;


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
        CURRENT_ACTIVITY = HTTP_TAG;
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
                    Intent intent = new Intent(HTTPActionActivity.this, ActionActivity.class);
                    intent.putExtra("trigger", trigger);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.activity_http);
        View inflatedView = viewStub.inflate();
        final EditText requestURL = (EditText) inflatedView.findViewById(R.id.text);
        AppCompatButton go = (AppCompatButton) inflatedView.findViewById(R.id.goAction);

        if(functionEdit){
            FunctionAction functionAction=function.getAction();
            HashMap<String,String> parameter=functionAction.getParameter();
            if(parameter.containsKey("url")){
                requestURL.setText(parameter.get("url"));
            }
        }

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map=new HashMap<>();
                map.put("url",requestURL.getText().toString());
                FunctionAction action=new FunctionAction(HTTP_ID,map);
                Intent intent=new Intent(HTTPActionActivity.this,FunctionCreateActivity.class);
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
