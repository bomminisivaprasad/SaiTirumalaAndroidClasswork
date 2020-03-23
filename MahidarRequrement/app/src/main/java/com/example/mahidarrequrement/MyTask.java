package com.example.mahidarrequrement;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class MyTask extends AsyncTask<String,Void,String> {
    Context ct;
    String myUrl;
    ProgressDialog pd;

    public MyTask(String url, MainActivity mainActivity) {
        ct = mainActivity;
        myUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ct);
        pd.setMessage("Please wait....");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL u = new URL(myUrl);
            HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line=reader.readLine())!=null){
                builder.append(line);
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        Toast.makeText(ct,
                ""+s, Toast.LENGTH_SHORT).show();
    }
}
