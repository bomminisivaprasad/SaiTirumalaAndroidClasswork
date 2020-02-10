package com.example.automationapp.automationapp.Controller;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by laiwf on 07/03/2017.
 */

public class WIFIController {
    private WifiManager mManager;

    public WIFIController(Context context) {
        mManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public boolean isConnected(){
        return mManager.isWifiEnabled();
    }

    public boolean setConnection(boolean connection){
        return mManager.setWifiEnabled(connection);
    }
}
