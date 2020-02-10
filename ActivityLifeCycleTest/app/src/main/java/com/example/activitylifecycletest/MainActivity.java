package com.example.activitylifecycletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.textview);
        tv.append("Oncreate \n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv.append("Onstart \n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv.append("OnResume \n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        tv.append("OnPause \n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        tv.append("OnStop \n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tv.append("OnDestroy \n");
        //Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tv.append("OnRestart \n");
    }
}
