package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceReportActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    FirebaseDatabase db;
    DatabaseReference dr;
    Spinner sbr, syear, ssection;
    EditText et_from,et_to;
    TextView tv_workingdays, tv_presents, tv_absents;
    int c_f_year,c_f_month,c_f_day;
    int c_t_year,c_t_month,c_t_day;
    String fromDate,toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        editText = findViewById(R.id.editText);
        et_from = findViewById(R.id.fromdate);
        et_to = findViewById(R.id.todate);
        et_from.setOnClickListener(this);
        et_to.setOnClickListener(this);
        sbr = findViewById(R.id.spinner);
        syear = findViewById(R.id.spinner2);
        ssection = findViewById(R.id.spinner3);
        tv_workingdays = findViewById(R.id.workingdays);
        tv_presents = findViewById(R.id.presents);
        tv_absents = findViewById(R.id.absents);
        db = FirebaseDatabase.getInstance();
        dr = db.getReference("Attendance");
    }

    public void getReport(View view) {
        final String rollno = editText.getText().toString();
        String fromdate = et_from.getText().toString();
        String todate = et_to.getText().toString();
        final String branch = sbr.getSelectedItem().toString();
        final String year = syear.getSelectedItem().toString();
        final String section = ssection.getSelectedItem().toString();
        final List<Attendance> list = new ArrayList<>();
        dr.child(branch).child(year)
                .child(section)
                .child(fromdate)
                .child(todate)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            tv_workingdays.setText("Number of Working days : " + dataSnapshot.getChildrenCount());
                            for (int i = 1; i <= 31; i++) {
                                DataSnapshot shot = dataSnapshot.child(String.valueOf(i));
                                if (shot != null) {
                                    for (DataSnapshot mysnapShot : shot.getChildren()) {
                                        Attendance attendance = mysnapShot.getValue(Attendance.class);
                                        list.add(attendance);
                                    }
                                }
                                int c = 0;
                                for (int j = 0; j < list.size(); j++) {
                                    if ((list.get(j).isAttendance() == true) &&
                                            (list.get(j).getRollnumber().equals(rollno))) {
                                        c++;
                                    }
                                }

                                tv_presents.setText(rollno + " Presents : " + c);
                                tv_absents.setText(rollno + " Absents : " + (dataSnapshot.getChildrenCount() - c));


                            }
                        /*for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Attendance attendance = snapshot.getValue(Attendance.class);
                            list.add(attendance);
                        }*/
                            Toast.makeText(AttendanceReportActivity.this,
                                    "" + list.size(), Toast.LENGTH_SHORT).show();
                          /*  PieChart pieChart = findViewById(R.id.piechart);
                            ArrayList NoOfEmp = new ArrayList();

                            NoOfEmp.add(new Entry(945f, 0));
                            NoOfEmp.add(new Entry(1040f, 1));
                            NoOfEmp.add(new Entry(1133f, 2));
                            NoOfEmp.add(new Entry(1240f, 3));
                            NoOfEmp.add(new Entry(1369f, 4));
                            NoOfEmp.add(new Entry(1487f, 5));
                            NoOfEmp.add(new Entry(1501f, 6));
                            NoOfEmp.add(new Entry(1645f, 7));
                            NoOfEmp.add(new Entry(1578f, 8));
                            NoOfEmp.add(new Entry(1695f, 9));
                            PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");

                            ArrayList year = new ArrayList();

                            year.add("2008");
                            year.add("2009");
                            year.add("2010");
                            year.add("2011");
                            year.add("2012");
                            year.add("2013");
                            year.add("2014");
                            year.add("2015");
                            year.add("2016");
                            year.add("2017");
                            PieData data = new PieData(year, dataSet);
                            pieChart.setData(data);
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieChart.animateXY(5000, 5000);
*/


                        /*int count = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if ((list.get(i).isAttendance() == true) &&
                                    (list.get(i).getRollnumber() == rollno)) {
                                count++;
                            }
                        }
                        Toast.makeText(AttendanceReportActivity.this,
                                "" + count + list.size(), Toast.LENGTH_SHORT).show();
*/
                        }else{
                            Toast.makeText(AttendanceReportActivity.this,
                                    "Data is Not Available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fromdate:
            fromDate();
            break;
            case R.id.todate:
                toDate();
                break;

        }

    }

    private void fromDate() {
        Calendar c = Calendar.getInstance();
        c_f_year = c.get(Calendar.YEAR);
        c_f_month = c.get(Calendar.MONTH);
        c_f_day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog fdialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fromDate = dayOfMonth+"-"+month+"-"+year;
                et_from.setText(fromDate);

            }
        },c_f_year,c_f_month,c_f_day);
        fdialog.show();
    }
    private void toDate() {
        Calendar c = Calendar.getInstance();
        c_t_year = c.get(Calendar.YEAR);
        c_t_month = c.get(Calendar.MONTH);
        c_t_day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog tdialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                toDate = dayOfMonth+"-"+month+"-"+year;
                et_to.setText(toDate);

            }
        },c_t_year,c_t_month,c_t_day);
        tdialog.show();
    }
}
