package com.example.firebasecrudoperations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et_name,et_rollno,et_mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_rollno = findViewById(R.id.rollno);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);

    }

    public void save(View view) {
        String rollno = et_rollno.getText().toString();
        String name = et_name.getText().toString();
        String mobile = et_mobile.getText().toString();


    }

    public void retrive(View view) {
    }
}
