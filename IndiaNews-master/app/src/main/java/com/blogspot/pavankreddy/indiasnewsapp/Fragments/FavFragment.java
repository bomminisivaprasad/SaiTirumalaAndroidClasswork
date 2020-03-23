package com.blogspot.pavankreddy.indiasnewsapp.Fragments;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.pavankreddy.indiasnewsapp.Activities.MainActivity;
import com.blogspot.pavankreddy.indiasnewsapp.Data.DatabaseContract;
import com.blogspot.pavankreddy.indiasnewsapp.ModelClasses.Article;
import com.blogspot.pavankreddy.indiasnewsapp.R;
import com.blogspot.pavankreddy.indiasnewsapp.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment
{
    List<Article> articles;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    public FavFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = v.findViewById(R.id.fav_recyclerview);
        articles = new ArrayList<>();
        if(articles!=null)
        {
            articles.clear();
        }
        loadData();
        return v;
    }

    private void loadData() {
        final ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(DatabaseContract.DatabaseEntry.CONTENT_URI,null,null,null,null);
        if(cursor!=null&&cursor.moveToFirst())
        {
            cursor.moveToFirst();
            do {
                Article article = new Article();
                article.setAuthor("");
                article.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_DESC)));
                article.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_TITLE)));
                article.setUrlToImage(cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_IMAGE_LINK)));
                article.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_URL_LINK)));
                article.setPublishedAt("");
                article.setSource(null);
                articles.add(article);
            }
            while(cursor.moveToNext());
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(),articles);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else
        {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.articles_not_found_msg)
                    .setMessage("EXPLORE APP AND ADD FAVORITES")
                    .setCancelable(true)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(articles!=null)
        {
            articles.clear();
            loadData();
        }
        else {
            loadData();
        }

    }
}
