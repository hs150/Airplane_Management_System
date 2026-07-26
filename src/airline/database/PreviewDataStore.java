package airline.database;

import airline.model.Booking;
import airline.model.Flight;
import airline.model.Passenger;
import java.util.ArrayList;
import java.util.List;

public class PreviewDataStore {
    private final List<Flight> flights = new ArrayList<>();
    private final List<Passenger> passengers = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public PreviewDataStore() {
        flights.add(new Flight("AI101", "Mumbai", "Delhi", "08:30", "10:45", "2h15m", "Boeing 737", "A. Sharma", 180, 45, 135, 6200, "On Time"));
        flights.add(new Flight("AI202", "Delhi", "Kolkata", "11:45", "14:10", "2h25m", "Airbus A320", "R. Mehta", 160, 80, 80, 7400, "Scheduled"));
        flights.add(new Flight("AI303", "Kolkata", "Bangalore", "15:20", "17:40", "2h20m", "Airbus A321", "S. Kumar", 200, 70, 130, 8100, "Delayed"));

        passengers.add(new Passenger("P001", "Aarav Patel", 32, "Male", "P12345", "Indian", "9876543210", "aarav@example.com", "Mumbai", "AI101", "12A", "2026-07-26"));
        passengers.add(new Passenger("P002", "Meera Rao", 28, "Female", "P67890", "Indian", "9123456780", "meera@example.com", "Delhi", "AI202", "08C", "2026-07-27"));

        bookings.add(new Booking("PNR1001", passengers.get(0), flights.get(0), "Business", 1, "Window", 12450, "Paid"));
        bookings.add(new Booking("PNR1002", passengers.get(1), flights.get(1), "Economy", 2, "Aisle", 14800, "Pending"));
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
