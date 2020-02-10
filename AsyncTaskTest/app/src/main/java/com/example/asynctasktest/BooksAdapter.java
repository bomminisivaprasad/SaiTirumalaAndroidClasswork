package com.example.asynctasktest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {
    Context ct;
    List<Book> bookslist;

    public BooksAdapter(Context ct, List<Book> bookslist) {
        this.ct = ct;
        this.bookslist = bookslist;
    }

    @NonNull
    @Override
    public BooksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.MyViewHolder holder, int position) {
        holder.tv1.setText(bookslist.get(position).getTitle());
        holder.tv2.setText(bookslist.get(position).getAuthor());
        Glide.with(ct).load(bookslist.get(position).getUrl()).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return bookslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv1,tv2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imageView);
            tv1 = itemView.findViewById(R.id.textView);
            tv2 = itemView.findViewById(R.id.textView2);
        }
    }
}
