package com.example.automationapp.automationapp.Action;

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

import com.example.automationapp.automationapp.Action.WIFIActionActivity;
import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import DTO.Action;
import DTO.Function;
import DTO.FunctionTrigger;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laiwf on 02/05/2017.
 */

public class ActionActivity extends MainActivity {
    private int ACTION_TAG=3;
    private RecyclerView mFunctionRecyclerView;
    private GridLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<Action,ActionViewHolder> mFirebaseAdapter;
    private ProgressBar mProgressBar;
    private static ArrayList<Action> actions;
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
                goToAction(function.getAction().getId());
            }
        }



        getSupportActionBar().setTitle("Action");
        CURRENT_ACTIVITY=ACTION_TAG;
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
        actions=new ArrayList<>();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Action,ActionViewHolder>(Action.class, R.layout.item_action_trigger, ActionViewHolder.class, mDatabase.child("action")) {

            @Override
            protected Action parseSnapshot(DataSnapshot snapshot) {
                Action action = null;
                if (snapshot != null) {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    action = super.parseSnapshot(snapshot);
                    actions.add(action);
                }
                return action;
            }

            @Override
            protected void populateViewHolder(ActionViewHolder viewHolder, final Action action, final int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (action != null) {
                    String name = action.getName();
                    if (name.length() > 12) {
                        name = name.substring(0, 12) + "...";
                    }
                    int resourceId = getResources().getIdentifier(action.getIcon(), "mipmap", getPackageName());
                    if(resourceId==0){
                        resourceId = getResources().getIdentifier(action.getIcon(), "drawable", getPackageName());
                    }
                    viewHolder.actionName.setText(name);
                    viewHolder.actionIcon.setImageResource(resourceId);
                    viewHolder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToAction(action.getId());
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

    public static class ActionViewHolder extends RecyclerView.ViewHolder {
        public TextView actionName;
        public final CircleImageView actionIcon;
        public final FrameLayout item;

        public ActionViewHolder(View v) {
            super(v);
            actionName = (TextView) itemView.findViewById(R.id.taskName);
            actionIcon = (CircleImageView) itemView.findViewById(R.id.taskIcon);
            item=(FrameLayout) itemView.findViewById(R.id.taskIcon_layout);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setBackgroundResource(R.color.btn_background);
                    actionIcon.setBackgroundResource(R.color.btn_background);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Action action = actions.get(getAdapterPosition());
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


    public void goToAction(String id){
        Intent intent=null;
        switch (id) {
            case "action_1":
                intent=new Intent(this,WIFIActionActivity.class);
                break;
            case "action_2":
                intent=new Intent(this,TorchLightActionActivity.class);
                break;
            case "action_3":
                intent=new Intent(this,CameraActionActivity.class);
                break;
            case "action_4":
                intent=new Intent(this,VolumeActionActivity.class);
                break;
            case "action_5":
                intent=new Intent(this,HTTPActionActivity.class);
                break;
        }
        if(functionEdit && function!=null){
            intent.putExtra("functionEdit",functionEdit);
            intent.putExtra("function",function);
        }
        if(intent!=null){
            intent.putExtra("trigger",trigger);
            startActivity(intent);
            finish();
        }
    }

}
