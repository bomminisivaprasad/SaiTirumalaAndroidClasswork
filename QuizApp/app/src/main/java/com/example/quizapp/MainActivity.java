package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et_username,et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = findViewById(R.id.loginusername);
        et_password = findViewById(R.id.loginpass);
    }

    public void login(View view) {
    }

    public void newUser(View view) {
        startActivity(new Intent(this,FacultyRegistrationActivity.class));
    }
}
