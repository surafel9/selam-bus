package selambusreservationsystem;

public class BookingDisplay {
    private int bookingId;
    private String passengerName;
    private String busName;
    private String origin;
    private String destination;
    private String date;
    private int seatNumber;
    private String status;

    public BookingDisplay(int bookingId, String passengerName, String busName, 
                          String origin, String destination, String date, 
                          int seatNumber, String status) {
        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.busName = busName;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.seatNumber = seatNumber;
        this.status = status;
    }


    public int getBookingId() { return bookingId; }
    public String getPassengerName() { return passengerName; }
    public String getBusName() { return busName; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDate() { return date; }
    public int getSeatNumber() { return seatNumber; }
    public String getStatus() { return status; }
}