package com.example.digitalrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 20;
    EditText itemname,itemPrice;
    ImageView itemImage,qrCodeImage;
    Button imageUploadButton,saveDataButton,dataViewButton;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 22;
    private Uri filepath;

    private String fileUrl;


    FirebaseDatabase database;
    DatabaseReference myRef;

    FirebaseStorage storage;
    StorageReference storageReference;
    private ArrayList<HotelItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemname = findViewById(R.id.item);
        itemPrice = findViewById(R.id.itemPrice);
        itemImage = findViewById(R.id.itemImage);
        qrCodeImage = findViewById(R.id.qrCodeImage);

        imageUploadButton = findViewById(R.id.uploadButton);
        saveDataButton = findViewById(R.id.saveButton);
        dataViewButton = findViewById(R.id.dataViewButton);


        imageUploadButton.setOnClickListener(this);
        saveDataButton.setOnClickListener(this);
        dataViewButton.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("items");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadButton:
                pickImage();
                break;
            case R.id.saveButton:
                saveData();
                break;
            case R.id.dataViewButton:
                generateQrCode();
        }

    }

    private void generateQrCode() {
        DatabaseReference ref = database.getReference();
        final StringBuilder builder = new StringBuilder();
        ref.child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<HotelItem>();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    HotelItem item = singleSnapshot.getValue(HotelItem.class);
                    list.add(item);
                }

                Toast.makeText(MainActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
                for(int i = 0;i<list.size();i++){
                    builder.append(list.get(i).getItemName()+" "+list.get(i).getItemPrice()+" "+list.get(i).getItemImagepath()+"\n");
                }

                String QRcode = builder.toString();
                new generateQrcode(qrCodeImage).execute(QRcode);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    private class generateQrcode extends AsyncTask<String, Void, Bitmap> {
        public final static int WIDTH = 400;
        ImageView bmImage;

        public generateQrcode(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String Value = urls[0];
            com.google.zxing.Writer writer = new QRCodeWriter();
            Bitmap bitmap = null;
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = writer.encode(Value, com.google.zxing.BarcodeFormat.QR_CODE, WIDTH, WIDTH,
                        ImmutableMap.of(EncodeHintType.MARGIN, 1));
                bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
                for (int i = 0; i < 400; i++) {
                    for (int j = 0; j < 400; j++) {
                        bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK
                                : Color.WHITE);
                    }
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void saveData() {
        if(filepath!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            storageReference = storageReference.child("restaurant/"+ UUID.randomUUID().toString());
            storageReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    firebasefilepath= taskSnapshot.getStorage().getDownloadUrl();
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this, ""+firebasefilepath, Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Uploaded....", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fileUrl = uri.toString();
                            String name = itemname.getText().toString();
                            String price = itemPrice.getText().toString();
                            if (fileUrl!=null){
                                HotelItem item = new HotelItem(name,price,fileUrl);
                                myRef.push().setValue(item).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(MainActivity.this, "Your Details are Saved....", Toast.LENGTH_SHORT).show();
                                                filepath = Uri.EMPTY;
                                                itemname.setText("");
                                                itemPrice.setText("");
                                                itemImage.setImageURI(filepath);

                                            }
                                        });
                            }
                        }
                    });


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


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void pickImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == SELECT_PICTURE){
                filepath = data.getData();
                itemImage.setImageURI(filepath);
                Toast.makeText(this, "" + filepath, Toast.LENGTH_SHORT).show();
            }

            }
        }
    }

