package com.example.asynctasktest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String url = "https://www.googleapis.com/books/v1/volumes?q=";
    EditText et;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.bookname);
        rv = findViewById(R.id.recycler);


    }

    public void search(View view)
    {
        String bookname = et.getText().toString();
        String myProperUrl = url+bookname;
       // Toast.makeText(this, myProperUrl, Toast.LENGTH_SHORT).show();
        MyTask task = new MyTask(myProperUrl,this,rv);
        task.execute();

    }
}
