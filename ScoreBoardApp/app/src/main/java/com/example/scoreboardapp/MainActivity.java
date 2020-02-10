package com.example.scoreboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b_plus,b_minus,b_reset;
    int count = 0;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_plus = findViewById(R.id.plusButton);
        b_minus = findViewById(R.id.minusButton);
        b_reset = findViewById(R.id.resetButton);

        tv = findViewById(R.id.textview);

        b_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               count++;
               tv.setText(""+count);
            }
        });


        b_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                tv.setText(""+count);
                if(count <= 0){
                    Toast.makeText(MainActivity.this,
                            "You can't decrement", Toast.LENGTH_SHORT).show();
                   count = 0;
                    tv.setText(""+count);

                }
            }
        });
b_reset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        tv.setText(""+0);
        count=0;
    }
});
    }



}

