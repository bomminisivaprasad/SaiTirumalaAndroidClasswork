package com.example.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    String[] names={"Sai","Sankar","Gopal",
            "Masthan","Priya","Lakshmi",
    "Jyothi","Sreevani","Kiranbedi",
            "Bhargavi","Preethi","Archana",
            "AllaBhakshu","Krishna","Anusha",
    "Deepu","Gopi","Shakku","AndroidStudio",
            "Koritala"};

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.list);

        ArrayAdapter<String> adapter=
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        names);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit:
                Toast.makeText(this,
                        item.getTitle(),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.delete:
                Toast.makeText(this,
                        item.getTitle(),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        // Action
        return super.onContextItemSelected(item);
    }


}
