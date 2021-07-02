package pl.edu.pwr.s249268.venueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BandHomeActivity extends AppCompatActivity {

    TextView mainText;
    Button addVenue_button, showVenues_button;
    ListView lv_eventList;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    String bandUserName, bandName;
    boolean mode; // false = event / true = venue
    VenueModel venueModel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_home);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bandUserName = extras.getString("BandName");
        }

        mode = true;

        addVenue_button = findViewById(R.id.button_band_add_venue);
        showVenues_button = findViewById(R.id.button_band_show_my_venues);
        lv_eventList = findViewById(R.id.lv_band_eventList);
        dataBaseHelper = new DataBaseHelper(this);
        listItem = new ArrayList<>();
        mainText = findViewById(R.id.tv_band_home);

        getBandName();
        mainText.setText("Hello,  " + bandName);



        addVenue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = true;
                viewData();
            }
        });



        showVenues_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = false;
                viewMyVenues();
            }
        });



        lv_eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(mode)
                {
                    Cursor cursor = dataBaseHelper.viewData();
                    cursor.moveToPosition(position);

                    String venue_location = cursor.getString(1);
                    String venue_date = cursor.getString(2);
                    int venue_tickets = cursor.getInt(3);

                    VenueModel venueModel = new VenueModel(-1, venue_location, venue_date,venue_tickets, bandName);

                    dataBaseHelper.addVenue(venueModel);
                    dataBaseHelper.changeStatus(venue_location, venue_date);
                    viewData();
                    Toast.makeText(BandHomeActivity.this, venueModel.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(BandHomeActivity.this, "CLICKED", Toast.LENGTH_SHORT).show();
                }
            }
            }
        );
    }


    private void getBandName()
    {
        Cursor cursor = dataBaseHelper.ReturnBandName(bandUserName);
        cursor.moveToFirst();

        bandName = cursor.getString(0);
    }

    private void viewData()
    {
        listItem.clear();
        Cursor cursor = dataBaseHelper.viewData();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(BandHomeActivity.this, "No data to show", Toast.LENGTH_SHORT).show();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            adapter.notifyDataSetChanged();
            lv_eventList.setAdapter(adapter);
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
    public void onBackPressed()
    {
        Toast.makeText(BandHomeActivity.this, "You have been logout.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        BandHomeActivity.this.finish();
    }



    private void viewMyVenues()
    {
        listItem.clear();
        Cursor cursor = dataBaseHelper.returnVenues(bandName);
        if(cursor.getCount() == 0)
        {
            Toast.makeText(BandHomeActivity.this, "No data to show", Toast.LENGTH_SHORT).show();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            adapter.notifyDataSetChanged();
            lv_eventList.setAdapter(adapter);
        }
        else
        {
            while (cursor.moveToNext())
            {
                String venue_location = cursor.getString(1);
                String venue_date = cursor.getString(2);
                int venue_tickets = cursor.getInt(3);
                listItem.add(bandName + " concert in " + venue_location + " on date " + venue_date + ". Remaining Tickets: " + venue_tickets + ".");
            }
          adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
          lv_eventList.setAdapter(adapter);
        }
    }
}




