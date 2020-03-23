package com.example.mahidarrequrement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button light1onButton,light1OffButton,light2OnButton,light2OffButton,
    fanOnButton,fanOffButton,motorOnButton,motorOffButton,onAllButton,oFFAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onAllButton = findViewById(R.id.onallButton);
        oFFAllButton = findViewById(R.id.offallButton);


        light1onButton = findViewById(R.id.light1onButton);
        light1OffButton = findViewById(R.id.light1offButton);

        light2OnButton = findViewById(R.id.light2onButton);
        light2OffButton = findViewById(R.id.light2offButton);

        fanOnButton = findViewById(R.id.fanonButton);
        fanOffButton = findViewById(R.id.fanoffButton);

        motorOnButton = findViewById(R.id.motoronButton);
        motorOffButton = findViewById(R.id.motoroffButton);

        light1onButton.setOnClickListener(this);
        light1OffButton.setOnClickListener(this);

        light2OnButton.setOnClickListener(this);
        light2OffButton.setOnClickListener(this);

        fanOnButton.setOnClickListener(this);
        fanOffButton.setOnClickListener(this);

        motorOnButton.setOnClickListener(this);
        motorOffButton.setOnClickListener(this);

        onAllButton.setOnClickListener(this);
        oFFAllButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.light1onButton:
                String light1onUrl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field1=1";
                MyTask light1Task = new MyTask(light1onUrl,this);
                light1Task.execute();
                break;

            case R.id.light1offButton:
                String light1offurl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field1=0";
                MyTask light2Task = new MyTask(light1offurl,this);
                light2Task.execute();
                break;
            case R.id.light2onButton:
                String light2onUrl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field2=1";
                MyTask fanTask = new MyTask(light2onUrl,this);
                fanTask.execute();
                break;
            case R.id.light2offButton:
                String light2offUrl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field2=0";
                MyTask motorTask = new MyTask(light2offUrl,this);
                motorTask.execute();
                break;

            case R.id.fanonButton:
                String fanonfUrl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field3=1";
                MyTask fanOnTask = new MyTask(fanonfUrl,this);
                fanOnTask.execute();
                break;
            case R.id.fanoffButton:
                String fanoffUrl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field3=0";
                MyTask fanOfTask = new MyTask(fanoffUrl,this);
                fanOfTask.execute();
                break;

            case R.id.motoronButton:
                String motoronUrl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field4=1";
                MyTask motorOnTask = new MyTask(motoronUrl,this);
                motorOnTask.execute();
                break;

            case R.id.motoroffButton:
                String motoroffUrl = "https://api.thingspeak.com/update?api_key=ZNUU79E9B005O6A3&field4=0";
                MyTask motoroffTask = new MyTask(motoroffUrl,this);
                motoroffTask.execute();
                break;

            case R.id.onallButton:
                break;
            case R.id.offallButton:
                break;


        }

    }
}
