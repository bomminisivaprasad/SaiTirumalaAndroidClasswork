package com.example.attendanceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context ct;
    List<Student> myList;
    public CallbackAttendance callbackAttendance;
    static int pos;
    public MyAdapter(Context applicationContext, List<Student> studentslist,CallbackAttendance callbackAttendance) {
        ct = applicationContext;
        myList = studentslist;
        this.callbackAttendance=callbackAttendance;
    }




    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
        return new MyViewHolder(V);
    }

    public interface CallbackAttendance{
        void getAttenance(int position,boolean attend);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {
        holder.tv1.setText((position+1)+" . "+myList.get(position).getRollno());
        holder.tv2.setText(myList.get(position).getName());
        holder.ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callbackAttendance.getAttenance(position,isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        CheckBox ch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv_r);
            tv2 = itemView.findViewById(R.id.tv_n);
            ch = itemView.findViewById(R.id.check);
            if(ch.isChecked()){
                pos = getAdapterPosition();
            }

        }
    }
}
