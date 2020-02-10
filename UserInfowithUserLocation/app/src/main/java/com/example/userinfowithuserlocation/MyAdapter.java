package com.example.userinfowithuserlocation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.userinfowithuserlocation.model.User;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context ct;
    ArrayList<User> myList;
    public MyAdapter(DataViewActivity dataViewActivity, ArrayList<User> list) {
        ct = dataViewActivity;
        myList = list;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        holder.name.setText(myList.get(position).getUname());
        holder.mobile.setText(myList.get(position).getUmobile());
        holder.email.setText(myList.get(position).getUemail());
        Glide.with(ct).load(myList.get(position).getImagepath()).into(holder.iv);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val[] = myList.get(position).getUlatlan().split(",");
                double l1 = Double.parseDouble(val[0]);
                double l2 = Double.parseDouble(val[1]);
                Uri u = Uri.parse("geo:"+l1+","+l2);
                Intent i = new Intent(Intent.ACTION_VIEW,u);
                ct.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,mobile,email;
        ImageView iv;
        Button b;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b = itemView.findViewById(R.id.pl);
            name = itemView.findViewById(R.id.pn);
            mobile = itemView.findViewById(R.id.pm);
            email = itemView.findViewById(R.id.pe);
            iv = itemView.findViewById(R.id.pic);
        }
    }
}
