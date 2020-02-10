package com.example.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textview);
        tv.append("Oncreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        tv.append("Onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv.append("OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        tv.append("OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        tv.append("OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tv.append("OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tv.append("Onrestart");
    }
}
