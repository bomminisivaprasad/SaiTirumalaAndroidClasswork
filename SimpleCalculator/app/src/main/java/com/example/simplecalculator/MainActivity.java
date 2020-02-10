package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et_a,et_b;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_a=findViewById(R.id.a_value);
        et_b=findViewById(R.id.b_value);
        tv=findViewById(R.id.result);
    }

    public void add(View view) {
        int a = Integer.parseInt(et_a.getText().toString());
        int b = Integer.parseInt(et_b.getText().toString());
        int sum = a+b;
        tv.setText("The sum of "+a+" and "+b+" is "+sum);

    }
}
