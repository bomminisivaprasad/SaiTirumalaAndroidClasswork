package com.example.gettextapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       et =  findViewById(R.id.userinput);
       tv=findViewById(R.id.textview);
    }

    public void get(View view) {
        String data =et.getText().toString();
        tv.setText(data);
    }
}
