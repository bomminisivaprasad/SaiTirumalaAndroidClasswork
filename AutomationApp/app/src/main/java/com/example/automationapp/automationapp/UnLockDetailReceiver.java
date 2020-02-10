package com.example.automationapp.automationapp;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.automationapp.automationapp.TriggerService;

/**
 * Created by laiwf on 11/03/2017.
 */

public class UnLockDetailReceiver extends DeviceAdminReceiver {

    private String TAG="UnLockDetailReceiver";
    protected static TriggerService triggerService;

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context, "admin enabled", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onEnabled");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Toast.makeText(context,"admin disabled", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDisabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
        Log.d(TAG, "onPasswordChanged");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        super.onPasswordFailed(context, intent);
        Log.d(TAG, "onPasswordFailed");
        DevicePolicyManager mgr = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        int no = mgr.getCurrentFailedPasswordAttempts();
        IBinder binder = peekService(context, new Intent(context, TriggerService.class));
        if (binder != null){
            triggerService = ((TriggerService.TriggerServiceBinder) binder).getService();
            triggerService.startReceiverAction(TAG,no);
        }
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        super.onPasswordSucceeded(context, intent);
        Log.d(TAG, "onPasswordSucceeded");
    }
}
