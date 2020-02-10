package com.example.automationapp.automationapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.automationapp.automationapp.Controller.AlarmController;
import com.example.automationapp.automationapp.Controller.CameraController;
import com.example.automationapp.automationapp.Controller.AcceleratorSensorController;
import com.example.automationapp.automationapp.Controller.HTTPController;
import com.example.automationapp.automationapp.Controller.TorchLightController;
import com.example.automationapp.automationapp.Controller.VolumeController;
import com.example.automationapp.automationapp.Controller.WIFIController;
import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import DTO.Function;
import DTO.FunctionAction;
import DTO.FunctionTrigger;
import DTO.Trigger;

/**
 * Created by laiwf on 09/03/2017.
 */

public class TriggerService extends Service {

    public static final String ANONYMOUS = "anonymous";
    private String TAG = "TriggerService";
    private String UNLOCK_RECEIVER_TAG="UnLockDetailReceiver";
    private String ALARM_RECEIVER_TAG="AlarmReceiver";
    private String SMS_RECEIVER_TAG="SMSReceiver";
    private final String SHAKE_TRIGGER_ID = "trigger_1";
    private final String UNLOCK_TRIGGER_ID = "trigger_2";
    private final String ALARM_TRIGGER_ID = "trigger_3";
    private final String SMS_TRIGGER_ID = "trigger_4";
    private final String WIFI_ACTION_ID = "action_1";
    private final String TORCHLIGHT_ACTION_ID = "action_2";
    private final String CAMERA_ACTION_ID = "action_3";
    private final String VOLUME_ACTION_ID = "action_4";
    private final String HTTP_ACTION_ID = "action_5";
    private final String STATUS_ON = "on";
    private final String STATUS_OFF = "off";
    private int Notification_COUNT=0;

    private final IBinder myBinder = new TriggerServiceBinder();

    private ArrayList<Function> functions = new ArrayList<>();

    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;

    protected static FirebaseAuth mFirebaseAuth;
    protected static DatabaseReference mDatabase;

    private HashMap<String,AcceleratorSensorController> AcceleratorControllers;
    private TorchLightController torchLightController;
    private WIFIController wifiController;
    private VolumeController volumeController;
    private HashMap<String,Function>  UnlockFuntions;
    private HashMap<String,Function>  SMSFuntions;
    private HashMap<String,AlarmController> AlarmControllers;



