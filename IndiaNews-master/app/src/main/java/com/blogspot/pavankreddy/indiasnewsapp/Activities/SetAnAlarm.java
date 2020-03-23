package com.blogspot.pavankreddy.indiasnewsapp.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blogspot.pavankreddy.indiasnewsapp.AlarmReceiver;
import com.blogspot.pavankreddy.indiasnewsapp.R;

public class SetAnAlarm extends AppCompatActivity
{

    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID = 0;
    AlarmManager alarmManager;
    Intent notifyIntent;
    PendingIntent notifyPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_an_alarm);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setReminderForNews(View view)
    {

        long triggerTime = SystemClock.elapsedRealtime()+30*1000;
        long repeatInterval = 30*1000;
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime, repeatInterval, notifyPendingIntent);
        Toast.makeText(this, R.string.reminder_is_set, Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(View view)
    {
        alarmManager.cancel(notifyPendingIntent);
        Toast.makeText(this, R.string.reminder_is_cancelled, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return true;

    }
}
