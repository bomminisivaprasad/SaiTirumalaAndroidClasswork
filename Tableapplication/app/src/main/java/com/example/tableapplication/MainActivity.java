package com.example.tableapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
EditText et_n;
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_n=findViewById(R.id.value);
        tv=findViewById(R.id.result);
    }

    public void generate_table(View view) {
        int n=Integer.parseInt(et_n.getText().toString());
        int i,res=0;
        for(i=1;i<=10;i++)
        {
            res=n*i;
            tv.append(n+"*" + i+"=" + res +"\n");

        }
    }
}
