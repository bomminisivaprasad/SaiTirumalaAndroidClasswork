package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_rollno, et_name, et_mobileno;
    Spinner sp_branch, sp_year, sp_section;

    FirebaseDatabase fdb;
    DatabaseReference dr;
    List<Student> studentslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.name);
        et_rollno = findViewById(R.id.rollno);
        et_mobileno = findViewById(R.id.mobile);

        sp_branch = findViewById(R.id.branch);
        sp_year = findViewById(R.id.year);
        sp_section = findViewById(R.id.section);

        fdb = FirebaseDatabase.getInstance();
        dr = fdb.getReference("Students");
    }

    public void save(View view) {
        final String name = et_name.getText().toString();
        final String rollno = et_rollno.getText().toString();
        final String mobileno = et_mobileno.getText().toString();
        final String branch = sp_branch.getSelectedItem().toString();
        final String year = sp_year.getSelectedItem().toString();
        final String section = sp_section.getSelectedItem().toString();

        dr.child(branch).child(year).child(section).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentslist = new ArrayList<Student>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    studentslist.add(postSnapshot.getValue(Student.class));
                }
                if (studentslist.size() != 0) {
                    int count = 0;
                    for (int i = 0; i < studentslist.size(); i++) {
                        if (rollno.equals(studentslist.get(i).getRollno())) {
                            Toast.makeText(MainActivity.this,
                                    "Already Exist", Toast.LENGTH_SHORT).show();
                        } else {
                            count++;
                            if (count == studentslist.size()) {
                                Student s = new Student(rollno, name, mobileno, branch, year, section);
                                dr.child(branch).child(year).child(section).push().setValue(s);
                                Toast.makeText(getApplicationContext(),
                                        "Details Saved SuccessFully", Toast.LENGTH_SHORT).show();
                                et_rollno.setText("");
                                et_mobileno.setText("");
                                et_name.setText("");

                            }
                        }
                    }
                } else {
                    Student s = new Student(rollno, name, mobileno, branch, year, section);
                    dr.child(branch).child(year).child(section).push().setValue(s);
                    Toast.makeText(getApplicationContext(),
                            "Details Saved SuccessFully", Toast.LENGTH_SHORT).show();
                    et_rollno.setText("");
                    et_mobileno.setText("");
                    et_name.setText("");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void gotoAttendance(View view) {
        Intent i = new Intent(MainActivity.this, AttendanceActivity.class);
        startActivity(i);
    }

    public void getReportPage(View view) {
        Intent i = new Intent(MainActivity.this, AttendanceReportActivity.class);
        startActivity(i);
    }
}
