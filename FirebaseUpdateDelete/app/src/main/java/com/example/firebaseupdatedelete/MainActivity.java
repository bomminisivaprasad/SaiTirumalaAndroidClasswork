package com.example.firebaseupdatedelete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_rollno, et_name, et_mobile, et_email;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_rollno = findViewById(R.id.rollno);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        et_email = findViewById(R.id.email);
        reference = FirebaseDatabase.getInstance().getReference("Student");
    }

    public void save(View view) {
        final String name = et_name.getText().toString();
        final String rollno = et_rollno.getText().toString();
        final String mobile = et_mobile.getText().toString();
        final String email = et_email.getText().toString();

        final List<Student> list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Student s = snapshot.getValue(Student.class);
                    list.add(s);
                }

                if(list.size()!=0){
                    int count = 0;
                for(int i = 0;i<list.size();i++) {
                    if (rollno.equals(list.get(i).getRollno())) {
                        Toast.makeText(MainActivity.this,
                                "Already Exist",
                                Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        count++;
                        if(count == list.size()){
                            Student s = new Student(rollno, name, mobile, email);
                            reference.push().setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this,
                                            "Details Saved Successfully..", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                }else{

                    Student s = new Student(rollno, name, mobile, email);
                    reference.push().setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this,
                                    "Details Saved Successfully..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    public void openupdateandDeletepage(View view) {
        startActivity(new Intent(this,
                UpdateAndDeleteActivity.class));

    }
}
