package com.example.textfiledatareading;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 22;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkPermission()){
            Toast.makeText(this,
                    "Permission Granted", Toast.LENGTH_SHORT).show();
        }else {
            requestPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        //int access_loc_re = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        // int cam_re=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        int write_to_ExternalStorage= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read_external_storage=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        return write_to_ExternalStorage== PackageManager.PERMISSION_GRANTED && read_external_storage==PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
        }

    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {      /* Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,*/
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_PERMISSION_CODE);
    }

    public void get(View view) {

        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        File file = new File(sdcard,"contact.txt");

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

//Find the view by its id
        //TextView tv = (TextView)findViewById(R.id.text_view);

//Set the text
        //tv.setText(text.toString());
        Toast.makeText(this, ""+text.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, ""+(int)'a', Toast.LENGTH_SHORT).show();
    }
}
