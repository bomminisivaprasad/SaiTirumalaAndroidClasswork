package com.example.firebasedatabasetest;

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

import java.util.List;

class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    Context ct;
    List<Student> studentList;
    public StudentAdapter(Context applicationContext, List<Student> list) {
        ct = applicationContext;
        studentList = list;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(ct)
                .inflate(R.layout.row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentAdapter.MyViewHolder holder, final int position) {
        holder.rollno.setText(studentList.get(position).getRollno());
        holder.name.setText(studentList.get(position).getName());
        holder.mobileno.setText(studentList.get(position).getMobileno());
        //holder.email.setText(studentList.get(position).get());
        holder.branch.setText(studentList.get(position).getBranch());
        Glide.with(ct).load(studentList.get(position)
                .getImagepath()).into(holder.iv);
        holder.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latlong = studentList.get(position).getLatlong();
                Uri u = Uri.parse("geo:"+latlong);
                Intent i = new Intent(Intent.ACTION_VIEW,u);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ct.startActivity(i);
            }
        });

        holder.mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:"+studentList.get(position).getMobileno());
                Intent i = new Intent(Intent.ACTION_DIAL,u);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ct.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView rollno,name,mobileno,email,branch;
        Button locationButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.studentImage);
            rollno = itemView.findViewById(R.id.studentRollno);
            name = itemView.findViewById(R.id.studentName);
            mobileno = itemView.findViewById(R.id.studentMobileno);
            //email = itemView.findViewById(R.id.studentEmail);
            branch = itemView.findViewById(R.id.studentBranch);
            locationButton = itemView.findViewById(R.id.studentLocation);

        }
    }
}
