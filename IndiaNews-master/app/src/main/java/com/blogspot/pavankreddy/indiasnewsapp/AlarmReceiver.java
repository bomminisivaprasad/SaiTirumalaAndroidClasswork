package com.blogspot.pavankreddy.indiasnewsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.blogspot.pavankreddy.indiasnewsapp.Activities.MainActivity;
import com.blogspot.pavankreddy.indiasnewsapp.Activities.SetAnAlarm;

public class AlarmReceiver extends BroadcastReceiver
{
    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_CHANNEL_NAME_ID = "reminder_notification_channel";
    private static final String NOTIFICATION_CHANNEL_TYPE = "primary";

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Create the content intent for the notification, which launches this activity
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_NAME_ID, NOTIFICATION_CHANNEL_TYPE, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_NAME_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_access_alarms_black_24dp)
                .setContentTitle("Time to Read Some News!!")
                .setContentText("Tap This notification to Read News~!")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(takemeToApp(context))
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        //Deliver the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }


    private NotificationCompat.Action takemeToApp(Context context)
    {
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_check_black_24dp,"TAKE ME TO APP",pendingIntent);
        return action;
    }

    private PendingIntent contentIntent(Context context)
    {
        Intent intent = new Intent(context,MainActivity.class);
        return PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
