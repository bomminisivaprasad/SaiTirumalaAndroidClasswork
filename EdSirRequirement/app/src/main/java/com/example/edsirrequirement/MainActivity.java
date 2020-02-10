package com.example.edsirrequirement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoScan(View view) {
        startActivity(new Intent(this,BundleInfoActivity.class));
    }

    public void editPage(View view) {
    }
}
