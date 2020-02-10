package com.example.cameraandgalleryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.image);
       if(!checkPermission()){
           requestPermission();
       }
    }

    public void openGallery(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,22);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 22){
            if(resultCode == RESULT_OK){
                Uri u = data.getData();
                iv.setImageURI(u);

            }
        }
        else if(requestCode == 44){
            if(resultCode == RESULT_OK){
            /*  Uri u1 = data.getData();
              iv.setImageURI(u1);*/
            Bitmap b = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(b);

            }
        }
    }

    public void openCamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,44);
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA

                },66);
    }



    public boolean checkPermission(){
        int read = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        return read == PackageManager.PERMISSION_GRANTED &&
                write == PackageManager.PERMISSION_GRANTED &&
                camera == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 66){
            if(grantResults.length>0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permissions are granted", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Permissions are dinied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
