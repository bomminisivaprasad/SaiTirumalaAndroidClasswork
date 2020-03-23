package com.example.facultyheiringtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context ct;
    List<MyQuestion> qlist;
    public CallbackResult callbackResult;
    private RadioButton lastCheckedRB = null;

    public MyAdapter(MainActivity mainActivity, List<MyQuestion> list, CallbackResult result) {
        callbackResult = result;
        ct = mainActivity;
        qlist = list;
    }

    public interface CallbackResult {
        void getresult(int position,String question, String selectedoption, String exactAnswer);
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {
        holder.tv.setText(qlist.get(position).getQuestion());
        holder.r1.setText(qlist.get(position).getOpt1());
        holder.r2.setText(qlist.get(position).getOpt2());
        holder.r3.setText(qlist.get(position).getOpt3());
        holder.r4.setText(qlist.get(position).getOpt4());


        holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked_rb = (RadioButton) group.findViewById(checkedId);
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(true);
                }
                //store the clicked radiobutton
                lastCheckedRB = checked_rb;
                callbackResult.getresult(position,qlist.get(position).getQuestion(),lastCheckedRB.getText().toString(),qlist.get(position).getAnswer());
            }
        });

    }

    @Override
    public int getItemCount() {
        return qlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        RadioButton r1, r2, r3, r4;
        RadioGroup rg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.t_q);
            r1 = itemView.findViewById(R.id.t_o1);
            r2 = itemView.findViewById(R.id.t_o2);
            r3 = itemView.findViewById(R.id.t_o3);
            r4 = itemView.findViewById(R.id.t_o4);
            rg = itemView.findViewById(R.id.radiogroup);
        }
    }
}
