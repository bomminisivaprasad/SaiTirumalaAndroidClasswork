package com.example.automationapp.automationapp.Function;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;
import com.example.automationapp.automationapp.Trigger.TriggerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DTO.Action;
import DTO.Function;
import DTO.Trigger;

/**
 * Created by laiwf on 02/05/2017.
 */

public class FunctionViewActivity extends MainActivity {

    private int FUNCTION_VIEW=6;
    private String FUNCTION_VIEW_TAG="FUNCTION_VIEW";
    private Function function;
    private int position;
    private static ArrayList<Action> actions;
    private static ArrayList<Trigger> triggers;
    private ProgressBar mProgressBar;
    private String triggerName;
    private String actionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey("position")) {
                position = (int)extras.get("position");
                function=functions.get(position);
            }
        }

        getSupportActionBar().setTitle(function.getName());
        CURRENT_ACTIVITY = FUNCTION_VIEW;
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
        viewStub.setLayoutResource(R.layout.activity_function_view);
        final View inflatedView = viewStub.inflate();
        mProgressBar = (ProgressBar) inflatedView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);



        mDatabase.child("action").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    System.out.println(dataSnapshot.getValue());
                    GenericTypeIndicator<ArrayList<Action>> functionType = new GenericTypeIndicator<ArrayList<Action>>() {
                    };
                    actions = dataSnapshot.getValue(functionType);
                    mDatabase.child("trigger").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {
                                System.out.println(dataSnapshot.getValue());
                                GenericTypeIndicator<ArrayList<Trigger>> functionType = new GenericTypeIndicator<ArrayList<Trigger>>() {
                                };
                                triggers = dataSnapshot.getValue(functionType);
                                setUpView(inflatedView);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i(FUNCTION_VIEW_TAG, "startService onCancelled " + databaseError.getDetails());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(FUNCTION_VIEW_TAG, "startService onCancelled " + databaseError.getDetails());
            }
        });


    }

    public void setUpView(View inflatedView){
        TextView trigger = (TextView) inflatedView.findViewById(R.id.trigger);
        TextView action = (TextView) inflatedView.findViewById(R.id.action);
        TextView desc = (TextView) inflatedView.findViewById(R.id.desc);
        ImageView delete_view = (ImageView) inflatedView.findViewById(R.id.delete);
        ImageView edit_view = (ImageView) inflatedView.findViewById(R.id.edit);


        String actionName="";
        for(Action act:actions){
            if(act.getId().equals(function.getAction().getId())){
                actionName=act.getName();
            }
        }


        String triggerName="";
        for(Trigger trig:triggers){
            if(trig.getId().equals(function.getTrigger().getId())){
                triggerName=trig.getName();
            }
        }
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        trigger.setText(triggerName);
        action.setText(actionName);
        desc.setText(function.getDesc());


        delete_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 =new AlertDialog.Builder(FunctionViewActivity.this);
                builder2.setTitle("Are you sure you want to delete?");
                builder2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).child(function.getId()).removeValue();
                        Toast.makeText(FunctionViewActivity.this,"Function Deleted !",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog adialog=builder2.create();
                adialog.show();
            }
        });

        edit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TriggerActivity.class);
                intent.putExtra("functionEdit", "true");
                intent.putExtra("function", functions.get(position));
                v.getContext().startActivity(intent);
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
