package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recycle);
        int images[] = {R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher};

        String names[] = {"Mastan","Gopal","Sai","Ramesh",
                "Sravani","Mohan","Akhila","Sreenu","Vani","Kiranmai"};

        String mobilenos[] = {"1234567890","2134567890",
                "3124567890","4123567890","5123467890",
        "6123457890","7123456890","8123456790","9123456780","0123456789"};

       String emaiIds[] = {"a@gmail.com","b@gmail.com","c@gmail.com",
       "d@gmail.com","e@gmail.com","f@gmail.com","g@gmail.com",
       "h@gmail.com","i@gmail.com","j@gmail.com"};

        LinearLayoutManager manager
                = new LinearLayoutManager(this);
       rv.setLayoutManager(manager);
       MyAdapter adapter = new MyAdapter(this,images,names,mobilenos,emaiIds);
       rv.setAdapter(adapter);

    }
}
