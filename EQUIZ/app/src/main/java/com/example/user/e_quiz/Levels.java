package com.example.user.e_quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Levels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
    }

    public void basic(View view) {
        switch (view.getId()){
            case R.id.basic:
                Intent i=new Intent(Levels.this,JavaActivity.class);
                i.putExtra("key","basic");
                startActivity(i);
                break;
            case R.id.advanced:
                Intent i1=new Intent(Levels.this,JavaActivity.class);
                i1.putExtra("key","advanced");
                startActivity(i1);
                break;

        }
    }
}
