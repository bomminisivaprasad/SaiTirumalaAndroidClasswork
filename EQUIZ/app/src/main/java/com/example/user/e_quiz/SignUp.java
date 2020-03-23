package com.example.user.e_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.e_quiz.MyModel.MyScore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class SignUp extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private final FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    private final String x = "X";
    private final String invalid = "INVALID CRENDENTIALS";
    private final String EEa = "Enter E-mail address";
    private final String cancel = "CANCEL";
    private final String ok = "OK";
    private final String EP = "Enter Password!";
    private final String pea = "Please Enter Valid E-Mail Address!";
    private final String RS = "REGISTRATION SUCCESS";
    private final String YS = "You have succesfully registered,Keep going :)";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        auth = FirebaseAuth.getInstance();

        Button btnSignIn = findViewById(R.id.sign_in_button);
        Button btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        Button btnResetPassword = findViewById(R.id.btn_reset_password);

        String MScore = "MyScore";
        databaseReference = reference.getReference(MScore);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, ResetPassword.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    new FancyGifDialog.Builder(SignUp.this)
                            .setTitle(invalid)
                            .setMessage(EEa)
                            .setNegativeBtnText(cancel)
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
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    new FancyGifDialog.Builder(SignUp.this)
                            .setTitle(invalid)
                            .setMessage(EP)
                            .setNegativeBtnText(cancel)
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
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), getString(R.string.suptoast), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {

                                    new FancyGifDialog.Builder(SignUp.this)
                                            .setTitle(invalid)
                                            .setMessage(pea)
                                            .setNegativeBtnText(cancel)
                                            .setPositiveBtnBackground("#FF4081")
                                            .setPositiveBtnText(ok)
                                            .setNegativeBtnBackground("#FFA9A7A8")
                                            .setGifResource(R.drawable.notok)
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
                                } else {
                                    new FancyGifDialog.Builder(SignUp.this)
                                            .setTitle(RS)
                                            .setMessage(YS)
                                            .setNegativeBtnText("")
                                            .setPositiveBtnBackground("#FF4081")
                                            .setPositiveBtnText(ok)
                                            .setNegativeBtnBackground("#ffffff")
                                            .setGifResource(R.drawable.ok)
                                            .isCancellable(false)
                                            .OnPositiveClicked(new FancyGifDialogListener() {
                                                @Override
                                                public void OnClick() {
                                                    MyScore myScore = new MyScore(email, x, x, x,
                                                            x, x, x, x, x, x, x, x, x);
                                                    String insert = databaseReference.push().getKey();
                                                    databaseReference.child(insert).setValue(myScore);
                                                    startActivity(new Intent(SignUp.this, JavaActivity.class));
                                                    finish();

                                                }
                                            })
                                            .build();


                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

