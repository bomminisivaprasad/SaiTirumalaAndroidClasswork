package com.example.asynctasktest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MyTask extends AsyncTask<Void,Void,String> {


    String myUrl;
    Context ct;
    ProgressDialog pd;
    RecyclerView rv;
    List<Book> bookslist;
    public MyTask(String myProperUrl, MainActivity mainActivity, RecyclerView rv) {
        myUrl = myProperUrl;
        ct = mainActivity;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ct);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Please wait....");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL u = new URL(myUrl);
            HttpsURLConnection connection = (HttpsURLConnection)
                    u.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while((line = reader.readLine())!=null){
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
        //Toast.makeText(ct, s, Toast.LENGTH_SHORT).show();
        pd.dismiss();
        bookslist = new ArrayList<>();
        try {
            JSONObject rootJsonObject = new JSONObject(s);
            JSONArray itemsArray = rootJsonObject.getJSONArray("items");
            for(int i = 0;i<itemsArray.length();i++) {
                JSONObject indexObject = itemsArray.getJSONObject(i);
                JSONObject volumeinfo = indexObject.getJSONObject("volumeInfo");
                JSONObject imagelinkobject = volumeinfo.getJSONObject("imageLinks");
                String booktitle = volumeinfo.getString("title");
                String authors = volumeinfo.getString("authors");
                String imgurl = imagelinkobject.getString("thumbnail");
                //Glide.with(ct).load(imgurl).placeholder(R.drawable.images).into(bookImage);
                Book book = new Book(booktitle, authors, imgurl);
                bookslist.add(book);
            }
            rv.setLayoutManager(new LinearLayoutManager(ct));
            rv.setAdapter(new BooksAdapter(ct,bookslist));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
