package com.example.user.e_quiz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class JavaActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);


        AdView adView = findViewById(R.id.ad);
        HashMap<String, String> hash_file_maps = new HashMap<>();
        SliderLayout sliderLayout = findViewById(R.id.sliderLayout);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        String[] s1 = getResources().getStringArray(R.array.Basictopics);
        String[] s2 = getResources().getStringArray(R.array.AdvancedLevel);
        String basic=getIntent().getStringExtra("key");
        String advanced=getIntent().getStringExtra("key");
        RecyclerView recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(basic.equals("basic")){
            Toast.makeText(this, basic, Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(new RecyclerAdapter2(this, s1));
        }
        if (advanced.equals("advanced")){
            Toast.makeText(this, advanced, Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(new RecyclerAdapter2(this, s2));
        }




        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(JavaActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        hash_file_maps.put(getString(R.string.eng), getString(R.string.si1));


        for (String name : hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(JavaActivity.this);
            textSliderView
                    .description(name)
                    .image(hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_person_black), user.getEmail()));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp), getResources().getString(R.string.dashboard)));
        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.ic_power_settings_new_black_24dp), getResources().getString(R.string.logout)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        String str = "Hello  ";
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, str + user.getEmail(), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                startActivity(new Intent(JavaActivity.this, MyScoreActivity.class));
                break;
            case 3:
                auth.signOut();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
