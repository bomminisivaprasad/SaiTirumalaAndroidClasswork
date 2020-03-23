package com.example.farmerskart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Spinner usertype;
    EditText et_u, et_p;

    FirebaseDatabase database;
    DatabaseReference reference;

    List<User> usersList;
    String selectedUser, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usertype = findViewById(R.id.loginusertype);
        et_u = findViewById(R.id.loginusername);
        et_p = findViewById(R.id.loginpassword);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


    }

    public void login(View view) {
        selectedUser = usertype.getSelectedItem().toString();
        username = et_u.getText().toString();
        password = et_p.getText().toString();
        usersList = new ArrayList<>();
        reference.child("Users").child(selectedUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    usersList.add(u);
                }
                if (username.isEmpty() || password.isEmpty() || (selectedUser.equals("Select User Type"))) {
                    Toast.makeText(LoginActivity.this,
                            "Please fill the Details", Toast.LENGTH_SHORT).show();
                } else {
                    switch (selectedUser) {
                        case "Select User Type":
                            Toast.makeText(LoginActivity.this,
                                    "Please Select the Usertype", Toast.LENGTH_SHORT).show();
                            break;
                        case "Seller":
                            sellerLogin();
                            break;
                        case "Buyer":
                            buyerLogin();
                            break;
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void buyerLogin() {
        for (int i = 0; i < usersList.size(); i++) {
            if ((username.equals(usersList.get(i).getUsername())) &&
                    (password.equals(usersList.get(i).getPassword())) &&
                    (selectedUser.equals(usersList.get(i).getTypeofUser()))) {
                Intent buyerIntent = new Intent(getApplicationContext(), BuyerNavigationDrawerActivity.class);
                buyerIntent.putExtra("bname", usersList.get(i).getName());
                buyerIntent.putExtra("bmobile", usersList.get(i).getMobile());
                buyerIntent.putExtra("bdistrict", usersList.get(i).getDistrict());
                buyerIntent.putExtra("baddress", usersList.get(i).getAddress());
                buyerIntent.putExtra("btype", usersList.get(i).getTypeofUser());
                buyerIntent.putExtra("bimagepath", usersList.get(i).getImagePath());
                buyerIntent.putExtra("blocation", usersList.get(i).getLocation());
                startActivity(buyerIntent);
                finish();
                et_u.setText(" ");
                et_p.setText(" ");
                return;

            } else {
                Toast.makeText(this,
                        "InValid User", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sellerLogin() {
        for (int i = 0; i < usersList.size(); i++) {
            if ((username.equals(usersList.get(i).getUsername())) &&
                    (password.equals(usersList.get(i).getPassword())) &&
                    (selectedUser.equals(usersList.get(i).getTypeofUser()))) {
                Intent buyerIntent = new Intent(getApplicationContext(), SellerNavigationDrawerActivity.class);
                buyerIntent.putExtra("sname", usersList.get(i).getName());
                buyerIntent.putExtra("smobile", usersList.get(i).getMobile());
                buyerIntent.putExtra("sdistrict", usersList.get(i).getDistrict());
                buyerIntent.putExtra("saddress", usersList.get(i).getAddress());
                buyerIntent.putExtra("stype", usersList.get(i).getTypeofUser());
                buyerIntent.putExtra("simagepath", usersList.get(i).getImagePath());
                buyerIntent.putExtra("slocation", usersList.get(i).getLocation());
                startActivity(buyerIntent);
                et_u.setText(" ");
                et_p.setText(" ");
                finish();
                return;

            } else {
                Toast.makeText(this,
                        "InValid User", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
