package com.example.userinfowithuserlocation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userinfowithuserlocation.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_name, et_mobile, et_email;
    Button b_location, b_gallery, b_camera, b_save, b_viewdata;
    TextView tv_latlang;
    ImageView imageView;
    Uri filepath;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    LocationManager locationManager;

    double latitude, longitude;
    String mylatlang;


    FirebaseDatabase database;
    DatabaseReference reference;

    StorageReference storageReference;

    ArrayList<User> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobileno);
        et_email = findViewById(R.id.email);

        b_location = findViewById(R.id.getLocation);
        b_gallery = findViewById(R.id.openGallery);
        b_camera = findViewById(R.id.openCamera);
        b_save = findViewById(R.id.saveButton);
        b_viewdata = findViewById(R.id.viewdataButton);

        imageView = findViewById(R.id.image);

        tv_latlang = findViewById(R.id.latlang);

        b_location.setOnClickListener(this);
        b_gallery.setOnClickListener(this);
        b_camera.setOnClickListener(this);
        b_save.setOnClickListener(this);
        b_viewdata.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        storageReference = FirebaseStorage.getInstance().getReference();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getLocation:
                getuserLocation();
                break;
            case R.id.openGallery:
                pickfromGallery();
                break;
            case R.id.openCamera:
                picfromCamera();
                break;
            case R.id.saveButton:
                saveData();
                break;
            case R.id.viewdataButton:
                viewData();
        }
    }

    private void viewData() {
        Intent i = new Intent(this,DataViewActivity.class);
        startActivity(i);

    }

    private void saveData() {
        final String n = et_name.getText().toString();
        reference.child("UserInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User user = singleSnapshot.getValue(User.class);
                    list.add(user);
                }

                for(int i = 0; i<list.size();i++){
                    if(list.get(i).getUname().equals(n)){
                        Toast.makeText(MainActivity.this,
                                "Data Exist", Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        uploadImage();
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
     //uploadImage();

    }


    public void save(){
        final String name = et_name.getText().toString();
        final String mobile = et_mobile.getText().toString();
        final String email = et_email.getText().toString();
        final String latlang = tv_latlang.getText().toString();
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                User u = new User(name, mobile, email, latlang, uri.toString());
                reference.child("UserInfo").push().setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,
                                "Details Saved Successfully....", Toast.LENGTH_SHORT).show();
                        et_name.setText("");
                        et_mobile.setText("");
                        et_email.setText("");
                        tv_latlang.setText("");
                        imageView.setImageURI(Uri.parse(""));
                    }
                });
            }
        });

    }

    private void uploadImage() {
        if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            storageReference = storageReference.child("images/"+ UUID.randomUUID().toString());
            storageReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //firebasefilepath = String.valueOf(taskSnapshot.getStorage().getDownloadUrl());
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this, ""+firebasefilepath, Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Uploaded....", Toast.LENGTH_SHORT).show();
                    save();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });


        }
        //return firebasefilepath;
    }


    private void picfromCamera() {

        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private void pickfromGallery() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, REQUEST_IMAGE_GALLERY);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getuserLocation() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                mylatlang = latitude+","+longitude;
                tv_latlang.setText(mylatlang);
                /*Uri u = Uri.parse("geo:"+latitude+","+longitude);
                Intent i = new Intent(Intent.ACTION_VIEW,u);
                startActivity(i);*/


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQUEST_IMAGE_CAPTURE:
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    Uri u = getImageUri(this, thumbnail);
                    imageView.setImageURI(u);
                    filepath = u;

                    break;
                case REQUEST_IMAGE_GALLERY:
                    filepath = data.getData();
                    imageView.setImageURI(filepath);
                    Toast.makeText(this, ""+filepath, Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
