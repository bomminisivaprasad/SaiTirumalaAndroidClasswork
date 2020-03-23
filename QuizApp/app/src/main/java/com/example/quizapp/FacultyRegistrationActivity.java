package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import java.util.UUID;

public class FacultyRegistrationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY = 22;
    EditText et_fname,et_fmobile,et_femail,et_fusername,et_fpass;
    Spinner sp_fqualification;
    RadioButton r_fFresher,r_fexperiance;
    ImageView fiv;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference reference;
    String fexperianceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_registration);
        et_fname = findViewById(R.id.fname);
        et_fmobile = findViewById(R.id.fmobile);
        et_femail = findViewById(R.id.femail);
        et_fusername = findViewById(R.id.fusername);
        et_fpass = findViewById(R.id.fpass);
        sp_fqualification = findViewById(R.id.fqualification);
        r_fFresher = findViewById(R.id.fresher);
        r_fexperiance = findViewById(R.id.fExperiance);
        fiv = findViewById(R.id.fimage);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }

    public void openGallery(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (resultCode == RESULT_OK) {
                Uri u = data.getData();
                fiv.setImageURI(u);
                imageUpload(u);
            }
        }
    }

    private void imageUpload(Uri filepath) {
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
                Toast.makeText(FacultyRegistrationActivity.this,
                        "Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FacultyRegistrationActivity.this,
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

    public void submit(View view) {
        final String fname = et_fname.getText().toString();
        final String fmobile = et_fmobile.getText().toString();
        final String femail = et_femail.getText().toString();
        final String fusername = et_fusername.getText().toString();
        final String fpassword = et_fpass.getText().toString();
        final String fqualification = sp_fqualification.getSelectedItem().toString();

        if(r_fFresher.isChecked()){
            fexperianceStatus = r_fFresher.getText().toString();
        }
        if(r_fexperiance.isChecked()){
            fexperianceStatus = r_fexperiance.getText().toString();
        }

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if(uri!=null){
                    String fimagePath = uri.toString();
                    Faculty f = new Faculty(fname,fmobile,femail,fqualification,fusername,fpassword,fexperianceStatus,fimagePath);
                    reference.child("RegistredFaculty").child(fexperianceStatus).child(fqualification).push().setValue(f).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FacultyRegistrationActivity.this,
                                    "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                           /* String message = "Test \n" +"Username "+fusername+"Password "+fpassword;
                            sendSMS(fmobile,message);*/
                            startActivity(new Intent(FacultyRegistrationActivity.this,MainActivity.class));
                        }
                    });

                }else {
                    Toast.makeText(FacultyRegistrationActivity.this,
                            "Please select the image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
