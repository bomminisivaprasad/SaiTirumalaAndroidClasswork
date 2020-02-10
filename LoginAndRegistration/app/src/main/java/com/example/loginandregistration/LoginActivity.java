package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    String uname,upass;
    EditText et_uname,et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_uname = findViewById(R.id.u_name);
        et_pass = findViewById(R.id.u_p);
        uname = getIntent().getStringExtra("u");
        upass = getIntent().getStringExtra("p");

    }

    public void login(View view) {
        if(et_uname.getText().toString().equals(uname) &&
        et_pass.getText().toString().equals(upass)){

            Toast.makeText(this,
                    "Success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,
                    "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoRegsisterpage(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
