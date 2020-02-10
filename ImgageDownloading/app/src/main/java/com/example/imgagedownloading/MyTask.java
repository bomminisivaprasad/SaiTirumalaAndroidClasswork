package com.example.imgagedownloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class MyTask extends AsyncTask<Bitmap,Void,Bitmap>
{
    Context ct;
    ImageView iv;

    public MyTask(MainActivity mainActivity, ImageView iv) {
        this.iv = iv;
        ct = mainActivity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
        try {
            URL u = new URL("https://image.shutterstock.com/image-photo/kiev-ukraine-may-26-2015-260nw-283385381.jpg");
            HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            InputStream i = connection.getInputStream();
            Bitmap b = BitmapFactory.decodeStream(i);
            return  b;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        iv.setImageBitmap(bitmap);
    }
}
