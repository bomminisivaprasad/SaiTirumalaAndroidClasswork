package com.blogspot.pavankreddy.indiasnewsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.pavankreddy.indiasnewsapp.Activities.NewsDetailsActivity;
import com.blogspot.pavankreddy.indiasnewsapp.ModelClasses.Article;
import com.blogspot.pavankreddy.indiasnewsapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewInformation>
{
    public static final String SHARED_PREFERENCE_FILE_NAME = "NEWS_FOR_WIDGET";
    private Context context;
    private List<Article> articles;

    SharedPreferences sharedPreferences;

    public RecyclerViewAdapter(){}
    public RecyclerViewAdapter(Context context, List<Article> articles)
    {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewInformation onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewInformation(LayoutInflater.from(context).inflate(R.layout.news_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewInformation holder, int position)
    {
        holder.textView.setText(articles.get(position).getTitle());
        Glide.with(context).load(articles.get(position).getUrlToImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewInformation extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView;
        TextView textView;

        public ViewInformation(View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_article_image);
            textView = itemView.findViewById(R.id.news_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME,context.MODE_PRIVATE);

            int position = getAdapterPosition();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NewsDetailsActivity.TITLE_KEY,articles.get(position).getTitle());
            editor.putString(NewsDetailsActivity.NEWS_LINK_KEY,articles.get(position).getUrl());
            editor.apply();

            Intent intent = new Intent(context,NewsDetailsActivity.class);
            intent.putExtra(NewsDetailsActivity.TITLE_KEY,articles.get(position).getTitle());
            intent.putExtra(NewsDetailsActivity.DESC_KEY,articles.get(position).getDescription());
            intent.putExtra(NewsDetailsActivity.IMAGE_LINK_KEY,articles.get(position).getUrlToImage());
            intent.putExtra(NewsDetailsActivity.NEWS_LINK_KEY,articles.get(position).getUrl());
            context.startActivity(intent);

        }
    }
}
