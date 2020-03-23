package com.example.user.e_quiz;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class DetailActivity1 extends AppCompatActivity {

    private int s2;
    private TextView textView;
    private TextView textViewScore;
    private final int total = 5;
    private int score = 0;
    private int correct = 0;
    private int wrong = 0;
    private String answer;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private int QuestionNumber = 0;
    private int numberOfQuestions = 0;
    private ProgressBar progressBarJava;

    private final String T = "total";
    private final String S = "total";
    private final String C = "correct";
    private final String W = "wrong";

    private final String TU = "Time Up!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);

        String p = "pos";
        s2 = getIntent().getIntExtra(p, 0);

        textView = findViewById(R.id.txtQue);
        textViewScore = findViewById(R.id.score);
        TextView textViewTimer = findViewById(R.id.txtTimer);
        button1 = findViewById(R.id.choice1);
        button2 = findViewById(R.id.choice2);
        button3 = findViewById(R.id.choice3);
        button4 = findViewById(R.id.choice4);
        progressBarJava = findViewById(R.id.myJavaProgressBar);

        Firebase.setAndroidContext(getApplicationContext());

        MYAsyncQuestion myAsyncQuestion = new MYAsyncQuestion();
        myAsyncQuestion.execute();

        reverseTimer(textViewTimer);
    }

    @SuppressLint("StaticFieldLeak")
    class MYAsyncQuestion extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarJava.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBarJava.setVisibility(View.GONE);

            if (s2 == 0) {
                String LF = "LanguageFundamentals";
                updateQue(LF);
                updateAnswer(LF);
            } else if (s2 == 1) {
                String OA = "OperatorsAssignments";
                updateQue(OA);
                updateAnswer(OA);
            } else if (s2 == 2) {
                String DAC = "DeclarationsAccessControl";
                updateQue(DAC);
                updateAnswer(DAC);
            } else if (s2 == 3) {
                String ASS = "Assertions";
                updateQue(ASS);
                updateAnswer(ASS);
            } else if (s2 == 4) {
                String EXC = "Exceptions";
                updateQue(EXC);
                updateAnswer(EXC);
            } else if (s2 == 5) {
                String IClass = "InnerClass";
                updateQue(IClass);
                updateAnswer(IClass);
            } else if (s2 == 6) {
                String GC = "GarbageCollections";
                updateQue(GC);
                updateAnswer(GC);
            } else if (s2 == 7) {
                String JL = "Javalang";
                updateQue(JL);
                updateAnswer(JL);
            } else if (s2 == 8) {
                String FC = "FlowControl";
                updateQue(FC);
                updateAnswer(FC);
            } else if (s2 == 9) {
                String OC = "ObjectsCollections";
                updateQue(OC);
                updateAnswer(OC);
            } else if (s2 == 10) {
                String TH = "Threads";
                updateQue(TH);
                updateAnswer(TH);
            } else if (s2 == 11) {
                String JQ = "JAVAQuestions";
                updateQue(JQ);
                updateAnswer(JQ);
            }

        }
    }


    private void updateQue(String str) {

        if (numberOfQuestions > 4) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra(T, String.valueOf(total));
            intent.putExtra(S, score);
            intent.putExtra(C, String.valueOf(correct));
            intent.putExtra(W, String.valueOf(wrong));
            String sub = "subject";
            intent.putExtra(sub, str);
            finish();
            startActivity(intent);
        } else {
            progressBarJava.setVisibility(View.VISIBLE);
            String ques = "/question";
            String slash = "/";
            String MFB = "https://e-quiz-a7f0a.firebaseio.com/";
            Firebase mQuestion = new Firebase(MFB + str + slash + QuestionNumber + ques);
            mQuestion.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    progressBarJava.setVisibility(View.GONE);
                    String question = dataSnapshot.getValue(String.class);
                    textView.setText(question);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            String o1 = "/option1";
            Firebase mchoice1 = new Firebase(MFB + str + slash + QuestionNumber + o1);
            mchoice1.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice1 = dataSnapshot.getValue(String.class);
                    button1.setText(choice1);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            String o2 = "/option2";
            Firebase mchoice2 = new Firebase(MFB + str + slash + QuestionNumber + o2);
            mchoice2.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice2 = dataSnapshot.getValue(String.class);
                    button2.setText(choice2);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            String o3 = "/option2";
            Firebase mchoice3 = new Firebase(MFB + str + slash + QuestionNumber + o3);
            mchoice3.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice3 = dataSnapshot.getValue(String.class);
                    button3.setText(choice3);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            String o4 = "/option4";
            Firebase mchoice4 = new Firebase(MFB + str + slash + QuestionNumber + o4);
            mchoice4.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice4 = dataSnapshot.getValue(String.class);
                    button4.setText(choice4);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            String ans = "/answer";
            Firebase mAnswer = new Firebase(MFB + str + slash + QuestionNumber + ans);
            mAnswer.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    answer = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            QuestionNumber++;
            numberOfQuestions++;
        }

    }

    private void updateAnswer(final String string) {

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button1.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                } else {
                    wrong++;
                    updateQue(string);

                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button2.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                } else {
                    wrong++;
                    updateQue(string);
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button3.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                } else {
                    wrong++;
                    updateQue(string);
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button4.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                } else {
                    wrong++;
                    updateQue(string);
                }
            }
        });
    }

    private void updateScore(int score) {
        String sco = "Score : ";
        textViewScore.setText(sco + score);
    }

    private void reverseTimer(final TextView textView) {
        new CountDownTimer(1200 * 1000 + 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                textView.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));

            }

            @Override
            public void onFinish() {
                textView.setText(TU);
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra(T, String.valueOf(total));
                intent.putExtra(S, String.valueOf(score));
                intent.putExtra(C, String.valueOf(correct));
                intent.putExtra(W, String.valueOf(wrong));
                startActivity(intent);

            }
        }.start();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String PQ = "Play Or Quit";
        builder.setTitle(PQ);
        String alertq = "Do you want to quit the Quiz..? ";
        builder.setMessage(alertq);
        String s = "Yes";
        builder.setPositiveButton(s, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), JavaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        String n = "No";
        builder.setNegativeButton(n, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

}
