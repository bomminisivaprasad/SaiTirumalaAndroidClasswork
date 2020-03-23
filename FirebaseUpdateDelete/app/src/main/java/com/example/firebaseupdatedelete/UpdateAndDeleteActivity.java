package com.example.firebaseupdatedelete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAndDeleteActivity extends AppCompatActivity {

    EditText rollno;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_and_delete);
        rollno = findViewById(R.id.rno);
        reference = FirebaseDatabase.getInstance()
                .getReference("Student");
    }

    public void check(View view) {
        final String number = rollno.getText().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for(DataSnapshot snapshot:dataSnapshot.getChildren()){
               Student s = snapshot.getValue(Student.class);
               if(number.equals(s.getRollno())){
                   Log.d("Details",s.getName()+" "
                           +s.getMobile()+" "+s.getEmail());
                   s.setMobile("6301067787");
                   s.setEmail("vali@gmail.com");
                   Log.d("Key",snapshot.getKey());
                   reference.child(snapshot.getKey()).setValue(s);
                   break;
               }
           }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void delete(View view) {

        final String number = rollno.getText().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Student s = snapshot.getValue(Student.class);
                    if(number.equals(s.getRollno())){
                        Log.d("Details",s.getName()+" "
                                +s.getMobile()+" "+s.getEmail());
                        Log.d("Key",snapshot.getKey());
                        reference.child(snapshot.getKey()).removeValue();
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
