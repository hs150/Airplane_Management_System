package airline.model;

public class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String aircraft;
    private String pilot;
    private int capacity;
    private int bookedSeats;
    private int availableSeats;
    private double price;
    private String status;

    public Flight(String flightNumber, String source, String destination, String departureTime, String arrivalTime,
                  String duration, String aircraft, String pilot, int capacity, int bookedSeats, int availableSeats,
                  double price, String status) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.aircraft = aircraft;
        this.pilot = pilot;
        this.capacity = capacity;
        this.bookedSeats = bookedSeats;
        this.availableSeats = availableSeats;
        this.price = price;
        this.status = status;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getDuration() { return duration; }
    public String getAircraft() { return aircraft; }
    public String getPilot() { return pilot; }
    public int getCapacity() { return capacity; }
    public int getBookedSeats() { return bookedSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public void setSource(String source) { this.source = source; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setAircraft(String aircraft) { this.aircraft = aircraft; }
    public void setPilot(String pilot) { this.pilot = pilot; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setBookedSeats(int bookedSeats) { this.bookedSeats = bookedSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }

    public void reserveSeats(int count) {
        if (count <= 0 || count > availableSeats) {
            throw new IllegalArgumentException("The requested number of seats is not available.");
        }
        bookedSeats += count;
        availableSeats -= count;
    }

    public void releaseSeats(int count) {
        if (count <= 0 || count > bookedSeats) {
            throw new IllegalArgumentException("Cannot release the requested number of seats.");
        }
        bookedSeats -= count;
        availableSeats += count;
    }

    @Override
    public String toString() {
        return flightNumber + " - " + source + " to " + destination;
    }
}