    public TriggerService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY);
        System.out.println("TriggerService onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
//        Notification notification = new Notification(R.mipmap.icon, "service !", System.currentTimeMillis());
//        startForeground(0, notification);
        return myBinder;
    }

    public void startService() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null || (mFirebaseUser != null && !mFirebaseUser.isEmailVerified())) {
            startActivity(new Intent(this, SignInActivity.class));
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    GenericTypeIndicator<HashMap<String,Function>> functionType = new GenericTypeIndicator<HashMap<String,Function>>() {};
                    HashMap<String,Function> functionMap= dataSnapshot.getValue(functionType);
                    for (String key:functionMap.keySet()){
                        Function f=functionMap.get(key);
                        f.setId(key);
                        System.out.println(f);
                        functions.add(f);
                    }
                    startTrigger();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "startService onCancelled " + databaseError.getDetails());
            }
        });

        if(AcceleratorControllers==null){
            AcceleratorControllers=new HashMap<>();
        }

        if(UnlockFuntions==null){
            UnlockFuntions=new HashMap<>();
        }
        if(AlarmControllers==null){
            AlarmControllers=new HashMap<>();
        }
        if(SMSFuntions==null){
            SMSFuntions=new HashMap<>();
        }
    }

    public void startTrigger() {
        for (Function function : functions) {
            System.out.println(function);
            FunctionTrigger trigger=function.getTrigger();
            if (trigger.getId().equals(SHAKE_TRIGGER_ID) && function.getStatus().equals(STATUS_ON)) {
                AcceleratorControllers.remove(function.getId());
                AcceleratorControllers.put(function.getId(), new AcceleratorSensorController(this,trigger, function));
            }
            if (trigger.getId().equals(UNLOCK_TRIGGER_ID) && function.getStatus().equals(STATUS_ON)) {
                UnlockFuntions.remove(function.getId());
                UnlockFuntions.put(function.getId(),function);
            }
            if (trigger.getId().equals(ALARM_TRIGGER_ID) && function.getStatus().equals(STATUS_ON)) {
                AlarmControllers.remove(function.getId());
                AlarmControllers.put(function.getId(),new AlarmController(this,function));
            }
            if (trigger.getId().equals(SMS_TRIGGER_ID) && function.getStatus().equals(STATUS_ON)) {
                SMSFuntions.remove(function.getId());
                SMSFuntions.put(function.getId(),function);
            }
        }
    }

    public void cancelTrigger(String id) {
        Function function=getFunctionById(id);
        FunctionTrigger trigger=function.getTrigger();
        if (trigger.getId().equals(SHAKE_TRIGGER_ID) && AcceleratorControllers.containsKey(id)) {
                AcceleratorControllers.get(id).unregister();
                AcceleratorControllers.remove(id);
        }
        if(trigger.getId().equals(UNLOCK_TRIGGER_ID) && UnlockFuntions.containsKey(function.getId())){
            UnlockFuntions.remove(function.getId());
        }
        if(trigger.getId().equals(ALARM_TRIGGER_ID) && AlarmControllers.containsKey(function.getId())){
            AlarmControllers.get(id).stop();
            AlarmControllers.remove(function.getId());
        }
        if(trigger.getId().equals(UNLOCK_TRIGGER_ID) && UnlockFuntions.containsKey(function.getId())){
            SMSFuntions.remove(function.getId());
        }
        setFunctionStatus(STATUS_OFF,id);
        cancelAction(function);
    }

    public void setFunctionStatus(String status,String id){
        for(Function function:functions){
            if(function.getId().equalsIgnoreCase(id)){
                function.setStatus(status);
            }
        }
    }

    public void cancelAction(Function function){
        FunctionAction action=function.getAction();
        if (action.getId().equals(TORCHLIGHT_ACTION_ID) && torchLightController!=null) {
            torchLightController.turnLight(false);
            torchLightController=null;
        }
    }

    public void registerTrigger(String id) {
        Function function=getFunctionById(id);
        FunctionTrigger trigger=function.getTrigger();
        if (trigger.getId().equals(SHAKE_TRIGGER_ID) ) {
            AcceleratorControllers.put(id,new AcceleratorSensorController(this,trigger, function));
        }
        if (trigger.getId().equals(UNLOCK_TRIGGER_ID) ) {
            UnlockFuntions.remove(function.getId());
            UnlockFuntions.put(function.getId(),function);
        }

        if (trigger.getId().equals(ALARM_TRIGGER_ID)) {
            AlarmControllers.remove(function.getId());
            AlarmControllers.put(function.getId(),new AlarmController(this,function));
        }

        if (trigger.getId().equals(SMS_TRIGGER_ID) ) {
            SMSFuntions.remove(function.getId());
            SMSFuntions.put(function.getId(),function);
        }
        function.setStatus(STATUS_ON);
        registerAction(function);
    }

    public Function getFunctionById(String id){
        for(Function function:functions){
            if(function.getId().equalsIgnoreCase(id)){
                return function;
            }
        }
        return null;
    }

    public void registerAction(Function function){
        FunctionAction action=function.getAction();
        if (action.getId().equals(WIFI_ACTION_ID) && wifiController==null) {
            wifiController=new WIFIController(getApplication());
        }
        if (action.getId().equals(TORCHLIGHT_ACTION_ID) && torchLightController==null) {
            torchLightController=new TorchLightController(getApplicationContext());
        }
        if(action.getId().equals(VOLUME_ACTION_ID) && volumeController==null){
            volumeController=new VolumeController(getApplicationContext());
        }
    }

    public void startAction(Function function) {
        FunctionAction action=function.getAction();
        if (action.getId().equals(WIFI_ACTION_ID)) {
            wifiAction(function, action);
        }
        if (action.getId().equals(TORCHLIGHT_ACTION_ID)) {
            TorchLightAction(function, action);
        }
        if (action.getId().equals(CAMERA_ACTION_ID)) {
            new CameraController(TriggerService.this,function);
        }
        if(action.getId().equals(VOLUME_ACTION_ID)){
            VolumeAction(function,action);
        }
        if (action.getId().equals(HTTP_ACTION_ID)) {
            new HTTPController(function,this);
        }
    }


    public void startReceiverAction(String ReceiverTrigger,Object parameter){
        if(ReceiverTrigger.equals(UNLOCK_RECEIVER_TAG) && UnlockFuntions.size()>0 ){
            int no=(int)parameter;
            Iterator<Map.Entry<String,Function>> iter=UnlockFuntions.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String,Function> map=iter.next();
                Function function=map.getValue();
                FunctionTrigger trigger=function.getTrigger();
                if (trigger.getId().equals(UNLOCK_TRIGGER_ID)) {
                    HashMap<String, String> param = trigger.getParameter();
                    if(param.containsKey("times")){
                        int limit=Integer.parseInt(param.get("times"));
                        if(no>=limit){
                            startAction(function);
                        }
                    }
                }
            }
        }else if(ReceiverTrigger.equals(ALARM_RECEIVER_TAG) && AlarmControllers.size()>0){
            Iterator<Map.Entry<String,AlarmController>> iter=AlarmControllers.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String,AlarmController> map=iter.next();
                AlarmController alarmController=map.getValue();
                long time=alarmController.getTime();
                long now = System.currentTimeMillis();
                if(now>=time){
                    startAction(alarmController.getFunction());
                    if(!alarmController.isRepeat()) {
                        mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).child(alarmController.getFunction().getId()).child("status").setValue("off");
                    }
                }
            }
        }else if(ReceiverTrigger.equals(SMS_RECEIVER_TAG) && SMSFuntions.size()>0 ){
            String message=(String)parameter;
            Iterator<Map.Entry<String,Function>> iter=SMSFuntions.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String,Function> map=iter.next();
                Function function=map.getValue();
                FunctionTrigger trigger=function.getTrigger();
                if (trigger.getId().equals(SMS_TRIGGER_ID)) {
                    HashMap<String, String> param = trigger.getParameter();
                    if(param.containsKey("text")){
                        String text=param.get("text");
                        if(message.toLowerCase().contains(text.toLowerCase())){
                            startAction(function);
                        }
                    }
                }
            }
        }
    }

    public void TorchLightAction(Function function, FunctionAction action) {

        if(torchLightController==null) {
            torchLightController= new TorchLightController(getApplicationContext());
        }
        if (torchLightController.hasTorchLight()) {
            HashMap<String, String> parameter = action.getParameter();
            String key = "status";
            String reverse_TAG = "reverse";
            String reverseSuccess_TAG = "reverseSuccess";
            if (parameter.containsKey(key)) {
                String status = parameter.get(key);
                boolean reverse = false;
                String reverseSuccessText = "";
                if (parameter.containsKey(reverse_TAG)) {
                    reverse = Boolean.parseBoolean(parameter.get(reverse_TAG));
                }
                if (parameter.containsKey(reverseSuccess_TAG)) {
                    reverseSuccessText = parameter.get(reverseSuccess_TAG);
                }
                if (reverse) {
                    torchLightController.turnLight(!torchLightController.isTurnOn());
                    String output = torchLightController.isTurnOn() ? function.getSuccess() : reverseSuccessText;
                    setNotification(function.getName(),output);
                } else {
                    torchLightController.turnLight(status == STATUS_ON);
                    setNotification(function.getName(),function.getSuccess());
                 }

            } else {
                setNotification(function.getName(),function.getFail());
            }
        } else {
            Toast.makeText(getApplicationContext(), "TorchLight is not supported in your device", Toast.LENGTH_SHORT).show();
        }
    }

    public void wifiAction(Function function, FunctionAction action) {
        WIFIController controller;
        if(wifiController==null) {
            controller = new WIFIController(getApplicationContext());
        }else{
            controller=wifiController;
        }

        HashMap<String, String> parameter = action.getParameter();
        String key = "status";
        String reverse_TAG = "reverse";
        String reverseSuccess_TAG = "reverseSuccess";
        if (parameter.containsKey(key)) {
            String status = parameter.get(key);
            boolean reverse = false;
            String reverseSuccessText = "";
            if (parameter.containsKey(reverse_TAG)) {
                reverse = Boolean.parseBoolean(parameter.get(reverse_TAG));
            }
            if (parameter.containsKey(reverseSuccess_TAG)) {
                reverseSuccessText = parameter.get(reverseSuccess_TAG);
            }

            if (reverse) {
                System.out.println("wifiAction is isConnected :"+controller.isConnected());
                controller.setConnection(!controller.isConnected());
                String output = controller.isConnected() ? function.getSuccess() : reverseSuccessText;
                setNotification(function.getName(),output);
            } else {
                controller.setConnection(status.equalsIgnoreCase(STATUS_ON));
                setNotification(function.getName(),function.getSuccess());
            }
        } else {
            setNotification(function.getName(),function.getFail());
        }
    }

    private void VolumeAction(Function function, FunctionAction action) {
        if(volumeController==null){
            volumeController=new VolumeController(getApplicationContext());
        }
        HashMap<String, String> parameter = action.getParameter();

        if (parameter.containsKey("music") && parameter.containsKey("ring") && parameter.containsKey("alarm")) {
            int music = Integer.parseInt(parameter.get("music"));
            int ring = Integer.parseInt(parameter.get("ring"));
            int alarm = Integer.parseInt(parameter.get("alarm"));

            volumeController.changeAlarmVolume(alarm);
            volumeController.changeMusicVolume(music);
            volumeController.changeRingVolume(ring);
            setNotification(function.getName(),function.getSuccess());
        } else {
            setNotification(function.getName(),function.getFail());
        }
    }


    public class TriggerServiceBinder extends Binder {
        TriggerService getService() {
            return TriggerService.this;
        }
    }

    public void setNotification(String subject,String text){
        Notification.Builder mBuilder= new Notification.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_fire);
        mBuilder.setContentTitle(subject);
        mBuilder.setContentText(text);
        mBuilder.setAutoCancel(true);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(soundUri);
        Intent intent=new Intent(getApplicationContext(),FunctionActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(FunctionActivity.class);
        stackBuilder.addNextIntent(intent);
        Intent viewIntent=new Intent(getApplicationContext(),FunctionActivity.class);
        stackBuilder.addNextIntent(viewIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.build();
        NotificationManager mNotificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification_COUNT++;
        mNotificationManager.notify(Notification_COUNT, mBuilder.build());
    }


}
