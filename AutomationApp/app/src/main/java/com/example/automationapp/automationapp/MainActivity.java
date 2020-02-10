package com.example.automationapp.automationapp;

import android.Manifest;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.example.automationapp.automationapp.MenuActivity.RecommendActivity;
import com.example.automationapp.automationapp.Trigger.TriggerActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import DTO.Action;
import DTO.Function;
import DTO.Trigger;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {

    private SharedPreferences mSharedPreferences;
    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MainActivity";
    private static final String FUNCTION_TAG = "FunctionActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 2;
    private static final int REQUEST_SMS_RECEIVED_PERMISSION = 3;
    private static final int REQUEST_CODE_READ_CONTACTS = 4;

    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private String mEmail;
    private GoogleApiClient mGoogleApiClient;
    private GestureDetector mGestureDetector;
    private boolean isBound = false;

    protected static FirebaseAuth mFirebaseAuth;
    protected static int CURRENT_ACTIVITY=R.id.nav_function;
    protected static DatabaseReference mDatabase;
    protected static TriggerService triggerService;
    protected static ArrayList<Function> functions;
    protected static ArrayList<Trigger> triggers;
    protected static ArrayList<Action> actions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(getSupportActionBar()==null) {
            setSupportActionBar(toolbar);
        }else{
            toolbar = (Toolbar) getSupportActionBar().getCustomView();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(getClass().getSimpleName().equalsIgnoreCase(FUNCTION_TAG)) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Intent intent = new Intent(MainActivity.this, TriggerActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            fab.setVisibility(View.GONE);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null || (mFirebaseUser!=null && !mFirebaseUser.isEmailVerified())) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mEmail=mFirebaseUser.getEmail();
            TextView username=(TextView) header.findViewById(R.id.username);
            TextView email=(TextView) header.findViewById(R.id.email);
            username.setText(mUsername);
            email.setText(mEmail);
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                NavigationView nav=(NavigationView) findViewById(R.id.nav_view);
                View headerLayout = nav.getHeaderView(0);
                final CircleImageView userImage= (CircleImageView)headerLayout.findViewById(R.id.userImageView);
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {

                        try {
                            Bitmap userIProfileImage=Glide.with(MainActivity.this)
                                    .load(mPhotoUrl)
                                    .asBitmap()
                                    .into(50,50)
                                    .get();
                            return userIProfileImage;
//                            userImage.setImageBitmap(userIProfileImage);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        userImage.setImageBitmap(bitmap);
                    }


                }.execute();
                Intent intent=new Intent(this,TriggerService.class);
                startService(intent);
            }
        }
        approvedPermission();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(this, customGestureDetector);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        mGestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if ( e2.getX() - e1.getX() > 400 && Math.abs(e1.getY() - e2.getY())<100 ) {
                if(CURRENT_ACTIVITY!=R.id.nav_function){
                    switchIntent(CURRENT_ACTIVITY-1);
                    overridePendingTransition( android.R.anim.slide_in_left,  android.R.anim.slide_out_right);
                }
                System.out.println("swipe left "+CURRENT_ACTIVITY);
            }
            // Swipe right (previous)
            if (e1.getX() - e2.getX() > 400 && Math.abs(e1.getY() - e2.getY())<100 ) {
                if(CURRENT_ACTIVITY!=R.id.nav_recommend && Math.abs(e1.getY() - e2.getY())<100 ){
                    switchIntent(CURRENT_ACTIVITY+1);
                    overridePendingTransition( R.anim.slide_in_right,  R.anim.slide_out_left);
                }
                System.out.println("swipe right "+CURRENT_ACTIVITY);
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void switchIntent(int id) {
        Intent intent;
        if (id>20){
            if (id == R.id.nav_function) {
                intent = new Intent(this, FunctionActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_recommend) {
                intent = new Intent(this, RecommendActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }

    private ServiceConnection myConnection = new ServiceConnection()
    {

        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            TriggerService.TriggerServiceBinder binder = (TriggerService.TriggerServiceBinder) service;
            triggerService = binder.getService();
            isBound = true;
            triggerService.startService();
        }

        public void onServiceDisconnected(ComponentName arg0) {
            triggerService = null;
            isBound = false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_function && CURRENT_ACTIVITY!=R.id.nav_function) {
            intent=new Intent(this,FunctionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_recommend && CURRENT_ACTIVITY!=R.id.nav_recommend) {
            intent=new Intent(this, RecommendActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_export && CURRENT_ACTIVITY!=R.id.nav_export) {
            intent=new Intent(this,FunctionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_import && CURRENT_ACTIVITY!=R.id.nav_import) {
            intent=new Intent(this,FunctionActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_setting && CURRENT_ACTIVITY!=R.id.nav_setting) {
            intent=new Intent(this,FunctionActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mUsername = ANONYMOUS;
            startActivity(new Intent(this, SignInActivity.class));
        }
        finish();
//        overridePendingTransition( android.R.anim.slide_in_left,  android.R.anim.slide_out_right);
        overridePendingTransition(0,0);
        Snackbar.make(getCurrentFocus(), item.getTitle(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            String name=getClass().getSimpleName();
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
            if(isBound==false && name.equalsIgnoreCase(FUNCTION_TAG)) {
                Intent intent = new Intent(this, TriggerService.class);
                bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
            }
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        String name=getClass().getSimpleName();
        if(isBound && name.equalsIgnoreCase(FUNCTION_TAG) ) {
            unbindService(myConnection);
            isBound = false;
            triggerService=null;
        }
    }

    public void approvedPermission(){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName unLockDetailReceiver = new ComponentName(this, UnLockDetailReceiver.class);
        boolean isActive = devicePolicyManager.isAdminActive(unLockDetailReceiver);

        if(!isActive) {
            Intent intent1 = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent1.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, unLockDetailReceiver);
            intent1.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "We will need this to control over some system setting");
            startActivity(intent1);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this ,new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this ,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this ,new String[]{android.Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_RECEIVED_PERMISSION);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_CONTACTS }, REQUEST_CODE_READ_CONTACTS);
        }
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }


}
