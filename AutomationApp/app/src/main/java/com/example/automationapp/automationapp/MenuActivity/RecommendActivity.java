package com.example.automationapp.automationapp.MenuActivity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;

import com.example.automationapp.automationapp.MainActivity;
import com.example.automationapp.automationapp.R;

/**
 * Created by laiwf on 07/03/2017.
 */

public class RecommendActivity extends MainActivity {

    private final int ACTIVITY_ID= R.id.nav_recommend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.nav_recommend);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(ACTIVITY_ID);
        CURRENT_ACTIVITY=ACTIVITY_ID;

    }
}
