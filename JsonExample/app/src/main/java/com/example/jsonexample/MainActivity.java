package com.example.jsonexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String url = "https://api.androidhive.info/contacts/";
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.res);
    }

    public void get(View view) {
        MyTask task = new MyTask(this,url,tv);
        task.execute();

    }
}
