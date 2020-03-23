package com.example.user.e_quiz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {

    private final Context context;
    private final String[] s2;

    private final String ri = "RULES AND INSTRUCTIONS";
    private final String fcm = "1)Double clicking the answer will not be selected\n2)No negative evaluation\n" +
            "3)For every right answer,1 mark will be awarded\n4)Time alloted-20 minutes\n" +
            "5)After completion of time your result will be displayed\n6)After completion of questions , " +
            "your result will be displayed\n7)You can view answers with explanation after completion\n" +
            "8)When you quit the exam in the middle ,your will not get any score.\n" + "ALL THE BEST :)";
    private final String canl = "CANCEL";
    private final String taq = "TAKE A QUIZ";
    private final String position = "pos";

    public RecyclerAdapter2(JavaActivity javaActivity, String[] s1) {
        this.context = javaActivity;
        this.s2 = s1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design2, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.textViewType.setText(s2[i]);
        myViewHolder.buttonAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyGifDialog.Builder((Activity) context)
                        .setTitle(ri)
                        .setMessage(fcm)
                        .setNegativeBtnText(canl)
                        .setPositiveBtnBackground("#FF4081")
                        .setPositiveBtnText(taq)
                        .setGifResource(R.drawable.rulesinstru)
                        .setNegativeBtnBackground("#FFA9A7A8")
                        .isCancellable(false)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                Intent intent = new Intent(context, DetailActivity1.class);
                                intent.putExtra(position, i);
                                context.startActivity(intent);
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {

                            }
                        })
                        .build();


            }
        });

    }

    @Override
    public int getItemCount() {
        return s2.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewType;
        final Button buttonAttempt;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewType = itemView.findViewById(R.id.txtType);
            buttonAttempt = itemView.findViewById(R.id.btnAttempt);

            Dialog rulesdialog = new Dialog(context);
        }
    }
}
