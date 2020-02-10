package com.example.firedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    ArrayList<User> myList;
    Context ct;

    public MyAdapter(DetailsActivity detailsActivity, ArrayList<User> list) {
        ct = detailsActivity;
        myList=list;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tv1.setText(myList.get(position).getName());
        holder.tv2.setText(myList.get(position).getMobile());
        holder.tv3.setText(myList.get(position).getEmail());
        Glide.with(ct).load(myList.get(position).getFilepath()).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv1,tv2,tv3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.name);
            tv2 = itemView.findViewById(R.id.mobileno);
            tv3 = itemView.findViewById(R.id.emailid);
            iv = itemView.findViewById(R.id.imageview);
        }
    }
}
