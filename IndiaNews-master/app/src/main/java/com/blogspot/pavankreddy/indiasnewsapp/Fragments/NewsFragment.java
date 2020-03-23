package com.blogspot.pavankreddy.indiasnewsapp.Fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.pavankreddy.indiasnewsapp.Activities.CategoriesActivity;
import com.blogspot.pavankreddy.indiasnewsapp.ModelClasses.Article;
import com.blogspot.pavankreddy.indiasnewsapp.ModelClasses.NewsArticles;
import com.blogspot.pavankreddy.indiasnewsapp.Adapter.RecyclerViewAdapter;
import com.blogspot.pavankreddy.indiasnewsapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class NewsFragment extends Fragment
{

    private static final String BASE_URL = "https://newsapi.org/v2/top-headlines?country=in&category=";
    private static final String SAVE_STATE_KEY = "SAVE_STATE";
    private static final String POS_KEY ="POS";
    RecyclerView recyclerView;
    String category;
    String url;
    NewsArticles[] newsArticles;
    private ProgressBar progressBar;
 // Required empty public constructor
    public NewsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.news_recycler_view);
        progressBar = view.findViewById(R.id.progress_bar_fetch);
        progressBar.setVisibility(View.VISIBLE);
        String api_key = "&apiKey="+getResources().getString(R.string.api_key_valid);
        url = BASE_URL+category+api_key;
        newsArticles = new NewsArticles[1];

        if(savedInstanceState!=null)
        {
            progressBar.setVisibility(View.GONE);
            newsArticles[0] = (NewsArticles) savedInstanceState.getSerializable(SAVE_STATE_KEY);
            List<Article> articles = newsArticles[0].getArticles();
            recyclerView.setAdapter(new RecyclerViewAdapter(getContext(),articles));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            int position = savedInstanceState.getInt(POS_KEY,0);
            recyclerView.getLayoutManager().scrollToPosition(position);

        }
        else
        {
            loadData();
        }
        return view;
    }

    private void loadData() {
        final StringRequest request = new StringRequest(url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                newsArticles[0] = gson.fromJson(response,NewsArticles.class);
                List<Article> articles = newsArticles[0].getArticles();
                recyclerView.setAdapter(new RecyclerViewAdapter(getContext(),articles));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), R.string.data_retrevial_error_message, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null)
        {
            category = getArguments().getString(CategoriesActivity.CATEGORY_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(SAVE_STATE_KEY,newsArticles[0]);
        int current_visible_position = 0;
        current_visible_position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        outState.putInt(POS_KEY,current_visible_position);
    }


}
