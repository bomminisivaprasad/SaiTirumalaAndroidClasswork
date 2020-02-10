package com.example.automationapp.automationapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by laiwf on 03/05/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    private String TAG="SMSReceiver";
    protected static TriggerService triggerService;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals( Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    String msgBody="";
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody +=(" "+msgs[i].getMessageBody());
                    }
                    IBinder binder = peekService(context, new Intent(context, TriggerService.class));
                    if (binder != null){
                        triggerService = ((TriggerService.TriggerServiceBinder) binder).getService();
                        triggerService.startReceiverAction(TAG,msgBody);
                    }
                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
}
