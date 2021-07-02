package pl.edu.pwr.s249268.venueapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String BAND_TABLE = "BAND_TABLE";
    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String VENUE_TABLE = "VENUE_TABLE";
    public static final String TICKET_TABLE = "TICKETS_TABLE";


    public static final String COLUMN_CUSTOMER_ID = "ID";
    public static final String COLUMN_CUSTOMER_USERNAME = "USERNAME";
    public static final String COLUMN_CUSTOMER_NAME = "NAME";
    public static final String COLUMN_CUSTOMER_SURNAME = "SURNAME";
    public static final String COLUMN_CUSTOMER_PASSWORD = "PASSWORD";


    public static final String COLUMN_BAND_ID = "ID";
    public static final String COLUMN_BAND_NAME = "BAND_NAME";
    public static final String COLUMN_BAND_USERNAME = "USERNAME";
    public static final String COLUMN_BAND_PASSWORD = "PASSWORD";


    public static final String COLUMN_EVENT_ID = "ID";
    public static final String COLUMN_EVENT_LOCATION = "EVENT_LOCATION";
    public static final String COLUMN_EVENT_DATE = "EVENT_DATE";
    public static final String COLUMN_EVENT_STATUS = "EVENT_STATUS";
    public static final String COLUMN_EVENT_TICKET_AMOUNT = "EVENT_TICKET_AMOUNT";

    public static final String COLUMN_VENUE_ID = "ID";
    public static final String COLUMN_VENUE_EVENT_LOCATION = "VENUE_LOCATION";
    public static final String COLUMN_VENUE_EVENT_DATE = "VENUE_DATE";
    public static final String COLUMN_VENUE_AVAILABLE_TICKETS = "VENUE_TICKETS";
    public static final String COLUMN_VENUE_BAND_NAME = "BAND_NAME";

    public static final String COLUMN_TICKET_ID = "ID";
    public static final String COLUMN_TICKET_VENUE_ID = "VENUE_ID";
    public static final String COLUMN_TICKET_CUSTOMER_ID = "CUSTOMER_ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "venue.db", null, 1);
    }

    // this is called first time. Generate
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatementCustomer = "CREATE TABLE " + CUSTOMER_TABLE + " ( "
                + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CUSTOMER_USERNAME + " TEXT , "
                + COLUMN_CUSTOMER_NAME + " TEXT , "
                + COLUMN_CUSTOMER_SURNAME + " TEXT , "
                + COLUMN_CUSTOMER_PASSWORD + " TEXT )";


        String createTableStatementBand = "CREATE TABLE " + BAND_TABLE + " ( "
                + COLUMN_BAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BAND_USERNAME + " TEXT , "
                + COLUMN_BAND_NAME + " TEXT , "
                + COLUMN_BAND_PASSWORD + " TEXT )";

        String createTableStatementEvent = "CREATE TABLE " + EVENT_TABLE + " ( "
                + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EVENT_LOCATION + " TEXT , "
                + COLUMN_EVENT_DATE + " TEXT , "
                + COLUMN_EVENT_TICKET_AMOUNT + " INTEGER, "
                + COLUMN_EVENT_STATUS + " INTEGER ) ";

        String createTableStatementVenue = "CREATE TABLE " + VENUE_TABLE + " ( "
                + COLUMN_VENUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_VENUE_EVENT_LOCATION + " TEXT , "
                + COLUMN_VENUE_EVENT_DATE + " TEXT , "
                + COLUMN_VENUE_AVAILABLE_TICKETS + " INTEGER, "
                + COLUMN_VENUE_BAND_NAME + " TEXT )";

        String createTableStatementTicket = "CREATE TABLE " + TICKET_TABLE + " ( "
                + COLUMN_TICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TICKET_VENUE_ID + " INTEGER, "
                + COLUMN_TICKET_CUSTOMER_ID + " INTEGER )";

        db.execSQL(createTableStatementTicket);
        db.execSQL(createTableStatementVenue);
        db.execSQL(createTableStatementEvent);
        db.execSQL(createTableStatementBand);
        db.execSQL(createTableStatementCustomer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public boolean addTickets(TicketModel ticketModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TICKET_CUSTOMER_ID, ticketModel.getCustomer_id());
        cv.put(COLUMN_TICKET_VENUE_ID, ticketModel.getVenue_id());
        long insert = db.insert(TICKET_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addVenue(VenueModel venueModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_VENUE_EVENT_LOCATION, venueModel.getEvent_location());
        cv.put(COLUMN_VENUE_EVENT_DATE, venueModel.getEvent_date());
        cv.put(COLUMN_VENUE_AVAILABLE_TICKETS, venueModel.getAvailable_tickets());
        cv.put(COLUMN_VENUE_BAND_NAME, venueModel.getBand_name());
        long insert = db.insert(VENUE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addEvent(EventModel eventModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_LOCATION, eventModel.getLocation());
        cv.put(COLUMN_EVENT_DATE, eventModel.getDate());
        cv.put(COLUMN_EVENT_TICKET_AMOUNT, eventModel.getTicket_amout());
        cv.put(COLUMN_EVENT_STATUS, false);

        long insert = db.insert(EVENT_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean addCustomer(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_USERNAME, customerModel.getUsername());
        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_SURNAME, customerModel.getSurname());
        cv.put(COLUMN_CUSTOMER_PASSWORD, customerModel.getPassword());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addBand(BandModel bandModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BAND_USERNAME, bandModel.getUsername());
        cv.put(COLUMN_BAND_NAME, bandModel.getName());
        cv.put(COLUMN_BAND_PASSWORD, bandModel.getPassword());

        long insert = db.insert(BAND_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void changeStatus(String location, String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        int no = 1;
        String stringQuery = "UPDATE " + EVENT_TABLE + " SET " + COLUMN_EVENT_STATUS + " = '1'  WHERE " + COLUMN_EVENT_LOCATION + " ='" + location + "' AND " + COLUMN_EVENT_DATE + " ='" + date + "';";
        db.execSQL(stringQuery);
        //db.execSQL("UPDATE " + EVENT_TABLE + " SET " + COLUMN_EVENT_STATUS + " = '1'  WHERE " + COLUMN_EVENT_LOCATION + " =? AND "");
        db.close();

    }


    public boolean checkUserName(String username, boolean type)        //type 1 == customer, 0 == band
    {
        //SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (type) {
            cursor = db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = ? ", new String[]{username});

        } else {
            cursor = db.rawQuery("SELECT * FROM " + BAND_TABLE + " WHERE " + COLUMN_BAND_USERNAME + " = ?", new String[]{username});
        }
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean checkPassword(String username, String password, boolean type) //type 1 == customer, 0 == band
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (type) {
            cursor = db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = ? and " + COLUMN_CUSTOMER_PASSWORD + " = ?", new String[]{username, password});
        } else {
            cursor = db.rawQuery("SELECT * FROM " + BAND_TABLE + " WHERE " + COLUMN_BAND_USERNAME + " = ? and " + COLUMN_BAND_PASSWORD + " = ?", new String[]{username, password});
        }

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }


    public Cursor ReturnBandName(String bandUserName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_BAND_NAME + " FROM " + BAND_TABLE + " WHERE " + COLUMN_BAND_USERNAME + " = ? ", new String[]{bandUserName});
        return cursor;
    }

    public Cursor ReturnUser(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = ? ", new String[]{userName});
        return cursor;
    }

    public Cursor returnVenues(String bandName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + VENUE_TABLE + " WHERE " + COLUMN_VENUE_BAND_NAME + " =?", new String[]{bandName});
        return cursor;
    }

    public Cursor returnCustomerVenues() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + VENUE_TABLE, null);
        return cursor;
    }

    public boolean checkEvent(String location, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + EVENT_TABLE + " WHERE " + COLUMN_EVENT_LOCATION + " = ? and " + COLUMN_EVENT_DATE + " = ? ", new String[]{location, date});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }


    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + EVENT_TABLE + " WHERE " + COLUMN_EVENT_STATUS + " = 0 ", null);

        return cursor;
    }

    public Cursor returnCustomerID(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT " + COLUMN_CUSTOMER_ID + " FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_CUSTOMER_USERNAME + " = ? ", new String[]{username});

        return cursor;
    }
}