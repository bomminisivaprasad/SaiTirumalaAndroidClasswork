package com.example.user.e_quiz;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private static String email;

    private final String NIC = "NO INTERNET CONNECTION";
    private final String ntoast = "No internet connection found, check your connection or try again";
    private final String canc = "CANCEL";
    private final String clo = "CLOSE";
    private final String invcre = "INVALID CRENDENTIALS";
    private final String authentication = "Authentication failed, check your email and password or sign up";
    private final String ok = "OK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, Levels.class));
            finish();
        }
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        Button btnSignup = findViewById(R.id.btn_signup);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnReset = findViewById(R.id.btn_reset_password);


        auth = FirebaseAuth.getInstance();
        if (!isOnline()) {
            new FancyGifDialog.Builder(this)
                    .setTitle(NIC)
                    .setMessage(ntoast)
                    .setNegativeBtnText(canc)
                    .setPositiveBtnBackground("#FF4081")
                    .setPositiveBtnText(clo)
                    .setNegativeBtnBackground("#FFA9A7A8")
                    .setGifResource(R.drawable.nointernett)
                    .isCancellable(false)
                    .OnPositiveClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            finish();
                        }
                    })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .build();
        }
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPassword.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isOnline()) {
                    new FancyGifDialog.Builder(LoginActivity.this)
                            .setTitle(NIC)
                            .setMessage(ntoast)
                            .setNegativeBtnText(canc)
                            .setPositiveBtnBackground("#FF4081")
                            .setPositiveBtnText(clo)
                            .setNegativeBtnBackground("#FFA9A7A8")
                            .setGifResource(R.drawable.nointernett)
                            .isCancellable(false)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    finish();
                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {

                                }
                            })
                            .build();
                }
                email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.emailtoast), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.passtoast), Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {

                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {

                                        if (!isOnline()) {
                                            new FancyGifDialog.Builder(LoginActivity.this)
                                                    .setTitle(NIC)
                                                    .setMessage(ntoast)
                                                    .setNegativeBtnText(canc)
                                                    .setPositiveBtnBackground("#FF4081")
                                                    .setPositiveBtnText(clo)
                                                    .setNegativeBtnBackground("#FFA9A7A8")
                                                    .setGifResource(R.drawable.nointernett)
                                                    .isCancellable(false)
                                                    .OnPositiveClicked(new FancyGifDialogListener() {
                                                        @Override
                                                        public void OnClick() {
                                                            finish();
                                                        }
                                                    })
                                                    .OnNegativeClicked(new FancyGifDialogListener() {
                                                        @Override
                                                        public void OnClick() {

                                                        }
                                                    })
                                                    .build();
                                        }
                                        if (!task.isSuccessful()) {
                                            new FancyGifDialog.Builder(LoginActivity.this)
                                                    .setTitle(invcre)
                                                    .setMessage(authentication)
                                                    .setNegativeBtnText(canc)
                                                    .setPositiveBtnBackground("#FF4081")
                                                    .setPositiveBtnText(ok)
                                                    .setGifResource(R.drawable.notok)
                                                    .setNegativeBtnBackground("#FFA9A7A8")
                                                    .isCancellable(false)
                                                    .OnPositiveClicked(new FancyGifDialogListener() {
                                                        @Override
                                                        public void OnClick() {

                                                        }
                                                    })
                                                    .OnNegativeClicked(new FancyGifDialogListener() {
                                                        @Override
                                                        public void OnClick() {

                                                        }
                                                    })
                                                    .build();
                                        }
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, Levels.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}