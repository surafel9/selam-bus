package selambusreservationsystem;

public class Trip {

    private int tripId;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private double price;
    private String bus;

    public Trip(int tripId, String origin, String destination,
                String date, String time, double price, String bus) {

        this.tripId = tripId;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.bus = bus;
    }

    public int getTripId() {
        return tripId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getPrice() {
        return price;
    }

    public String getBus() {
        return bus;
    }
}