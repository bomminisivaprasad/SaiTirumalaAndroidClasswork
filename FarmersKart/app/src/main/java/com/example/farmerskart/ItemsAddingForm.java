package com.example.farmerskart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ItemsAddingForm extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences spreferences;
    private static final int GALLERY_CODE = 90;
    private static final int CAMERA_CODE = 80;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference reference;

    ImageView itemImage;
    Spinner sp;
    EditText et_pname, et_pPrice,et_pAvailable;

    Button b_camera, b_gallery, b_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_adding_form);
        et_pAvailable = findViewById(R.id.itemQuantity);
        spreferences = getSharedPreferences("SellerPrefrence", MODE_PRIVATE);
        itemImage = findViewById(R.id.itemimage);
        sp = findViewById(R.id.itemType);
        et_pname = findViewById(R.id.itemName);
        et_pPrice = findViewById(R.id.itempricewithunits);
        b_camera = findViewById(R.id.itemCamera);
        b_gallery = findViewById(R.id.itemGallery);
        b_add = findViewById(R.id.itemAddbutton);

        b_camera.setOnClickListener(this);
        b_gallery.setOnClickListener(this);
        b_add.setOnClickListener(this);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.itemCamera:
                openCamera();
                break;
            case R.id.itemGallery:
                openGallery();
                break;
            case R.id.itemAddbutton:
                saveData();
                break;
        }

    }

    private void saveData() {
        final String productType = sp.getSelectedItem().toString();
        final String productName = et_pname.getText().toString();
        final String productPricewithunits = et_pPrice.getText().toString();
        final String itemQunatity = et_pAvailable.getText().toString();
        final String ownerName = spreferences.getString("name", "owner Name not Found");
        final String ownerMobile = spreferences.getString("mobile", "owner mobile not Found");
        final String ownerDistrict = spreferences.getString("district", "owner district not Found");
        final String ownerAddress = spreferences.getString("address", "owner address not Found");
        final String ownerImagepath = spreferences.getString("imagepath", "owner image not Found");
        final String ownerLocation = spreferences.getString("location", "owner location not Found");
        if ((productType.equals("Select Type of Product")) || productName.isEmpty() ||
                productPricewithunits.isEmpty() || itemQunatity.isEmpty()) {
            Toast.makeText(this, "Please fill the Details", Toast.LENGTH_SHORT).show();
        } else {


            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String itemimagePath = uri.toString();
                    MyItem item = new MyItem(productType,productName,productPricewithunits,ownerName,ownerMobile, ownerDistrict,ownerAddress,ownerImagepath,ownerLocation,itemimagePath,itemQunatity);
                    reference.child("Items").child(ownerDistrict).child(productType).push().setValue(item)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ItemsAddingForm.this,
                                            "Your Details are Saved Successfully....", Toast.LENGTH_SHORT).show();
                                    et_pname.setText("");
                                    et_pPrice.setText("");
                                    sp.setSelection(0);
                                    itemImage.setImageURI(Uri.parse(""));
                                }
                            });


                }
            });


        }


    }


    public void imageUpload(Uri filepath) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading File");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.show();
        storageReference = storageReference.child("ItemImages/" + UUID.randomUUID().toString());
        storageReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ItemsAddingForm.this,
                        "Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ItemsAddingForm.this,
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

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, GALLERY_CODE);
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap b = (Bitmap) data.getExtras().get("data");
                //URL URI URN
                Uri u = getImageUri(this, b);
                itemImage.setImageURI(u);
                imageUpload(u);
            }
        } else if (requestCode == GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                Uri u = data.getData();
                itemImage.setImageURI(u);
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
