package com.example.firebasedatabasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.EventLogTags;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_rollno, et_name, et_mobile;
    Spinner sp_branch;
    Button b_location, b_camera, b_gallery, b_save, b_viewdata;
    ImageView profileImage;
    TextView tv_latlong;

    LocationManager manager;

    public static final int REQUEST_CODE_CAMERA = 22;
    public static final int REQUEST_CODE_GALLERY = 33;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_rollno = findViewById(R.id.rollno);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        sp_branch = findViewById(R.id.branch);
        b_location = findViewById(R.id.location_button);
        b_camera = findViewById(R.id.camera_button);
        b_gallery = findViewById(R.id.galley_button);
        b_save = findViewById(R.id.save_Button);
        b_viewdata = findViewById(R.id.viewdata_button);
        profileImage = findViewById(R.id.image);
        tv_latlong = findViewById(R.id.latlangTextview);

        b_location.setOnClickListener(this);
        b_camera.setOnClickListener(this);
        b_gallery.setOnClickListener(this);
        b_save.setOnClickListener(this);
        b_viewdata.setOnClickListener(this);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            //EventLogTags.Description.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Please Check your Internetconnection")
                    .setPositiveButton("OK", null).show();
        }else {
            Toast.makeText(this,
                    "Iternet is Available", Toast.LENGTH_SHORT).show();
        }

    }
    public void imageUpload(Uri filepath){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading File");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.show();
        storageReference=storageReference.child("Images/"+ UUID.randomUUID().toString());
        storageReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this,
                        "Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred())
                        / taskSnapshot.getTotalByteCount();
                pd.setProgress((int) progress);

            }
        });


    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_button:
                openCamera();
                break;
            case R.id.galley_button:
                openGallery();
                break;
            case R.id.location_button:
                getCurrentLocation();
                break;
            case R.id.save_Button:
                saveData();
                break;
            case R.id.viewdata_button:
                gotoViewDataActivity();
                break;
        }
    }

    private void gotoViewDataActivity() {
        Intent i = new Intent(this,ViewDataActivity.class);
        startActivity(i);
    }

    private void saveData() {
        final String rollno = et_rollno.getText().toString();
        final String name = et_name.getText().toString();
        final String mobileno = et_mobile.getText().toString();
        final String latlang = tv_latlong.getText().toString();
        final String branch = sp_branch.getSelectedItem().toString();
        if(rollno.isEmpty()||name.isEmpty()||mobileno.isEmpty()||latlang.isEmpty()||branch.isEmpty()){
            Toast.makeText(this,
                    "Please fill the details", Toast.LENGTH_SHORT).show();
        }else {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imagePath = uri.toString();
                    Student s = new Student(rollno, name, mobileno, branch, latlang, imagePath);
                    reference.child("Students").child(branch).push().setValue(s)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this,
                                            "Your Details are Saved Successfully....", Toast.LENGTH_SHORT).show();
                                    et_rollno.setText("");
                                    et_name.setText("");
                                    et_mobile.setText(" ");
                                    tv_latlong.setText("");
                                    profileImage.setImageURI(Uri.parse(""));
                                }
                            });


                }
            });
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurrentLocation() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tv_latlong.setText("" + latitude + "," + longitude);

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
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000, 1, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,1,listener);

    }

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,REQUEST_CODE_GALLERY);
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap b = (Bitmap) data.getExtras().get("data");
                //URL URI URN
                Uri u = getImageUri(this,b);
                profileImage.setImageURI(u);
                imageUpload(u);
            }
        }else if(requestCode == REQUEST_CODE_GALLERY){
                if(resultCode == RESULT_OK){
                    Uri u = data.getData();
                    profileImage.setImageURI(u);
                    imageUpload(u);
                }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media
                .insertImage(inContext.getContentResolver(),
                        inImage, "Title", null);
        return Uri.parse(path);
    }

}
