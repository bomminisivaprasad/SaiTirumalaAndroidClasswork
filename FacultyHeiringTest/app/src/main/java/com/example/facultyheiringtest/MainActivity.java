package com.example.facultyheiringtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.CallbackResult {

    RecyclerView recyclerView;
    DatabaseReference reference,dr;
    List<MyQuestion> list;
    int count = 0;

    List<CorrectQuestions> currectQuestionsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currectQuestionsList = new ArrayList<>();
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        reference = FirebaseDatabase.getInstance().getReference("Questions");
        dr = FirebaseDatabase.getInstance().getReference("CurrectQuestions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                        MyQuestion q = snapshot.getValue(MyQuestion.class);
                        list.add(q);
                    CorrectQuestions questions=new CorrectQuestions();
                    questions.setCuurectQuestion(q.getQuestion());
                    questions.setSelectedAnswer("");
                    currectQuestionsList.add(questions);

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(new MyAdapter(MainActivity.this,list,MainActivity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void addQuestions(View view) {
        startActivity(new Intent(this,AddingQuestions.class));
    }

    public void submitPaper(View view) {
        for (int i = 0; i < currectQuestionsList.size(); i++) {

            dr.push().setValue(currectQuestionsList.get(i)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this,
                            "Added Success Fully", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void getresult(int position, String question, String selectedoption, String exactAnswer) {
        CorrectQuestions correctQuestions = currectQuestionsList.get(position);
        if(exactAnswer.equals(selectedoption)){
            //Toast.makeText(this, "I am Correct", Toast.LENGTH_SHORT).show();
            //correctQuestions.setCuurectQuestion(question);
            correctQuestions.setSelectedAnswer(selectedoption);
            currectQuestionsList.set(position,correctQuestions);
        }

        Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();

    }

    public void myScore(View view) {
        final List<CorrectQuestions> cq = new ArrayList<>();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    CorrectQuestions q = snapshot.getValue(CorrectQuestions.class);
                    if(q.getSelectedAnswer().equals("")){
                        Toast.makeText(MainActivity.this,
                                "0", Toast.LENGTH_SHORT).show();
                    }else{
                        cq.add(q);
                    }
                }

                Toast.makeText(MainActivity.this, ""+cq.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
