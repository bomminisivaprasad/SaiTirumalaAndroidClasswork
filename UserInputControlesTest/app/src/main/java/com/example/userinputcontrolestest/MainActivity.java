package com.example.userinputcontrolestest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et_name,et_mobile,et_email;
    RadioButton male_r,female_r;
    Spinner sp_branch;
    CheckBox ch1,ch2,ch3,ch4;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        et_email = findViewById(R.id.email);

        male_r = findViewById(R.id.male);
        female_r = findViewById(R.id.female);

        sp_branch = findViewById(R.id.sp);

        ch1 = findViewById(R.id.java);
        ch2 = findViewById(R.id.android);
        ch3 = findViewById(R.id.python);
        ch4 = findViewById(R.id.ml);

        tv_result = findViewById(R.id.result);
    }

    public void save(View view) {
        String name,mobileno,email;
        String gender = null,branch,techSkills;
        name = et_name.getText().toString();
        mobileno = et_mobile.getText().toString();
        email = et_email.getText().toString();
        if(male_r.isChecked()){
            gender = male_r.getText().toString();
        }
        if(female_r.isChecked()){
            gender = female_r.getText().toString();
        }
        branch = sp_branch.getSelectedItem().toString();
        StringBuilder builder = new StringBuilder();
        if(ch1.isChecked()){
            builder.append(ch1.getText().toString());
        }
        if(ch2.isChecked()){
            builder.append(ch2.getText().toString()+",");
        }
        if(ch3.isChecked()){
            builder.append(ch3.getText().toString());
        }
        if(ch4.isChecked()){
            builder.append(ch4.getText().toString());
        }
        techSkills = builder.toString();
        
        /*tv_result.setText(name+"\n"+
                mobileno+"\n"+
                email+"\n"+
                gender+"\n"+
                branch+"\n"+
                techSkills);*/

        Intent i = new Intent(this,SecondActivity.class);
        i.putExtra("n",name);
        i.putExtra("m",mobileno);
        i.putExtra("e",email);
        i.putExtra("gender",gender);
        i.putExtra("branch",branch);
        i.putExtra("techskills",techSkills);
        startActivity(i);

    }
}
