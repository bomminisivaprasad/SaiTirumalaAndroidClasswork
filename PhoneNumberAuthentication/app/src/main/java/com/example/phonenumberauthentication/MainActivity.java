package com.example.phonenumberauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_phone = findViewById(R.id.phonenumber);
    }

    public void getOtp(View view) {
        String mobile = et_phone.getText().toString().trim();

        if(mobile.isEmpty() || mobile.length() < 10){
            et_phone.setError("Enter a valid mobile");
            et_phone.requestFocus();
            return;
        }

        Intent intent = new Intent(MainActivity.this, OtpReciverAuthentication.class);
        intent.putExtra("mobile", mobile);
        startActivity(intent);
    }
    }

