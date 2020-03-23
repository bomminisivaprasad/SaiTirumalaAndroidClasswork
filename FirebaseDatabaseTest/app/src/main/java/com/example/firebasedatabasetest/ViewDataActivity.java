package com.example.firebasedatabasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity {

    Spinner sp_branch;
    TextView tv;
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        sp_branch = findViewById(R.id.sp);
        //tv = findViewById(R.id.data);
        rv = findViewById(R.id.recycler);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public void getData(View view) {
        String selectedBranch = sp_branch.getSelectedItem().toString();
        final List<Student> list = new ArrayList<>();
        reference.child("Students").child(selectedBranch)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Student s = snapshot.getValue(Student.class);
                            list.add(s);
                        }

                        rv.setLayoutManager(new LinearLayoutManager(ViewDataActivity.this));
                        StudentAdapter adapter = new StudentAdapter(getApplicationContext(),list);
                        rv.setAdapter(adapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
