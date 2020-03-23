package com.example.user.e_quiz;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.e_quiz.MyModel.MyScore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyScoreActivity extends AppCompatActivity {


    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    TextView textViewCLang;
    private MyScore myScore;
    public String Clanguage;
    private String languageFundamentals;
    private String OperatorsAssignments;
    private String DeclarationsAccessControl;
    private String Assertions;
    private String Exceptions;
    private String InnerClasses;
    private String GarbageCollections;
    private String JavalangClass;
    private String FlowControl;
    private String ObjectsandCollections;
    private String Threads;
    private String OverallQuestions;
    private TextView textViewLF;
    private TextView textViewOA;
    private TextView textViewDA;
    private TextView textViewA;
    private TextView textViewE;
    private TextView textViewIC;
    private TextView textViewGC;
    private TextView textViewJL;
    private TextView textViewFC;
    private TextView textViewOC;
    private TextView textViewT;
    private TextView textViewOQ;
    private ProgressBar progressBarScore;

    private final String lf = "languagef";
    private final String op = "operators";
    private final String dc = "declarative";
    private final String ass = "assertions";
    private final String ex = "exceptions";
    private final String ic = "innerclass";
    private final String garbage = "gc";
    private final String jl = "javalang";
    private final String flc = "flowcontrol";
    private final String objc = "oc";
    private final String thr = "threads";
    private final String ovq = "overallque";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String name = "Mouni Rokstar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);

        progressBarScore = findViewById(R.id.myScoreProgress);
        textViewLF = findViewById(R.id.txtLF);
        textViewOA = findViewById(R.id.txtOA);
        textViewDA = findViewById(R.id.txtDA);
        textViewA = findViewById(R.id.txtA);
        textViewE = findViewById(R.id.txtE);
        textViewIC = findViewById(R.id.txtIC);
        textViewGC = findViewById(R.id.txtGC);
        textViewJL = findViewById(R.id.txtJL);
        textViewFC = findViewById(R.id.txtFC);
        textViewOC = findViewById(R.id.txtOC);
        textViewT = findViewById(R.id.txtT);
        textViewOQ = findViewById(R.id.txtOQ);

        String emailIDd = JavaActivity.user.getEmail();
        String ms = "MyScore";
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(ms);
        String ml = "mail";
        Query query = databaseReference.orderByChild(ml).equalTo(emailIDd);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    myScore = dataSnapshot1.getValue(MyScore.class);

                    progressBarScore.setVisibility(View.GONE);
                    languageFundamentals = myScore.getLanguageFundamentals();
                    OperatorsAssignments = myScore.getOperatorsAssignments();
                    DeclarationsAccessControl = myScore.getDeclarationsAccessControl();
                    Assertions = myScore.getAssertions();
                    Exceptions = myScore.getExceptions();
                    InnerClasses = myScore.getInnerClasses();
                    GarbageCollections = myScore.getGarbageCollections();
                    JavalangClass = myScore.getJavalangClass();
                    FlowControl = myScore.getFlowControl();
                    ObjectsandCollections = myScore.getObjectsandCollections();
                    Threads = myScore.getThreads();
                    OverallQuestions = myScore.getOverallQuestions();


                    textViewLF.setText(languageFundamentals);
                    textViewOA.setText(OperatorsAssignments);
                    textViewDA.setText(DeclarationsAccessControl);
                    textViewA.setText(Assertions);
                    textViewE.setText(Exceptions);
                    textViewIC.setText(InnerClasses);
                    textViewGC.setText(GarbageCollections);
                    textViewJL.setText(JavalangClass);
                    textViewFC.setText(FlowControl);
                    textViewOC.setText(ObjectsandCollections);
                    textViewT.setText(Threads);
                    textViewOQ.setText(OverallQuestions);


                    sharedPreferences = getSharedPreferences(name, MODE_PRIVATE);
                    editor = sharedPreferences.edit();

                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(languageFundamentals);
                    StringBuffer stringBuffer1 = new StringBuffer();
                    stringBuffer1.append(OperatorsAssignments);
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(DeclarationsAccessControl);
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append(Assertions);
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append(Exceptions);
                    StringBuffer stringBuffer5 = new StringBuffer();
                    stringBuffer5.append(InnerClasses);
                    StringBuffer stringBuffer6 = new StringBuffer();
                    stringBuffer6.append(GarbageCollections);
                    StringBuffer stringBuffer7 = new StringBuffer();
                    stringBuffer7.append(JavalangClass);
                    StringBuffer stringBuffer8 = new StringBuffer();
                    stringBuffer8.append(FlowControl);
                    StringBuffer stringBuffer9 = new StringBuffer();
                    stringBuffer9.append(ObjectsandCollections);
                    StringBuffer stringBuffer10 = new StringBuffer();
                    stringBuffer10.append(Threads);
                    StringBuffer stringBuffer11 = new StringBuffer();
                    stringBuffer11.append(OverallQuestions);

                    editor.putString(lf, String.valueOf(stringBuffer));
                    editor.putString(op, String.valueOf(stringBuffer1));
                    editor.putString(dc, String.valueOf(stringBuffer2));
                    editor.putString(ass, String.valueOf(stringBuffer3));
                    editor.putString(ex, String.valueOf(stringBuffer4));
                    editor.putString(ic, String.valueOf(stringBuffer5));
                    editor.putString(garbage, String.valueOf(stringBuffer6));
                    editor.putString(jl, String.valueOf(stringBuffer7));
                    editor.putString(flc, String.valueOf(stringBuffer8));
                    editor.putString(objc, String.valueOf(stringBuffer9));
                    editor.putString(thr, String.valueOf(stringBuffer10));
                    editor.putString(ovq, String.valueOf(stringBuffer11));
                    editor.apply();

                    Intent i = new Intent(MyScoreActivity.this, SampleWidget.class);
                    i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    int[] ids = AppWidgetManager.getInstance(MyScoreActivity.this).
                            getAppWidgetIds(new ComponentName(getApplicationContext(), SampleWidget.class));
                    i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                    sendBroadcast(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

