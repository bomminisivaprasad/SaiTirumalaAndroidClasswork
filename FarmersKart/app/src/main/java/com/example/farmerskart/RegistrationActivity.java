package com.example.farmerskart;

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
import android.os.UserHandle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class RegistrationActivity extends AppCompatActivity {

    EditText et_name, et_mobile, et_address, et_username, et_password;
    Spinner sp_district, sp_usertype;

    ImageView iv;
    LocationManager manager;


    public static final int REQUEST_CODE_CAMERA = 22;
    public static final int REQUEST_CODE_GALLERY = 33;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference reference;

    TextView tv_latlong;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..");
        dialog.setTitle("Fetching Location");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
        tv_latlong = findViewById(R.id.location);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        sp_district = findViewById(R.id.district);
        et_address = findViewById(R.id.address);
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        sp_usertype = findViewById(R.id.usertype);
        iv = findViewById(R.id.image);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {
            //EventLogTags.Description.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(RegistrationActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Please Check your Internetconnection")
                    .setPositiveButton("OK", null).show();
        } else {
            Toast.makeText(this,
                    "Iternet is Available", Toast.LENGTH_SHORT).show();
            getCurrentLocation();
        }


    }

    public void createAccount(View view) {
        final String name = et_name.getText().toString();
        final String mobile = et_mobile.getText().toString();
        final String district = sp_district.getSelectedItem().toString();
        final String address = et_address.getText().toString();
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();
        final String typeofUser = sp_usertype.getSelectedItem().toString();
        final String location = tv_latlong.getText().toString();
        if (name.isEmpty() || mobile.isEmpty() || (district.equals("Select District") || address.isEmpty() || username.isEmpty() || password.isEmpty() || location.isEmpty() || (typeofUser.equals("Select User Type")))) {
            Toast.makeText(this,
                    "Please fill the Details...", Toast.LENGTH_SHORT).show();
        } else {

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imagePath = uri.toString();
                    User user = new User(name, mobile, district, address, username, password, typeofUser, imagePath,location);
                    reference.child("Users").child(typeofUser).push().setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegistrationActivity.this,
                                            "Your Details are Saved Successfully....", Toast.LENGTH_SHORT).show();
                                    et_name.setText("");
                                    et_mobile.setText("");
                                    sp_district.setSelection(0);
                                    et_address.setText("");
                                    et_username.setText("");
                                    et_password.setText("");
                                    sp_usertype.setSelection(0);
                                    iv.setImageURI(Uri.parse(""));
                                    tv_latlong.setText("");
                                }
                            });


                }
            });


        }


    }

    public void openCamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_CODE_CAMERA);
    }

    public void openGallery(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap b = (Bitmap) data.getExtras().get("data");
                //URL URI URN
                Uri u = getImageUri(this, b);
                iv.setImageURI(u);
                imageUpload(u);
            }
        } else if (requestCode == REQUEST_CODE_GALLERY) {
            if (resultCode == RESULT_OK) {
                Uri u = data.getData();
                iv.setImageURI(u);
                imageUpload(u);
            }
        }
    }

    private void imageUpload(Uri u) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading File");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.show();
        storageReference = storageReference.child("Images/" + UUID.randomUUID().toString());
        storageReference.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this,
                        "Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this,
                        "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media
                .insertImage(inContext.getContentResolver(),
                        inImage, "Title", null);
        return Uri.parse(path);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurrentLocation() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tv_latlong.setText("" + latitude + "," + longitude);
                dialog.dismiss();

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




}
