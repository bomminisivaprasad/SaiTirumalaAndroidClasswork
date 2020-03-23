package com.example.edsirrequirement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context ct;
    int bundleNumber,numberofBooklets;
    IntentIntegrator myqrScan;
    String myMarks;
    callbackData callbackData;
    String rollno;
    public MyAdapter(BookletsScanningActivity bookletsScanningActivity, int bundleNumber, int numberofBooklets, IntentIntegrator qrScan, String marks, BookletsScanningActivity bookletsScanningActivity1, String contents) {
        ct = bookletsScanningActivity;
        this.bundleNumber = bundleNumber;
        this.numberofBooklets = numberofBooklets;
        myqrScan = qrScan;
        myMarks = marks;
        callbackData = bookletsScanningActivity1;
        rollno = contents;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
        return new MyViewHolder(v);
    }

    public interface callbackData{
        void getData(int position, String rollno, int bundleNumber, int numberofBooklets, String rollnumberMarks);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {

        holder.tv_sno.setText(""+position);
        holder.tv_marks.setText(""+myMarks);
       holder.scanButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //qrScan.initiateScan();
               myqrScan.initiateScan();

               callbackData.getData(position,rollno,bundleNumber,numberofBooklets,myMarks);

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
