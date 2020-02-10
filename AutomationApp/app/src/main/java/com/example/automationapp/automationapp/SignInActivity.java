package com.example.automationapp.automationapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.automationapp.automationapp.Function.FunctionActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DTO.Function;


/**
 * Created by laiwf on 04/02/2017.
 */

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String Google_SignIn_TAG = "SignInActivity";
    private static final String Email_SignIn_TAG = "EmailSignIn";
    private static final String Email_SignUp_TAG = "EmailSignUp";
    private static final String Email_Reset_TAG = "EmailReset";
    private static final int RC_SIGN_IN = 9001;
    private String Current_TAG=Google_SignIn_TAG;

    private FirebaseAuth mFirebaseAuth;
    private Button mSignInButton;

    private EditText signInEmailField;
    private EditText signInPasswordField;
    private ProgressBar signInProgressBar;
    private ProgressBar signUpProgressBar;
    private ProgressBar resetProgressBar;
    private EditText signUpEmailField;
    private EditText signUpPasswordField;
    private EditText signUpRetypePasswordField;
    private EditText resetEmailField;
    private AppCompatButton emailSignInButton;
    private AppCompatButton emailSignUpButton;
    private AppCompatButton emailResetButton;
    private GoogleApiClient mGoogleApiClient;
    private ViewFlipper viewFlipper;
    private GestureDetector mGestureDetector;

    // Firebase instance variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

        signInProgressBar = (ProgressBar) findViewById(R.id.signin_progressBar);
        signUpProgressBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        resetProgressBar = (ProgressBar) findViewById(R.id.reset_progressBar);
        signInProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorWarnRed), PorterDuff.Mode.MULTIPLY);
        signUpProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorWarnRed), PorterDuff.Mode.MULTIPLY);
        resetProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorWarnRed), PorterDuff.Mode.MULTIPLY);


        emailSignInButton=(AppCompatButton)findViewById(R.id.email_sign_in_button);
        emailSignUpButton=(AppCompatButton)findViewById(R.id.email_register_button);
        emailResetButton=(AppCompatButton)findViewById(R.id.email_reset_button);

        final AppCompatButton goSignIn =(AppCompatButton)findViewById(R.id.sign_in_page_button);
        final AppCompatButton goSignUp =(AppCompatButton)findViewById(R.id.sign_up_page_button);


        TextView registerLink=(TextView)findViewById(R.id.register_link);
        TextView sign_in_Link=(TextView)findViewById(R.id.sign_in_link);
        TextView reset_registerLink=(TextView)findViewById(R.id.reset_sign_up_link);
        TextView reset_sign_in_Link=(TextView)findViewById(R.id.reset_sign_in_link);
        TextView forgetPassword_in_Link=(TextView)findViewById(R.id.forgetpassword_link);


        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp();
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp();
            }
        });
        reset_registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp();
            }
        });

        goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn();
            }
        });
        sign_in_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn();
            }
        });
        reset_sign_in_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn();
            }
        });

        forgetPassword_in_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           goReset();
            }
        });
        hideProgressBar();

        signInEmailField = (EditText) findViewById(R.id.sign_in_username);
        signInPasswordField = (EditText) findViewById(R.id.sign_in_password);

        signUpEmailField = (EditText) findViewById(R.id.register_username);
        signUpPasswordField = (EditText) findViewById(R.id.register_password);
        signUpRetypePasswordField= (EditText) findViewById(R.id.register_retype_password);

        resetEmailField= (EditText) findViewById(R.id.reset_username);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        // Assign fields
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setEnabled(true);
        // Set click listeners
        mSignInButton.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Initialize FirebaseAuth

        emailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createAccount();
            hideKeyboard();
            }
        });

        emailResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
                hideKeyboard();
            }
        });
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
            System.out.println(Current_TAG);
            // Swipe left (next)
            if (e1.getX() - e2.getX() > 400 && Math.abs(e1.getY() - e2.getY())<100 ) {
                viewFlipper.setInAnimation(SignInActivity.this, R.anim.slide_in_right);
                viewFlipper.setOutAnimation(SignInActivity.this,R.anim.slide_out_left);

                if(Current_TAG.equals(Google_SignIn_TAG)){
                    goSignIn();
                    Current_TAG=Email_SignIn_TAG;
                }
            }

            // Swipe right (previous)
            if ( e2.getX() - e1.getX() > 400 && Math.abs(e1.getY() - e2.getY())<100 ) {
                if(Current_TAG.equals(Email_SignIn_TAG)){
                    viewFlipper.setInAnimation(SignInActivity.this, android.R.anim.slide_in_left);
                    viewFlipper.setOutAnimation(SignInActivity.this, android.R.anim.slide_out_right);
                    viewFlipper.setDisplayedChild(0);
                    clearSignUpField();
                    clearResetField();
                    clearSignInField();
                    Current_TAG=Google_SignIn_TAG;
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void goSignIn(){
        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setDisplayedChild(1);
        clearSignUpField();
        clearResetField();
    }

    private void goSignUp(){
        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setDisplayedChild(2);
        clearSignInField();
        clearResetField();
    }

    private void goReset(){
        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setDisplayedChild(3);
        clearSignUpField();
        clearSignInField();
    }


    private void hideProgressBar(){
        signInProgressBar.setVisibility(View.INVISIBLE);
        signUpProgressBar.setVisibility(View.INVISIBLE);
        resetProgressBar.setVisibility(View.INVISIBLE);
        signInProgressBar.setClickable(false);
        signUpProgressBar.setClickable(false);
        resetProgressBar.setClickable(false);
    }

    @Override
    public void onStart(){
        super.onStart();
        hideProgressBar();
        hideKeyboard();
    }

    @Override
    public void onPause(){
        super.onPause();
        hideProgressBar();
        mSignInButton.setEnabled(true);
        hideKeyboard();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        if(viewFlipper.getDisplayedChild()!=0) {
            viewFlipper.setDisplayedChild(0);
            clearSignUpField();
            clearResetField();
            clearSignInField();
            Current_TAG=Google_SignIn_TAG;
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                goolge_signIn();
                break;
            case R.id.email_sign_in_button:
                email_signIn();
                break;
        }
    }

    private void goolge_signIn() {
        hideKeyboard();
        if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(), "Please check your internet connection ", Toast.LENGTH_LONG).show();
        }else {
            signInProgressBar.setVisibility(View.VISIBLE);
            mSignInButton.setEnabled(false);
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    private void email_signIn(){
        hideKeyboard();
        signInProgressBar.setVisibility(View.VISIBLE);
        emailSignInButton.setEnabled(false);

        String email=signInEmailField.getText().toString();
        String password=signInPasswordField.getText().toString();

        Log.d(Email_SignIn_TAG, "signIn:" + email);
        if (!validateForm(Email_SignIn_TAG)) {
            hideProgressBar();
            emailSignUpButton.setEnabled(true);
            emailSignInButton.setEnabled(true);
            return;
        }

//        showProgressDialog();

        // [START sign_in_with_email]
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(Email_SignIn_TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if(task.isSuccessful() && !mFirebaseAuth.getCurrentUser().isEmailVerified()){
                            hideProgressBar();
                            emailSignInButton.setEnabled(true);
                            Toast.makeText(SignInActivity.this, "Please verify your email before you can sign in",Toast.LENGTH_LONG).show();
                        }else if (!task.isSuccessful()) {
                            Log.w(Email_SignIn_TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignInActivity.this, task.getException()+"",Toast.LENGTH_LONG).show();
                            hideProgressBar();
                            emailSignInButton.setEnabled(true);
                        }else {
                            startActivity(new Intent(SignInActivity.this, FunctionActivity.class));
                            finish();
                        }

//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void createAccount() {
        signUpProgressBar.setVisibility(View.VISIBLE);
        emailSignUpButton.setEnabled(false);

        final String email=signUpEmailField.getText().toString();
        final String password=signUpPasswordField.getText().toString();

        Log.d(Email_SignUp_TAG, "createAccount:" + email);
        if (!validateForm(Email_SignUp_TAG)) {
            hideProgressBar();
            emailSignUpButton.setEnabled(true);
            emailSignInButton.setEnabled(true);
            return;
        }

        // [START create_user_with_email]
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(Email_SignUp_TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, task.getException()+"",Toast.LENGTH_LONG).show();
                        hideProgressBar();
                        emailSignUpButton.setEnabled(true);
                    }else {

                        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        if (!mFirebaseUser.isEmailVerified()) {
                            mFirebaseUser.sendEmailVerification();
                        }
                        clearSignUpField();
                        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
                        viewFlipper.showNext();
                        mFirebaseAuth.getInstance().signOut();
                        Toast.makeText(SignInActivity.this,"An verification email have been sent to your mail,please follow instruction to verify your account  ",Toast.LENGTH_LONG).show();
                    }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void resetPassword(){
        resetProgressBar.setVisibility(View.VISIBLE);
        emailResetButton.setEnabled(false);

        final String email=resetEmailField.getText().toString();

        Log.d(Email_Reset_TAG, "resetAccount:" + email);
        if (!validateForm(Email_Reset_TAG)) {
            hideProgressBar();
            emailSignUpButton.setEnabled(true);
            emailSignInButton.setEnabled(true);
            emailResetButton.setEnabled(true);
            return;
        }

//        showProgressDialog();

        // [START create_user_with_email]
        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(Email_SignUp_TAG, "resetEmailSend:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, task.getException()+"",Toast.LENGTH_LONG).show();
                    hideProgressBar();
                    emailResetButton.setEnabled(true);
                }else {
                    resetProgressBar.setVisibility(View.INVISIBLE);
                    clearResetField();
                    final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
                    viewFlipper.setDisplayedChild(0);
                    Toast.makeText(getApplicationContext(),"An reset email has sent to your account",Toast.LENGTH_LONG).show();

                }
             }
        });
    }

    private boolean validateForm(String Tag) {
        boolean valid = true;
        if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(), "Please check your internet connection ", Toast.LENGTH_LONG).show();
            valid = false;
        }else if(Tag.equalsIgnoreCase(Email_SignIn_TAG)){
            String email = signInEmailField.getText().toString();
            if (TextUtils.isEmpty(email)) {
                signInEmailField.setError("Required.");
                valid = false;
            } else {
                signInEmailField.setError(null);
            }

            String password = signInPasswordField.getText().toString();
            if (TextUtils.isEmpty(password)) {
                signInPasswordField.setError("Required.");
                valid = false;
            } else {
                signInPasswordField.setError(null);
            }
        }else if(Tag.equalsIgnoreCase(Email_SignUp_TAG)){
            String email = signUpEmailField.getText().toString();

            if (TextUtils.isEmpty(email)) {
                signUpEmailField.setError("Required.");
                signUpEmailField.requestFocus();
                valid = false;
            } else {
                signUpEmailField.setError(null);
            }

            String password = signUpPasswordField.getText().toString();
            if (TextUtils.isEmpty(password)) {
                signUpPasswordField.setError("Required.");
                signUpPasswordField.requestFocus();
                valid = false;
            }else if(password.length()<6){
                signUpPasswordField.setError("Password should at least 6 digit/character length");
                signUpPasswordField.requestFocus();
                valid = false;
            } else {
                signUpPasswordField.setError(null);
            }

            String retype_password = signUpRetypePasswordField.getText().toString();
            if (TextUtils.isEmpty(retype_password)) {
                signUpRetypePasswordField.setError("Required.");
                signUpRetypePasswordField.requestFocus();
                valid = false;
            }else if(!password.equalsIgnoreCase(retype_password)){
                signUpRetypePasswordField.setError("Password not match.");
                signUpRetypePasswordField.requestFocus();
                valid = false;
            } else {
                signUpRetypePasswordField.setError(null);
            }
        }else if(Tag.equalsIgnoreCase(Email_Reset_TAG)){
            String email = resetEmailField.getText().toString();

            if (TextUtils.isEmpty(email)) {
                resetEmailField.setError("Required.");
                resetEmailField.requestFocus();
                valid = false;
            } else {
                resetEmailField.setError(null);
            }

        }


        return valid;
    }

    private void clearSignInField(){
        signInPasswordField.setText("");
        signInEmailField.setText("");
        signInEmailField.setError(null);
        signInPasswordField.setError(null);
    }

    private void clearSignUpField(){
        signUpEmailField.setText("");
        signUpRetypePasswordField.setText("");
        signUpPasswordField.setText("");
        signUpEmailField.setError(null);
        signUpRetypePasswordField.setError(null);
        signUpPasswordField.setError(null);
    }

    private void clearResetField(){
        resetEmailField.setText("");
        resetEmailField.setError(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(Google_SignIn_TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
        hideProgressBar();
        mSignInButton.setEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signInProgressBar.setVisibility(View.VISIBLE);
        emailSignInButton.setEnabled(false);
        mSignInButton.setEnabled(false);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else{
                // Google Sign In failed
                hideProgressBar();
                Log.e(Google_SignIn_TAG, "Google Sign In failed.");
                mSignInButton.setEnabled(true);
                emailSignInButton.setEnabled(true);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(Google_SignIn_TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(Google_SignIn_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(Google_SignIn_TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                            mSignInButton.setEnabled(true);
                            emailSignInButton.setEnabled(true);
                        } else {
                            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    System.out.println("dataSnapshot is " +dataSnapshot.getValue());

                                   if(dataSnapshot.getValue()==null){
                                       ArrayList<Function> functions=new ArrayList<>();
                                       functions.add(new Function());
                                       mDatabase.child("function").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(functions);
                                   }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            startActivity(new Intent(SignInActivity.this, FunctionActivity.class));
                            finish();
                        }
                    }
                });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        signInEmailField.clearFocus();
        signInPasswordField.clearFocus();
        signUpEmailField.clearFocus();
        signUpPasswordField.clearFocus();
        signUpRetypePasswordField.clearFocus();
        resetEmailField.clearFocus();
    }

}
