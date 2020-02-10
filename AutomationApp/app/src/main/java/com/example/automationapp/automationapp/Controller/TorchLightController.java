package com.example.automationapp.automationapp.Controller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import java.security.Policy;

import DTO.Function;

/**
 * Created by laiwf on 11/03/2017.
 */

public class TorchLightController {

    private CameraManager cameraManager;
    private String mCameraId;
    private Context mContext;
    private boolean status;

    public TorchLightController(Context context) {
        mContext=context;
        cameraManager =  (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean hasTorchLight(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void turnLight(boolean turn) {
        try {
            status=turn;
            cameraManager.setTorchMode(mCameraId, turn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTurnOn(){
        return status;
    }


}
