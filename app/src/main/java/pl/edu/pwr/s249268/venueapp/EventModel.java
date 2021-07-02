package pl.edu.pwr.s249268.venueapp;

public class EventModel {

    private int id;
    private String location;
    private String date;
    private int ticket_amout;
    private int status;

    public EventModel(int id, String location, String date, int ticket_amout, int status) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.ticket_amout = ticket_amout;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTicket_amout() {
        return ticket_amout;
    }

    public void setTicket_amout(int ticket_amout) {
        this.ticket_amout = ticket_amout;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
