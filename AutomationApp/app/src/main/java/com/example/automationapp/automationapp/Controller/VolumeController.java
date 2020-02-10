package com.example.automationapp.automationapp.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by laiwf on 03/05/2017.
 */

public class VolumeController {

    private AudioManager am;
    private Context mContext;

    public VolumeController(Context context){
        mContext=context;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void changeMusicVolume(int volume){
        changeMode();
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }

    public void changeRingVolume(int volume){
        changeMode();
        am.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
    }

    public void changeAlarmVolume(int volume){
        changeMode();
        am.setStreamVolume(AudioManager.STREAM_ALARM, volume, 0);
    }

    public void changeMode(){
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
        am.setMode(AudioManager.MODE_NORMAL);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        if (am.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE || am.getRingerMode()==AudioManager.RINGER_MODE_SILENT) {
            Toast.makeText(mContext.getApplicationContext(),"Phone in silence mode or do not disturb mode might restrict the function ",Toast.LENGTH_SHORT).show();
        }
    }

    public int getMusicMaxVolume(){
        return am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public int getRingMaxVolume(){
        return am.getStreamMaxVolume(AudioManager.STREAM_RING);
    }

    public int getAlarmMaxVolume(){
        return am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
    }

    public int getMusicVolume(){
        return am.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public int getRingVolume(){
        return am.getStreamVolume(AudioManager.STREAM_RING);
    }

    public int getAlarmVolume(){
        return am.getStreamVolume(AudioManager.STREAM_ALARM);
    }
}
