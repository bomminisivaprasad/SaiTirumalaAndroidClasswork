package com.example.qrtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DataViewActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        et = findViewById(R.id.rollno);
        tv = findViewById(R.id.result);
        preferences = getSharedPreferences("MyPrefrence",MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent i =new Intent(this,MainActivity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    public void Submit(View view) {
        String rollNo = et.getText().toString();
        String data = preferences.getString(rollNo,"Data is Not Available");
        tv.setText(rollNo+"  "+data);


    }
}
