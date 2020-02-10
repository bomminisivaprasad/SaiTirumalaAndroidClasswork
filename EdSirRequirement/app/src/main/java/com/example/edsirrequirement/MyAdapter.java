package com.example.edsirrequirement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context ct;
    int bundleNumber,numberofBooklets;
    IntentIntegrator myqrScan;
    String myMarks;
    public MyAdapter(BookletsScanningActivity bookletsScanningActivity, int bundleNumber, int numberofBooklets, IntentIntegrator qrScan,String marks) {
        ct = bookletsScanningActivity;
        this.bundleNumber = bundleNumber;
        this.numberofBooklets = numberofBooklets;
        myqrScan = qrScan;
        myMarks = marks;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        if(position<=8){
            holder.tv_sno.setText("0" + (position + 1));
        }else {
            holder.tv_sno.setText("" + (position + 1));
        }
        holder.tv_marks.setText(myMarks);
       /* position = 0;
        for (int i = 0;i<=numberofBooklets;i++){
                if(position==i){
                    holder.itemView.setVisibility(View.VISIBLE);
                }else {
                    holder.itemView.setVisibility(View.GONE);
                }
                position++;
        }*/

       holder.scanButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //qrScan.initiateScan();
               myqrScan.initiateScan();
           }
       });

    }

    @Override
    public int getItemCount() {
        return numberofBooklets;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sno,tv_marks;
        Button statusButton,scanButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sno = itemView.findViewById(R.id.sno);
            tv_marks = itemView.findViewById(R.id.marks);
            statusButton = itemView.findViewById(R.id.statusButton);
            scanButton = itemView.findViewById(R.id.scanButton);
        }
    }
}
