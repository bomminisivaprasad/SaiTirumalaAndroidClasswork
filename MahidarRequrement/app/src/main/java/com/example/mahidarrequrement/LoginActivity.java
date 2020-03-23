package com.example.mahidarrequrement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            //EventLogTags.Description.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Please Check your Internetconnection")
                    .setPositiveButton("OK", null).show();
        }else {
            Toast.makeText(this,
                    "Iternet is Available", Toast.LENGTH_SHORT).show();

        }

    }

    public void login(View view) {
        String u = et_username.getText().toString();
        String p = et_password.getText().toString();
        if (u.isEmpty() || p.isEmpty()) {
            Toast.makeText(this,
                    "Please fill the details...", Toast.LENGTH_SHORT).show();
        } else {
            if ((u.equals("manikanta.p1099@gmail.com")) && (p.equals("mani1234"))) {
                startActivity(new Intent(this, MainActivity.class));

            } else if ((u.equals("msudhareddy456@gmail.com")) && (p.equals("sudha1299"))) {
                startActivity(new Intent(this, MainActivity.class));
            } else if ((u.equals("srinvaspaladugu94@gmail.com")) && (p.equals("srinu1234"))) {
                startActivity(new Intent(this, MainActivity.class));
            } else if ((u.equals("kvenunaidu143@gmail.com")) && (p.equals("venu1234"))) {
                startActivity(new Intent(this, MainActivity.class));
            }else {
                Toast.makeText(this, "InValid user...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
