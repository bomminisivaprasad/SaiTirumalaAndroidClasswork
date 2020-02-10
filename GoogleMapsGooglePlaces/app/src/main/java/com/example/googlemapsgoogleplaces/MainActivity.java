package com.example.googlemapsgoogleplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isServicesOk()){
            iniit();
        }
    }
    private void iniit(){
        Button buutonmap = findViewById(R.id.map_buuton);
        buutonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MapActivity.class);
                startActivity(i);
            }
        });

    }
    public boolean isServicesOk(){
        Log.d(TAG,"isServiceOk: Checking Google Service Version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServiceOk: Google Play service is working");
            return true;

        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"isServiceOk: an error oocuured but we can fix it");
            Dialog d = GoogleApiAvailability.getInstance().getErrorDialog(this,available,ERROR_DIALOG_REQUEST);
            d.show();


        }else {
            Toast.makeText(this, "we cann't make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
