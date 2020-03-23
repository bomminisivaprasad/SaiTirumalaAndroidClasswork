package com.example.user.e_quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.e_quiz.MyModel.ModelC;
import com.example.user.e_quiz.MyModel.MyScore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView textViewTotalScore;
    private ModelC modelC;
    String models1;
    private DatabaseReference databaseReference;
    int total;
    private ArrayList<ModelC> modelCArrayList;
    private MyAdapter adapter;
    private ProgressBar progressBar;
    private String e;
    private String crct;
    private ProgressDialog progressDoalog;
    Button buttonAddScore;
    private MyScore myScore;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private final String lF = "languageFundamentals";
    private final String oA = "operatorsAssignments";
    private final String dAC = "declarationsAccessControl";
    private final String aS = "assertions";
    private final String eX = "exceptions";
    private final String iC = "innerClasses";
    private final String gC = "garbageCollections";
    private final String jlC = "javalangClass";
    private final String fC = "javalangClass";
    private final String oC = "objectsandCollections";
    private final String tH = "threads";
    private final String oQ = "overallQuestions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.tick);

        progressBar = findViewById(R.id.progres);
        RatingBar ratingBar = findViewById(R.id.myRating);

        TextView textViewTotal = findViewById(R.id.txtTotal);
        TextView textViewCorrect = findViewById(R.id.txtCorrect);
        TextView textViewWrong = findViewById(R.id.txtWrong);
        TextView textViewReview = findViewById(R.id.txtviewReview);
        Button buttonExp = findViewById(R.id.buttonExplanation);

        RecyclerView recyclerView = findViewById(R.id.myRecycler);
        modelCArrayList = new ArrayList<>();

        Intent intent = getIntent();
        String tl = "total";
        final String tot = intent.getStringExtra(tl);
        String co = "correct";
        crct = intent.getStringExtra(co);
        String wr = "wrong";
        String wrng = intent.getStringExtra(wr);

        String sb = "subject";
        e = intent.getStringExtra(sb);
        Log.i("program", e);
        textViewTotal.setText(tot);
        textViewCorrect.setText(crct);
        textViewWrong.setText(wrng);

        updateScoreBoard();
        /*buttonAddScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        });*/

        String c0t = "You have to Concentrate More";
        String c1t = "You have to Concentrate More";
        String c2t = "It was nice attempt but you need to Practice Well";
        String c3t = "It was good attempt but you need to work hard!";
        String c4t = "It was better attempt but you need to do more practice";
        String c5t = "Good,Keep It Up!";
        switch (crct) {
            case "5":
                textViewReview.setText(c5t);
                break;
            case "4":
                textViewReview.setText(c4t);
                break;
            case "3":
                textViewReview.setText(c3t);
                break;
            case "2":
                textViewReview.setText(c2t);
                break;
            case "1":
                textViewReview.setText(c1t);
                break;
            case "0":
                textViewReview.setText(c0t);
                break;
        }
        ratingBar.setRating(Float.parseFloat(crct));

        buttonExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                modelCArrayList.clear();
                ImageView imageView = findViewById(R.id.imgswipe);
                imageView.setVisibility(View.VISIBLE);
                progressDoalog = new ProgressDialog(ResultActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (progressDoalog.getProgress() <= progressDoalog
                                    .getMax()) {
                                Thread.sleep(200);
                                handle.sendMessage(handle.obtainMessage());
                                if (progressDoalog.getProgress() == progressDoalog
                                        .getMax()) {
                                    progressDoalog.dismiss();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                update();
            }

            final Handler handle = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressBar.incrementProgressBy(1);
                    handle.sendMessage(handle.obtainMessage());
                }
            };

        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new MyAdapter(this, modelCArrayList);
        recyclerView.setAdapter(adapter);

    }

    private void updateScoreBoard() {
        String emailID = JavaActivity.user.getEmail();
       /* Log.i("correct",crct);
        Log.i("email",emailID);
       */
        String ms = "MyScore";
        databaseReference = firebaseDatabase.getReference().child(ms);
        String m = "mail";
        switch (e) {
            case "LanguageFundamentals":
                Query query = databaseReference.orderByChild(m).equalTo(emailID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(lF).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "OperatorsAssignments":
                Query query1 = databaseReference.orderByChild(m).equalTo(emailID);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(oA).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "DeclarationsAccessControl":
                Query query2 = databaseReference.orderByChild(m).equalTo(emailID);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(dAC).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Assertions":
                Query query3 = databaseReference.orderByChild(m).equalTo(emailID);
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(aS).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Exceptions":
                Query query4 = databaseReference.orderByChild(m).equalTo(emailID);
                query4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(eX).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "InnerClass":
                Query query5 = databaseReference.orderByChild(m).equalTo(emailID);
                query5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(iC).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "GarbageCollections":
                Query query6 = databaseReference.orderByChild(m).equalTo(emailID);
                query6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(gC).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Javalang":
                Query query7 = databaseReference.orderByChild(m).equalTo(emailID);
                query7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(jlC).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "FlowControl":
                Query query8 = databaseReference.orderByChild(m).equalTo(emailID);
                query8.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(fC).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "ObjectsCollections":
                Query query9 = databaseReference.orderByChild(m).equalTo(emailID);
                query9.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(oC).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Threads":
                Query query10 = databaseReference.orderByChild(m).equalTo(emailID);
                query10.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(tH).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "JAVAQuestions":
                Query query11 = databaseReference.orderByChild(m).equalTo(emailID);
                query11.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child(oQ).setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }
    }

    private void update() {

        databaseReference = FirebaseDatabase.getInstance().getReference(e);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);
                    modelC = snapshot.getValue(ModelC.class);
                    modelCArrayList.add(modelC);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ResultActivity.this, getString(R.string.cancelErr), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent m = new Intent(getApplicationContext(), JavaActivity.class);
        startActivity(m);
    }
}
