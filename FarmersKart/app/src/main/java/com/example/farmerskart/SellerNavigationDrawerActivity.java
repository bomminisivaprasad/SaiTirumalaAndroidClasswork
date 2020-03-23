package com.example.farmerskart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerNavigationDrawerActivity extends AppCompatActivity {

    String sName,sMobile,sDistrict,sAddress,sTypeofUser,sImagepath,sLocation;

    SharedPreferences spreferences;

    CircleImageView sImage;
    TextView sname,smobile,sdistrict,saddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_navigation_drawer);
        sImage = findViewById(R.id.sellerImage);
        sname = findViewById(R.id.sellernam);
        smobile = findViewById(R.id.sellerMobile);
        sdistrict = findViewById(R.id.sellerDistrict);
        saddress = findViewById(R.id.sellerAddress);
        spreferences = getSharedPreferences("SellerPrefrence",MODE_PRIVATE);
        Intent i = getIntent();
        if(i.hasExtra("sname")){
            sName = i.getStringExtra("sname");
            sMobile = i.getStringExtra("smobile");
            sDistrict = i.getStringExtra("sdistrict");
            sAddress = i.getStringExtra("saddress");
            sTypeofUser = i.getStringExtra("stype");
            sImagepath = i.getStringExtra("simagepath");
            sLocation = i.getStringExtra("slocation");
            Log.d("Buyer",sName+" "+sMobile+" "+sDistrict+" "+sAddress+" "+sTypeofUser+" "+sLocation);
        }

        SharedPreferences.Editor editor = spreferences.edit();
        editor.putString("name",sName);
        editor.putString("mobile",sMobile);
        editor.putString("district",sDistrict);
        editor.putString("address",sAddress);
        editor.putString("type",sTypeofUser);
        editor.putString("imagepath",sImagepath);
        editor.putString("location",sLocation);
        editor.apply();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(this).load(spreferences.getString("imagepath","")).into(sImage);
        sname.setText(spreferences.getString("name","Not Founnd"));
        smobile.setText(spreferences.getString("mobile","Not Found"));
        sdistrict.setText(spreferences.getString("district","Not Found"));
        saddress.setText(spreferences.getString("address","Not Found"));
    }

    public void openAddItemspage(View view) {
        startActivity(new Intent(this,ItemsAddingForm.class));
    }



}
