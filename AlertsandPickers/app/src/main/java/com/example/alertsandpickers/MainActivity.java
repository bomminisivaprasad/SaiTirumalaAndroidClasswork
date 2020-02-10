package com.example.alertsandpickers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int c_day,c_month,c_year,c_hour,c_minuite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openAlertDialog(View view) {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    public void openDialog(View view) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.mylayout);
        final EditText et_u = d.findViewById(R.id.username);
        final EditText et_p = d.findViewById(R.id.pass);
        Button b = d.findViewById(R.id.loginButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = et_u.getText().toString();
                String p = et_p.getText().toString();
                if(u.equals("Admin") && p.equals("123")){
                    Toast.makeText(MainActivity.this,
                            "Success",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,
                            "Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }

    public void openTimePicker(View view) {
        Calendar c = Calendar.getInstance();
        c_hour = c.get(Calendar.HOUR_OF_DAY);
        c_minuite = c.get(Calendar.MINUTE);
        TimePickerDialog tdialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String myTime = hourOfDay+":"+minute;
                Toast.makeText(MainActivity.this,
                        ""+myTime, Toast.LENGTH_SHORT).show();

            }
        },c_hour,c_minuite,false);
        tdialog.show();


    }

    public void openDatePicker(View view) {
        Calendar c = Calendar.getInstance();
        c_year = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String myDate = dayOfMonth+"-"+(month+1)+"-"+year;
                Toast.makeText(MainActivity.this,
                        ""+myDate, Toast.LENGTH_SHORT).show();

            }
        },c_year,c_month,c_day);
        dDate.show();
    }
}
