package com.example.edsirrequirement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookletsScanningActivity extends AppCompatActivity implements MyAdapter.callbackData {

    int bundleNumber,numberofBooklets;
    RecyclerView rv;
    TextView bundleNumberTextView;
    private IntentIntegrator qrScan;
    String marks;

    List<MyData> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklets_scanning);
        dataList = new ArrayList<>();
        rv = findViewById(R.id.recyclerView);
        qrScan = new IntentIntegrator(this);
        bundleNumberTextView = findViewById(R.id.textView);
        Intent i = getIntent();
        if(i.hasExtra("bundle")){
            bundleNumber = i.getIntExtra("bundle",0);
            numberofBooklets = i.getIntExtra("booklet",0);
            bundleNumberTextView.setText("Bundle Number is : "+bundleNumber);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter(BookletsScanningActivity.this,bundleNumber,numberofBooklets,qrScan,marks,this,""));

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

                    //ArrayList<MyPojo> list = new ArrayList();
                   /* String[] splited = result.getContents().split("\n");

                    for(int i=0;i<splited.length;i++){

                        String[] linesplit = splited[i].split(" ");

                    }
*/
                    Toast.makeText(this, ""+result.getContents(), Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
                    LayoutInflater inflater = this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.mylayout, null);
                    dialogBuilder.setView(dialogView);
                    final EditText et = dialogView.findViewById(R.id.input);
                    Button b = dialogView.findViewById(R.id.button);
                    dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            marks = et.getText().toString();
                            rv.setAdapter(new MyAdapter(BookletsScanningActivity.this,bundleNumber,numberofBooklets,qrScan,marks,BookletsScanningActivity.this,result.getContents()));

                            dialog.dismiss();

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


    public void commit(View view) {


    }



    @Override
    public void getData(int position, String rollno, int bundleNumber, int numberofBooklets, String rollnumberMarks) {

        Toast.makeText(this,
                ""+position+" "+rollno+" "+bundleNumber+" "+numberofBooklets+" "+rollnumberMarks, Toast.LENGTH_SHORT).show();

    }
}
