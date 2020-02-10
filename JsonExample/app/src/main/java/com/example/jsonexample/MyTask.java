package com.example.jsonexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class MyTask extends AsyncTask<Void,Void,String> {

    Context ct;
    String myUrl;
    TextView mytv;
    ProgressDialog pd;

    public MyTask(MainActivity mainActivity, String url, TextView tv) {
        ct = mainActivity;
        myUrl=url;
        mytv=tv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ct);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Please wait.....");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL u = new URL(myUrl);
            HttpsURLConnection connection =
                    (HttpsURLConnection)u.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuilder builder = new StringBuilder();
            while ((line=reader.readLine())!=null){
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        Toast.makeText(ct, ""+s, Toast.LENGTH_SHORT).show();
    }
}
