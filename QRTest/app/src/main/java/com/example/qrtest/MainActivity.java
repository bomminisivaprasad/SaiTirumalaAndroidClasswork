package com.example.qrtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
        preferences = getSharedPreferences("MyPrefrence",MODE_PRIVATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    //textViewName.setText(result.getContents());
                    Toast.makeText(this, ""+result.getContents(), Toast.LENGTH_SHORT).show();
                    /*final Dialog d = new Dialog(this);
                    d.setContentView(R.layout.mylayout);
                    final EditText et = d.findViewById(R.id.input);
                    Button b = d.findViewById(R.id.button);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(result.getContents(),et.getText().toString());
                            editor.apply();
                            Toast.makeText(MainActivity.this, "Marks are Saved Success Fully....", Toast.LENGTH_SHORT).show();
                            et.setText("");
                            d.dismiss();
                            Intent i = new Intent(MainActivity.this,DataViewActivity.class);
                            startActivity(i);

                        }
                    });
                    d.show();*/

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
                    LayoutInflater inflater = this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.mylayout, null);
                    dialogBuilder.setView(dialogView);
                    final EditText et = dialogView.findViewById(R.id.input);
                    Button b = dialogView.findViewById(R.id.button);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(result.getContents(),et.getText().toString());
                            editor.apply();

                            Toast.makeText(MainActivity.this, "Marks are Saved Success Fully....", Toast.LENGTH_SHORT).show();
                            et.setText("");
                            //dialogView.dismiss();
                            Intent i = new Intent(MainActivity.this,DataViewActivity.class);
                            startActivity(i);

                        }
                    });
                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
