package com.example.imagedownloading;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyTask extends AsyncTask<Void,Void,Bitmap> {
    ImageView myIv;
    ProgressDialog pd;
    Context ct;

    public MyTask(ImageView iv, MainActivity mainActivity) {
        myIv = iv;
        ct = mainActivity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ct);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Please wait.......");
        pd.show();


    }

    @Override
    protected Bitmap doInBackground(Void... bitmaps) {
        String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Android_robot.svg/2000px-Android_robot.svg.png";

        try {
            URL u = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection)
                    u.openConnection();
            InputStream is = connection.getInputStream();
            Bitmap b = BitmapFactory.decodeStream(is);
            //iv.setImageBitmap(b);
            return  b;
        } catch (Exception e) {
            e.printStackTrace();
        }





        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        myIv.setImageBitmap(bitmap);
        pd.dismiss();


    }
}
