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

public class CustomerHomeActivity extends AppCompatActivity {

    String userName, user_Name, user_Surname;
    TextView customerMain;
    Button buyTicket_button, viewTickets_button;
    ListView lv_venueList;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("UserName");
        }

        customerMain = findViewById(R.id.tv_customer_home);
        dataBaseHelper = new DataBaseHelper(this);
        buyTicket_button = findViewById(R.id.button_customer_buy_ticket);
        viewTickets_button = findViewById(R.id.button_customer_see_my_tickets);
        lv_venueList = findViewById(R.id.lv_customer_List);
        listItem = new ArrayList<>();

        getUser();
        customerMain.setText("Hello, " + user_Name + " " + user_Surname);

        System.out.println(userName);
        System.out.println(user_Name);
        System.out.println(user_Surname);

        buyTicket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewVenues();
                Toast.makeText(CustomerHomeActivity.this, "Buy Tickets", Toast.LENGTH_SHORT).show();
            }
        });

        viewTickets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerHomeActivity.this, "See Tickets", Toast.LENGTH_SHORT).show();
            }
        });

        lv_venueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void viewVenues()
    {
        listItem.clear();
        Cursor cursor = dataBaseHelper.returnCustomerVenues();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(CustomerHomeActivity.this, "No data to show", Toast.LENGTH_SHORT).show();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            adapter.notifyDataSetChanged();
            lv_venueList.setAdapter(adapter);
        }
        else
        {
            while (cursor.moveToNext())
            {
                String location = cursor.getString(1);
                String date = cursor.getString(2);
                int remaining_Tickets = cursor.getInt(3);
                String band_name = cursor.getString(4);

                listItem.add(band_name + " concert in " + location + " on " + date + ". Remaining tickets: " + remaining_Tickets);
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            lv_venueList.setAdapter(adapter);
        }
    }


    private void getUser()
    {
        Cursor cursor = dataBaseHelper.ReturnUser(userName);
        cursor.moveToFirst();
            user_Name = cursor.getString(2);
            user_Surname = cursor.getString(3);

    }


    @Override
    public void onBackPressed()
    {
        Toast.makeText(CustomerHomeActivity.this, "You have been logout.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        CustomerHomeActivity.this.finish();
    }

}

