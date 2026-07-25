package airline.model;

public class Booking {
    private String pnr;
    private Passenger passenger;
    private Flight flight;
    private String seatClass;
    private int passengers;
    private String seatPreference;
    private double total;
    private String paymentStatus;

    public Booking(String pnr, Passenger passenger, Flight flight, String seatClass, int passengers, String seatPreference, double total, String paymentStatus) {
        this.pnr = pnr;
        this.passenger = passenger;
        this.flight = flight;
        this.seatClass = seatClass;
        this.passengers = passengers;
        this.seatPreference = seatPreference;
        this.total = total;
        this.paymentStatus = paymentStatus;
    }

    public String getPnr() { return pnr; }
    public Passenger getPassenger() { return passenger; }
    public Flight getFlight() { return flight; }
    public String getSeatClass() { return seatClass; }
    public int getPassengers() { return passengers; }
    public String getSeatPreference() { return seatPreference; }
    public double getTotal() { return total; }
    public String getPaymentStatus() { return paymentStatus; }
}
