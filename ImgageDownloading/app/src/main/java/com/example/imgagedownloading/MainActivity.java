package com.example.imgagedownloading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.imageView);
    }

    public void download(View view) {
       /* MyTask task = new MyTask(this,iv);
        task.execute();*/
        try {
            URL u = new URL("https://image.shutterstock.com/image-photo/kiev-ukraine-may-26-2015-260nw-283385381.jpg");
            HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            InputStream i = connection.getInputStream();
            Bitmap b = BitmapFactory.decodeStream(i);
            iv.setImageBitmap(b);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
