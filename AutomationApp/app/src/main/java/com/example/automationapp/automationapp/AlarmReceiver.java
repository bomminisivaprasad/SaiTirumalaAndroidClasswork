package com.example.automationapp.automationapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.GregorianCalendar;

/**
 * Created by laiwf on 03/05/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    protected static TriggerService triggerService;
    private String TAG="AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        IBinder binder = peekService(context, new Intent(context, TriggerService.class));
        if (binder != null){
            triggerService = ((TriggerService.TriggerServiceBinder) binder).getService();
            triggerService.startReceiverAction(TAG,null);
        }
    }
}
