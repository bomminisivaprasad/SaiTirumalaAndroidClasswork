package com.example.farmerskart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyerNavigationDrawerActivity extends AppCompatActivity {

    String bName,bMobile,bDistrict,bAddress,bTypeofUser,bImagepath,bLocation;

    SharedPreferences bpreferences;

    RecyclerView rv;
    Spinner sp_district,sp_category;

    FirebaseDatabase database;
    DatabaseReference reference;

    List<MyItem> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_navigation_drawer);

        rv = findViewById(R.id.recycler);
        sp_district = findViewById(R.id.search_district);
        sp_category = findViewById(R.id.search_category);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        bpreferences = getSharedPreferences("BuyerPrefrence",MODE_PRIVATE);
        Intent i = getIntent();
        if(i.hasExtra("bname")){
            bName = i.getStringExtra("bname");
            bMobile = i.getStringExtra("bmobile");
            bDistrict = i.getStringExtra("bdistrict");
            bAddress = i.getStringExtra("baddress");
            bTypeofUser = i.getStringExtra("btype");
            bImagepath = i.getStringExtra("bimagepath");
            bLocation = i.getStringExtra("blocation");
            Log.d("Buyer",bName+" "+bMobile+" "+bDistrict+" "+bAddress+" "+bTypeofUser+" "+bLocation);
        }

        SharedPreferences.Editor editor = bpreferences.edit();
        editor.putString("name",bName);
        editor.putString("mobile",bMobile);
        editor.putString("district",bDistrict);
        editor.putString("address",bAddress);
        editor.putString("type",bTypeofUser);
        editor.putString("imagepath",bImagepath);
        editor.putString("location",bLocation);
        editor.apply();
    }


    public void getData(View view) {
        String selectedDidtrict = sp_district.getSelectedItem().toString();
        String slectedCategory = sp_category.getSelectedItem().toString();
        if((selectedDidtrict.equals("Select District")) || (slectedCategory.equals("Select Type of Product"))){
            Toast.makeText(this, "Please select the District and Category", Toast.LENGTH_SHORT).show();
        }else{
            itemsList = new ArrayList<>();
            reference.child("Items").child(selectedDidtrict).child(slectedCategory).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        MyItem item = snapshot.getValue(MyItem.class);
                        itemsList.add(item);
                    }
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setAdapter(new ItemsAdapter(getApplicationContext(),itemsList));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
