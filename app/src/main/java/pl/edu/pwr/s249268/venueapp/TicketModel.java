package pl.edu.pwr.s249268.venueapp;

public class TicketModel {

    private int id;
    private int customer_id;
    private int venue_id;

    public TicketModel(int id, int customer_id, int venue_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.venue_id = venue_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(int venue_id) {
        this.venue_id = venue_id;
    }
}
