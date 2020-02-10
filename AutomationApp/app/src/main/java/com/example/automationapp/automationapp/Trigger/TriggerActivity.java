package com.example.automationapp.automationapp.Trigger;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import DTO.Function;
import DTO.Trigger;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laiwf on 01/05/2017.
 */

public class TriggerActivity extends MainActivity {

    private int TRIGGER_TAG=1;
    private RecyclerView mFunctionRecyclerView;
    private GridLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<Trigger,TriggerViewHolder> mFirebaseAdapter;
    private ProgressBar mProgressBar;
    private static ArrayList<Trigger> triggers;
    private Function function;
    private boolean  functionEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            Bundle extras=getIntent().getExtras();
            if(extras!=null  && extras.containsKey("functionEdit") && extras.containsKey("function")) {
                function = (Function) extras.getSerializable("function");
                functionEdit=true;
                goToTrigger(function.getTrigger().getId());
            }
        }
        getSupportActionBar().setTitle("Trigger");
        CURRENT_ACTIVITY=TRIGGER_TAG;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,  drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewStub viewStub=(ViewStub)findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.content_main);
        View inflatedView = viewStub.inflate();

        mFunctionRecyclerView = (RecyclerView) inflatedView.findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new GridLayoutManager(this,3);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProgressBar = (ProgressBar) inflatedView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.signIn), PorterDuff.Mode.MULTIPLY);
//        mLinearLayoutManager.setStackFromEnd(true);
        mFunctionRecyclerView.setLayoutManager(mLinearLayoutManager);
        triggers=new ArrayList<>();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Trigger, TriggerViewHolder>(Trigger.class, R.layout.item_action_trigger, TriggerViewHolder.class, mDatabase.child("trigger")) {

            @Override
            protected Trigger parseSnapshot(DataSnapshot snapshot) {
                Trigger trigger = null;
                if (snapshot != null) {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    trigger = super.parseSnapshot(snapshot);
                    triggers.add(trigger);
                }
                return trigger;
            }

            @Override
            protected void populateViewHolder(TriggerViewHolder viewHolder, final Trigger trigger, final int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (trigger != null) {
                    String name = trigger.getName();
                    if (name.length() > 12) {
                        name = name.substring(0, 12) + "...";
                    }
                    int resourceId = getResources().getIdentifier(trigger.getIcon(), "mipmap", getPackageName());
                    if(resourceId==0){
                        resourceId = getResources().getIdentifier(trigger.getIcon(), "drawable", getPackageName());
                    }
                    viewHolder.triggerName.setText(name);
                    viewHolder.triggerIcon.setImageResource(resourceId);
                    viewHolder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToTrigger(trigger.getId());
                            finish();
                        }
                    });
                }
            }
        };

        mFunctionRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFunctionRecyclerView.setAdapter(mFirebaseAdapter);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        mFunctionRecyclerView.addItemDecoration(itemDecoration);
    }

    public static class TriggerViewHolder extends RecyclerView.ViewHolder {
        public TextView triggerName;
        public final CircleImageView triggerIcon;
        public final FrameLayout item;

        public TriggerViewHolder(View v) {
            super(v);
            triggerName = (TextView) itemView.findViewById(R.id.taskName);
            triggerIcon = (CircleImageView) itemView.findViewById(R.id.taskIcon);
            item=(FrameLayout) itemView.findViewById(R.id.taskIcon_layout);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setBackgroundResource(R.color.btn_background);
                    triggerIcon.setBackgroundResource(R.color.btn_background);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Trigger trigger = triggers.get(getAdapterPosition());
                        // We can access the data within the views
//                        Toast.makeText(mContext, function.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


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

    public void goToTrigger(String id){
        Intent intent=null;
        switch (id) {
            case "trigger_1":
                intent=new Intent(this,ShakeTriggerActivity.class);
                break;
            case "trigger_2":
                intent=new Intent(this,UnlockTriggerActivity.class);
                break;
            case "trigger_3":
                intent=new Intent(this,AlarmTriggerActivity.class);
                break;
            case "trigger_4":
                intent=new Intent(this,SMSTriggerActivity.class);
                break;

        }
        if(functionEdit && function!=null){
            intent.putExtra("functionEdit",functionEdit);
            intent.putExtra("function",function);
        }
        if(intent!=null){
            startActivity(intent);
            finish();
        }
    }
}
