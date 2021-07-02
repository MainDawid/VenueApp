package pl.edu.pwr.s249268.venueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    Button addEvent_button, showEvents_button;
    ListView lv_eventList;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> listItem;
    ArrayAdapter adapter;


    private void viewData()
    {
        listItem.clear();
        Cursor cursor = dataBaseHelper.viewData();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();

        }
        else
        {
            while (cursor.moveToNext())
            {
                String location = cursor.getString(1);
                String date = cursor.getString(2);
                listItem.add("Available Event in: " + location + ", on: " + date +".");
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);

            lv_eventList.setAdapter(adapter);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addEvent_button = findViewById(R.id.button_admin_add_event);
        showEvents_button = findViewById(R.id.button_admin_show_events);
        lv_eventList = findViewById(R.id.lv_admin_eventList);
        dataBaseHelper = new DataBaseHelper(this);
        listItem = new ArrayList<>();


        addEvent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(intent);
                AdminHomeActivity.this.finish();
            }
        });


        showEvents_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewData();


            }
        });


    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(AdminHomeActivity.this, "You have been logout.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}