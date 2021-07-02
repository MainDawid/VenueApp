package pl.edu.pwr.s249268.venueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    EditText location, ticketAmount;
    TextView date;
    Button createNewEvent;
    DatePickerDialog.OnDateSetListener setListener;
    DataBaseHelper dataBaseHelper;
    EventModel eventModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        location = findViewById(R.id.event_location);
        date = findViewById(R.id.event_date);
        ticketAmount = findViewById(R.id.event_ticketAmount);
        createNewEvent = findViewById(R.id.event_createButton);
        dataBaseHelper = new DataBaseHelper(this);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddEventActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String read_date = dayOfMonth + "/" + month + "/" + year;
                date.setText(read_date);
            }
        };


        createNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typed_location = location.getText().toString();
                String chosen_date = date.getText().toString();
                String typed_TicketAmount = ticketAmount.getText().toString();

                if (typed_location.equals("") || typed_TicketAmount.equals("") || chosen_date.equals("")||typed_TicketAmount.equals("0"))
                {
                    Toast.makeText(AddEventActivity.this, "Error adding Event.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean checkEventInstance = dataBaseHelper.checkEvent(typed_location,chosen_date);

                    if(!checkEventInstance)
                    {
                        eventModel = new EventModel(-1,typed_location,chosen_date,Integer.parseInt(ticketAmount.getText().toString()),0);  //Event status taken = 1, available = 0
                        dataBaseHelper = new DataBaseHelper(AddEventActivity.this);
                        boolean success = dataBaseHelper.addEvent(eventModel);

                        if(success)
                        {
                            //Toast.makeText(AddEventActivity.this, "Successfully added new Event", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AddEventActivity.this, eventModel.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                            startActivity(intent);
                            AddEventActivity.this.finish();
                        }
                        else
                        {
                            Toast.makeText(AddEventActivity.this, "Event creation failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddEventActivity.this, "There is an event at this location on that day.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}