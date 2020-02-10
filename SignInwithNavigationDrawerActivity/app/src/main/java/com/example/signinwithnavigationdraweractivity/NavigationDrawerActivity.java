package com.example.signinwithnavigationdraweractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationDrawerActivity extends AppCompatActivity {

    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        dl = findViewById(R.id.dLayout);
        nv = findViewById(R.id.nv);
        at = new ActionBarDrawerToggle(this,dl,
                R.string.open,R.string.close);
        dl.addDrawerListener(at);
        at.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        String username = i.getStringExtra("n");
        String usermail = i.getStringExtra("e");
        String photourl = i.getStringExtra("url");
        Toast.makeText(this,
                ""+photourl, Toast.LENGTH_SHORT).show();
        View v = nv.getHeaderView(0);
        TextView tv_name = v.findViewById(R.id.name);
        TextView tv_email = v.findViewById(R.id.email);
        CircleImageView iv = v.findViewById(R.id.image);
        tv_name.setText(username);
        tv_email.setText(usermail);
        Glide.with(this).load(photourl).into(iv);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(at.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
