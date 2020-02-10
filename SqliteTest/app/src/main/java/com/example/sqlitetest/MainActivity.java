package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_name,et_mobile;
    TextView tv;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        tv = findViewById(R.id.data);
        helper = new DbHelper(this);
    }

    public void save(View view) {
        String name = et_name.getText().toString();
        String mobileno = et_mobile.getText().toString();
        if(name.isEmpty() || mobileno.isEmpty()){
            Toast.makeText(this,
                    "Please enter details", Toast.LENGTH_SHORT).show();
        }else {
            ContentValues cv = new ContentValues();
            cv.put(DbHelper.COL_1, name);
            cv.put(DbHelper.COL_2, mobileno);
            long i = helper.insertData(cv);
            if (i > 0) {
                Toast.makeText(this,
                        "Data inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Data is not inserted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void retrive(View view) {
        Cursor c =helper.getData();
        StringBuilder builder = new StringBuilder();
        while (c.moveToNext()){
            builder.append(c.getInt(0)+" "+
                    c.getString(1)+" "+
                    c.getString(2)+"\n");
        }

        tv.setText(builder.toString());
    }
}
