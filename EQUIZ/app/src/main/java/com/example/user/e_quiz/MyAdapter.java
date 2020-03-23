package com.example.user.e_quiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.e_quiz.MyModel.ModelC;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final ArrayList<ModelC> modelCArrayList;
    private final Context context;


    public MyAdapter(ResultActivity resultActivity, ArrayList<ModelC> modelCArrayList) {
        this.context = resultActivity;
        this.modelCArrayList = modelCArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.design1, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ModelC modelC = modelCArrayList.get(i);

        myViewHolder.textView.setText(modelC.getQuestion());
        myViewHolder.textView2.setText(modelC.getOption1());
        myViewHolder.textView3.setText(modelC.getOption2());
        myViewHolder.textView4.setText(modelC.getOption3());
        myViewHolder.textView5.setText(modelC.getOption4());
        myViewHolder.textView6.setText(modelC.getAnswer());
        myViewHolder.textView7.setText(modelC.getExplanation());

        Log.i("questions", modelC.getQuestion());
    }

    @Override
    public int getItemCount() {
        return modelCArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final TextView textView2;
        final TextView textView3;
        final TextView textView4;
        final TextView textView5;
        final TextView textView6;
        final TextView textView7;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtDisplay);
            textView2 = itemView.findViewById(R.id.txtDisplay2);
            textView3 = itemView.findViewById(R.id.txtDisplay3);
            textView4 = itemView.findViewById(R.id.txtDisplay4);
            textView5 = itemView.findViewById(R.id.txtDisplay5);
            textView6 = itemView.findViewById(R.id.txtDisplay6);
            textView7 = itemView.findViewById(R.id.txtDisplay7);
        }
    }
}

