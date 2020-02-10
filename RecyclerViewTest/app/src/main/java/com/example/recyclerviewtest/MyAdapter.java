package com.example.recyclerviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context ct;
    int myImages[];
    String myNames[];
    String myMobilenos[];
    String myEmailIds[];

    public MyAdapter(MainActivity mainActivity, int[] images, String[] names, String[] mobilenos, String[] emaiIds) {
        ct = mainActivity;
        myImages = images;
        myNames = names;
        myMobilenos = mobilenos;
        myEmailIds = emaiIds;

    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(ct)
                .inflate(R.layout.row,parent,
                        false);
        MyViewHolder viewHolder =
                new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder,
                                 int position) {
        holder.iv.setImageResource(myImages[position]);
        holder.tv_name.setText(myNames[position]);
        holder.tv_mobileno.setText(myMobilenos[position]);
        holder.tv_email.setText(myEmailIds[position]);

    }

    @Override
    public int getItemCount() {
        return myImages.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_name,tv_mobileno,tv_email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.profilepic);
            tv_name = itemView.findViewById(R.id.profilename);
            tv_mobileno = itemView.findViewById(R.id.mobileno);
            tv_email = itemView.findViewById(R.id.email);
        }
    }
}
