package pl.edu.pwr.s249268.venueapp;

public class VenueModel {

    private int id;
    private String event_location;
    private String event_date;
    private String band_name;
    private int available_tickets;

    public VenueModel(int id, String event_location, String event_date, int available_tickets, String band_name) {
        this.id = id;
        this.event_location = event_location;
        this.event_date = event_date;
        this.available_tickets = available_tickets;
        this.band_name = band_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getBand_name() {
        return band_name;
    }

    public void setBand_name(String band_name) {
        this.band_name = band_name;
    }

    public int getAvailable_tickets() {
        return available_tickets;
    }

    public void setAvailable_tickets(int available_tickets) {
        this.available_tickets = available_tickets;
    }
}

