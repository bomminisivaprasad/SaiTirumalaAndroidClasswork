package com.example.edsirrequirement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class BundleInfoActivity extends AppCompatActivity {

    EditText et_bundle,et_booklet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle_info);
        et_bundle = findViewById(R.id.bundleedittext);
        et_booklet = findViewById(R.id.bookleteditText);
    }

    public void submit(View view) {
        int bundleNumber = Integer.parseInt(et_bundle.getText().toString());
        int numberofBooklets = Integer.parseInt(et_booklet.getText().toString());
        Intent i = new Intent(this,BookletsScanningActivity.class);
        i.putExtra("bundle",bundleNumber);
        i.putExtra("booklet",numberofBooklets);
        startActivity(i);

    }
}
