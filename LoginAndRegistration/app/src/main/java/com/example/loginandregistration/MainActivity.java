package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_u,et_p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_u = findViewById(R.id.username);
        et_p = findViewById(R.id.pass);

    }

    public void register(View view) {
        String username = et_u.getText().toString();
        String password = et_p.getText().toString();
        Intent i = new Intent(this,LoginActivity.class);
        i.putExtra("u",username);
        i.putExtra("p",password);
        startActivity(i);
        Toast.makeText(this,
                "Details Saved Successfully..",
                Toast.LENGTH_SHORT).show();
    }

    public void gotologin(View view) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
