package com.example.googlesigninintigration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NVDRActivity extends AppCompatActivity {

    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle at;

    FragmentManager fg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvdr);
        fg = getSupportFragmentManager();
        dl = findViewById(R.id.dlayout);
        nv = findViewById(R.id.navigationV);
        at = new ActionBarDrawerToggle(this,dl,
                R.string.open,
                R.string.close);

        dl.addDrawerListener(at);
        at.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fg.beginTransaction()
                .replace(R.id.main_container,new HomeFragment())
                .commit();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        Fragment fragment = fg.findFragmentById(R.id.main_container);
                        fg.beginTransaction().remove(fragment).commit();
                        fg.beginTransaction()
                                .replace(R.id.main_container,new HomeFragment())
                                .commit();
                        dl.closeDrawers();
                        break;

                    case R.id.about:
                        Toast.makeText(NVDRActivity.this,
                                "About", Toast.LENGTH_SHORT).show();
                        Fragment fragment1 = fg.findFragmentById(R.id.main_container);
                        fg.beginTransaction().remove(fragment1).commit();
                        fg.beginTransaction()
                                .replace(R.id.main_container,new AboutFragment())
                                .commit();
                        dl.closeDrawers();
                        break;
                    case R.id.contact:
                        Fragment fragment2 = fg.findFragmentById(R.id.main_container);
                        fg.beginTransaction().remove(fragment2).commit();
                        Toast.makeText(NVDRActivity.this,
                                "Contact", Toast.LENGTH_SHORT).show();
                        fg.beginTransaction()
                                .replace(R.id.main_container,new ContactFragment())
                                .commit();
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(at.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
