package com.example.sharedprefrencetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_name,et_mobile;
    TextView tv;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        tv = findViewById(R.id.textView);
        preferences = getPreferences(MODE_PRIVATE);
    }

    public void save(View view) {
        String name = et_name.getText().toString();
        String mobile = et_mobile.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("n",name);
        editor.putString("m",mobile);
        editor.apply();
        Toast.makeText(this,
                "Saved Suucess fully...", Toast.LENGTH_SHORT).show();

    }

    public void retrive(View view) {
        String key = et_name.getText().toString();
        String data = preferences.getString(key,"Data not available");
        tv.setText(data);

    }
}
