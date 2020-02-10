package com.example.automationapp.automationapp.Function;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import DTO.Function;
import DTO.FunctionAction;
import DTO.FunctionTrigger;

/**
 * Created by laiwf on 02/05/2017.
 */

public class FunctionCreateActivity extends MainActivity {

    private int WIFI_TAG=5;
    private FunctionAction action;
    private FunctionTrigger trigger;
    private boolean statusCheck;
    private Function function;
    private boolean functionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null){
            Bundle extras=getIntent().getExtras();
            if(extras!=null  && extras.containsKey("trigger") && extras.containsKey("action")) {
                trigger = (FunctionTrigger) extras.getSerializable("trigger");
                action = (FunctionAction) extras.getSerializable("action");
            }
        }

        if(savedInstanceState==null){
            Bundle extras=getIntent().getExtras();
            if(extras!=null  && extras.containsKey("functionEdit") && extras.containsKey("function")) {
                function = (Function) extras.getSerializable("function");
                functionEdit=true;
            }
        }


        getSupportActionBar().setTitle("Function Set Up");
        CURRENT_ACTIVITY = WIFI_TAG;
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
                finish();
            }
        });
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.activity_function_create);
        View inflatedView = viewStub.inflate();
        final TextView name=(TextView)inflatedView.findViewById(R.id.name);
        final TextView desc=(TextView)inflatedView.findViewById(R.id.desc);
        final TextView fail=(TextView)inflatedView.findViewById(R.id.fail);
        final TextView success=(TextView)inflatedView.findViewById(R.id.success);
        final Switch status= (Switch)inflatedView.findViewById(R.id.status);
        AppCompatButton go=(AppCompatButton)inflatedView.findViewById(R.id.done);
        if(functionEdit){
            name.setText(function.getName());
            desc.setText(function.getDesc());
            fail.setText(function.getFail());
            success.setText(function.getSuccess());
            boolean statusValue=function.getStatus().equalsIgnoreCase("ON")?true:false;
            setStatusCheck(statusValue);
            status.setChecked(statusValue);

        }
        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setStatusCheck(isChecked);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().isEmpty()){
                    name.setError("Name cannot be empty");
                }else if(desc.getText().toString().trim().isEmpty()){
                    desc.setError("Description cannot be empty");
                }else if(success.getText().toString().trim().isEmpty()){
                    success.setError("Success message cannot be empty");
                }else if(fail.getText().toString().trim().isEmpty()){
                    fail.setError("Failure Message cannot be empty");
                }else{
                    final String status_value=isStatusCheck()?"on":"off";
                    if(functionEdit){
                        HashMap<String,Object> update=new HashMap<String, Object>();
                        update.put("name",name.getText().toString());
                        update.put("desc",desc.getText().toString());
                        update.put("fail",fail.getText().toString());
                        update.put("success",success.getText().toString());
                        update.put("status",status_value);
                        update.put("action",action);
                        update.put("trigger",trigger);
                        mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).child(function.getId()+"").updateChildren(update);
                        Toast.makeText(getApplicationContext(),"Function Edited !",Toast.LENGTH_SHORT).show();
                    }else{
                        Date date=new Date();
                        Function func=new Function("id",name.getText().toString(),desc.getText().toString(), fail.getText().toString(), success.getText().toString(),status_value,date.toString(), action, trigger);
                        mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).push().setValue(func);
                        Toast.makeText(getApplicationContext(),"Function Added !",Toast.LENGTH_SHORT).show();
                    }
                    Intent intent=new Intent(FunctionCreateActivity.this,FunctionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
//                    finish();
                }
            }
        });


    }


    public boolean isStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(boolean statusCheck) {
        this.statusCheck = statusCheck;
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
