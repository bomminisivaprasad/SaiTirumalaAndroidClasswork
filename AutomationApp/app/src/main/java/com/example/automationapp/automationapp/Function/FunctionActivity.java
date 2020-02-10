package com.example.automationapp.automationapp.Function;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;
import com.example.automationapp.automationapp.Trigger.TriggerActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

import DTO.Action;
import DTO.Function;
import DTO.Trigger;

/**
 * Created by laiwf on 20/02/2017.
 */

public class FunctionActivity extends MainActivity {

    private final int ACTIVITY_ID = R.id.nav_function;
    private final String ACTIVITY_TAG = "FunctionActivity";
    private RecyclerView mFunctionRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<Function, FunctionViewHolder> mFirebaseAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.nav_function);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(ACTIVITY_ID);
        CURRENT_ACTIVITY = ACTIVITY_ID;
        ViewStub viewStub=(ViewStub)findViewById(R.id.stub_layout);
        viewStub.setLayoutResource(R.layout.content_main);
        View inflatedView = viewStub.inflate();

        mFunctionRecyclerView = (RecyclerView) inflatedView.findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);

        mProgressBar = (ProgressBar) inflatedView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.signIn), PorterDuff.Mode.MULTIPLY);
//        mLinearLayoutManager.setStackFromEnd(true);
        mFunctionRecyclerView.setLayoutManager(mLinearLayoutManager);
        functions = new ArrayList<>();
        actions = new ArrayList<>();
        triggers = new ArrayList<>();
        setUpAction();

    }

    public void setUpAction(){
        mDatabase.child("action").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    GenericTypeIndicator<ArrayList<Action>> functionType = new GenericTypeIndicator<ArrayList<Action>>() {};
                    actions = dataSnapshot.getValue(functionType);
                    setUpTrigger();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(ACTIVITY_TAG, "startService onCancelled " + databaseError.getDetails());
            }
        });
    }

    public void setUpTrigger(){
        mDatabase.child("trigger").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    GenericTypeIndicator<ArrayList<Trigger>> functionType = new GenericTypeIndicator<ArrayList<Trigger>>() {};
                    triggers = dataSnapshot.getValue(functionType);
                    setUpFunction();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(ACTIVITY_TAG, "startService onCancelled " + databaseError.getDetails());
            }
        });
    }

    public void setUpFunction(){
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Function, FunctionViewHolder>(Function.class, R.layout.item_function, FunctionViewHolder.class, mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid())) {

            @Override
            protected Function parseSnapshot(DataSnapshot snapshot) {
                Function function = null;
                if (snapshot.getValue() != null) {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    function = super.parseSnapshot(snapshot);
                    function.setId(snapshot.getKey());
                    functions.add(function);
                }
                return function;
            }

            @Override
            protected void populateViewHolder(FunctionViewHolder viewHolder, Function function, final int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (function != null) {
                    String name = function.getName();

                    if (name.length() > 40) {
                        name = name.substring(0, 40) + "...";
                    }
                    String desc = function.getDesc();
                    if (desc.length() > 40) {
                        desc = desc.substring(0, 40) + "...";
                    }
                    viewHolder.functionName.setText(name);
                    viewHolder.functionDesc.setText(desc);
                    viewHolder.functionStatus.setChecked(function.getStatus().equalsIgnoreCase("on"));
                    viewHolder.functionIcon.setImageBitmap(setImage(position));

                    if(function.getAction().getParameter()!=null){
                        HashMap<String,String> parameters=function.getAction().getParameter();
                        Trigger trigger=findTrigger(function.getTrigger().getId());
                        Action action=findAction(function.getAction().getId());
                        int resourceId = getResources().getIdentifier(action.getIcon(), "drawable", getPackageName());
                        viewHolder.actionIcon.setImageResource(resourceId);
                        resourceId = getResources().getIdentifier(trigger.getIcon(), "drawable", getPackageName());
                        viewHolder.triggerIcon.setImageResource(resourceId);
                        String linkIconName="ic_right_arrow";
                        if(parameters.containsKey("reverse") && parameters.get("reverse").equalsIgnoreCase("true")){
                            linkIconName="ic_two_side_arrow";
                        }
                        resourceId = getResources().getIdentifier(linkIconName, "drawable", getPackageName());
                        viewHolder.linkIcon.setImageResource(resourceId);
                    }else{
                        viewHolder.actionIcon.setVisibility(View.GONE);
                        viewHolder.triggerIcon.setVisibility(View.GONE);
                        viewHolder.linkIcon.setVisibility(View.GONE);
                    }
                }
            }
        };


        mFunctionRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFunctionRecyclerView.setAdapter(mFirebaseAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mFunctionRecyclerView.addItemDecoration(itemDecoration);
    }

    public Trigger findTrigger(String id){
        for(Trigger trigger:triggers){
            if(trigger.getId().equalsIgnoreCase(id)){
                return trigger;
            }
        }
        return null;
    }

    public Action findAction(String id){
        for(Action action:actions){
            if(action.getId().equalsIgnoreCase(id)){
                return action;
            }
        }
        return null;
    }

    public Bitmap setImage(int num){
//        ImageView image=(ImageView) itemLayout.findViewById(R.id.sender_image);

        Resources res = getApplicationContext().getResources();
        Bitmap bm = BitmapFactory.decodeResource(res, R.mipmap.ic_fire);

        Bitmap.Config config = bm.getConfig();
        int width = bm.getWidth();
        int height = bm.getHeight();

        Bitmap newImage = Bitmap.createBitmap(width, height, config);

        Canvas c = new Canvas(newImage);
        c.drawBitmap(bm, 100, 100, null);
        String[] colors={"F44336","E91E63","9C27B0","673AB7","3F51B5","2196F3","03A9F4","00BCD4","009688","4CAF50","8BC34A","CDDC39","FFEB3B","FFC107","FF9800","FF5722"};
        Paint paint = new Paint();

        if(num>=colors.length-1){
            num=num%colors.length;
        }
        paint.setColor(Color.parseColor("#"+colors[num]));
        paint.setStyle(Paint.Style.FILL);

        c.drawCircle(newImage.getWidth()/2,newImage.getHeight()/2,newImage.getWidth(),paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(60);
        String id=(num+1)+"";
        float textWidths=paint.measureText(id);
        float textHeight=paint.getFontMetrics().top;

        c.drawText(id, (c.getWidth()-textWidths)/2, 4*(c.getHeight()-textHeight)/9, paint);

        return getRoundedRectBitmap(newImage);
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(bitmap.getHeight()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

    public static class FunctionViewHolder extends RecyclerView.ViewHolder {
        public TextView functionName;
        public TextView functionDesc;
        public ImageView functionIcon;
        public Switch functionStatus;
        public ImageView triggerIcon;
        public ImageView actionIcon;
        public ImageView linkIcon;

        public FunctionViewHolder(View v) {
            super(v);
            functionName = (TextView) itemView.findViewById(R.id.functionName);
            functionDesc = (TextView) itemView.findViewById(R.id.functionDesc);
            functionIcon = (ImageView) itemView.findViewById(R.id.iconImage);
            triggerIcon = (ImageView) itemView.findViewById(R.id.iconTrigger);
            actionIcon = (ImageView) itemView.findViewById(R.id.iconAction);
            linkIcon = (ImageView) itemView.findViewById(R.id.iconReact);
            functionStatus = (Switch) itemView.findViewById(R.id.switch_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Intent intent=new Intent(v.getContext(), FunctionViewActivity.class);
                        intent.putExtra("position",getAdapterPosition());
                        v.getContext().startActivity(intent);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    final Function function = functions.get(getAdapterPosition());
                    final AlertDialog.Builder builder =new AlertDialog.Builder(v.getContext());
                    builder.setTitle(function.getName());
                    builder.setCancelable(true);


                    final CharSequence [] lists={"Delete","View","Edit"};
                    builder.setItems(lists, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0){
                                AlertDialog.Builder builder2 =new AlertDialog.Builder(v.getContext());
                                builder2.setTitle("Are you sure you want to delete?");
                                builder2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).child(function.getId()).removeValue();
                                        Toast.makeText(v.getContext(),"Function Deleted !",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog adialog=builder2.create();
                                adialog.show();
                            }else if(which==1){
                                Intent intent=new Intent(v.getContext(), FunctionViewActivity.class);
                                intent.putExtra("position",getAdapterPosition());
                                v.getContext().startActivity(intent);
                            }else if(which==2){
                                Intent intent = new Intent(v.getContext(), TriggerActivity.class);
                                intent.putExtra("functionEdit", "true");
                                intent.putExtra("function", functions.get(getAdapterPosition()));
                                v.getContext().startActivity(intent);
                            }
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();

                    return true;
                }
            });

            functionStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION && buttonView.isPressed()) {
                        String status = isChecked ? "on" : "off";
                        Function function=functions.get(getAdapterPosition());
                        mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).child(function.getId()).child("status").setValue(status);
                        functions.get(getAdapterPosition()).setStatus(status);
                        if(isChecked){
                            triggerService.registerTrigger(functions.get(getAdapterPosition()).getId());
                        }else{
                            triggerService.cancelTrigger(functions.get(getAdapterPosition()).getId());
//                            triggerService.cancelAction(functions.get(getAdapterPosition()));
                        }
                    }
                }
            });


        }
    }


}
