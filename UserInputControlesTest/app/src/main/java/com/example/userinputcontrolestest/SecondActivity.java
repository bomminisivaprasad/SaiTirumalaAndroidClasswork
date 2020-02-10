package com.example.userinputcontrolestest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv = findViewById(R.id.textview);
        Intent i = getIntent();
        String name = i.getStringExtra("n");
        String mobile = i.getStringExtra("m");
        String email = i.getStringExtra("e");
        String gender = i.getStringExtra("gender");
        String branch = i.getStringExtra("branch");
        String tSkills = i.getStringExtra("techskills");
        tv.setText(name+"\n"+
                mobile+"\n"+
                email+"\n"+
                gender+"\n"+
                branch+"\n"+
                tSkills);

    }
}
