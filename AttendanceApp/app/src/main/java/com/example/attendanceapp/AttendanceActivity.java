package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity implements MyAdapter.CallbackAttendance {
private List<Attendance> attendanceList;
    RecyclerView rv;
    Spinner b,y,s;
    FirebaseDatabase fdb;
    DatabaseReference dr;
    List<Student> studentslist;
    TextView tv;

    DatabaseReference attendance_dr;

    int c_year,c_month,c_date;
    private Student student;
    String branch,year,section;
    int i;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        rv = findViewById(R.id.recycler);
        b = findViewById(R.id.a_branch);
        y = findViewById(R.id.a_year);
        s = findViewById(R.id.a_section);
        tv = findViewById(R.id.date_tv);

        fdb = FirebaseDatabase.getInstance();
        dr = fdb.getReference("Students");
        attendance_dr = fdb.getReference("Attendance");
        attendanceList=new ArrayList<>();
    }

    public void get(View view) {

         branch = b.getSelectedItem().toString();
        String year = y.getSelectedItem().toString();
        String section = s.getSelectedItem().toString();

        dr.child(branch).child(year).child(section).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentslist = new ArrayList<Student>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    student=postSnapshot.getValue(Student.class);
                    studentslist.add(student);
                    Attendance attendance=new Attendance();
                    attendance.setRollnumber(student.getRollno());
                    attendance.setName(student.getName());
                    attendance.setAttendance(false);
                    attendanceList.add(attendance);
                }

                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new MyAdapter(getApplicationContext(),studentslist,AttendanceActivity.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void selectDate(View view) {
        Calendar c = Calendar.getInstance();
        c_year = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_date = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //view.setMinDate(System.currentTimeMillis() - 1000);
                String mydate = dayOfMonth+"-"+(month+1)+"-"+year;
                tv.setText(mydate);
            }
        },c_year,c_month,c_date);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        dialog.show();

    }

    public void submit(View view) {

        String br = b.getSelectedItem().toString();
        String yr = y.getSelectedItem().toString();
        String sn = s.getSelectedItem().toString();
        String date = tv.getText().toString();
        if(date.isEmpty()){
            Toast.makeText(this,
                    "Please select the date", Toast.LENGTH_SHORT).show();
        }else{
        String datesplit[] = date.split("-");
        //String d = datesplit[2]+datesplit[1]+datesplit[0];
        String month="";
        switch (datesplit[1]){
            case "1":
                month = "January";
                break;
            case "2":
                month = "February";
                break;
            case "3":
                month = "March";
                break;
            case "4":
                month = "April";
                break;
            case "5":
                month = "May";
                break;
            case "6":
                month = "June";
                break;
            case "7":
                month = "July";
                break;
            case "8":
                month = "August";
                break;
            case "9":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }
            for (i = 0; i < attendanceList.size(); i++) {
                attendance_dr.child(br).child(yr)
                        .child(sn)
                        .child(datesplit[2])
                        .child(month)
                        .child(datesplit[0])
                        .push().setValue(attendanceList.get(i));
                Toast.makeText(AttendanceActivity.this,
                        "Attendance posted successfullyy..",
                        Toast.LENGTH_SHORT).show();
           /* Attendance attendance = attendanceList.get(i);
            attendance.setAttendance(false);*/
                //rv.setAdapter(adapter);
                tv.setText("");
                startActivity(new Intent(AttendanceActivity.this,AttendanceActivity.class));
                finish();
            }
        }

    }

    @Override
    public void getAttenance(int position, boolean attend) {
        Attendance attendance=attendanceList.get(position);
        attendance.setAttendance(attend);
        attendanceList.set(position,attendance);
    }
}
