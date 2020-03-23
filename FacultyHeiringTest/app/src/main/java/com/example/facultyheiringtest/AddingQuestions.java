package com.example.facultyheiringtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingQuestions extends AppCompatActivity {

    EditText et_q,et_1,et_2,et_3,et_4,et_a;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_questions);
        reference = FirebaseDatabase.getInstance().getReference("Questions");
        et_q = findViewById(R.id.question);
        et_1 = findViewById(R.id.option1);
        et_2 = findViewById(R.id.option2);
        et_3 = findViewById(R.id.option3);
        et_4 = findViewById(R.id.option4);
        et_a = findViewById(R.id.answer);

    }

    public void save(View view) {
        String q = et_q.getText().toString();
        String opt1 = et_1.getText().toString();
        String opt2 = et_2.getText().toString();
        String opt3 = et_3.getText().toString();
        String opt4 = et_4.getText().toString();
        String a = et_a.getText().toString();
        MyQuestion question = new MyQuestion(q,opt1,opt2,opt3,opt4,a);
        reference.push().setValue(question).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddingQuestions.this,
                        "Svaed Successfully....", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
