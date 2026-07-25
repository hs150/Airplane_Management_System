package airline.service;

import airline.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirlineService {
    private final Map<String, UserAccount> users = new HashMap<>();
    private final List<Passenger> passengers = new ArrayList<>();
    private final List<Flight> flights = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public AirlineService() {
        users.put("admin", new UserAccount("admin", "admin123", "Admin User"));
        flights.add(new Flight("AI101", "Mumbai", "Delhi", "08:30", "10:45", "2h15m", "Boeing 737", "A. Sharma", 180, 45, 135, 6200, "On Time"));
        flights.add(new Flight("AI202", "Delhi", "Kolkata", "11:45", "14:10", "2h25m", "Airbus A320", "R. Mehta", 160, 80, 80, 7400, "Scheduled"));
    }

    public boolean login(String username, String password) {
        UserAccount user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public UserAccount getUser(String username) {
        return users.get(username);
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public void updatePassenger(Passenger passenger) {
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getPassengerId().equals(passenger.getPassengerId())) {
                passengers.set(i, passenger);
                return;
            }
        }
    }

    public void deletePassenger(String passengerId) {
        passengers.removeIf(p -> p.getPassengerId().equals(passengerId));
    }

    public Passenger findPassenger(String passengerId) {
        return passengers.stream().filter(p -> p.getPassengerId().equals(passengerId)).findFirst().orElse(null);
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void updateFlight(Flight flight) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber().equals(flight.getFlightNumber())) {
                flights.set(i, flight);
                return;
            }
        }
    }

    public void deleteFlight(String flightNumber) {
        flights.removeIf(f -> f.getFlightNumber().equals(flightNumber));
    }

    public Flight findFlight(String flightNumber) {
        return flights.stream().filter(f -> f.getFlightNumber().equals(flightNumber)).findFirst().orElse(null);
    }

    public Booking createBooking(Passenger passenger, Flight flight, String seatClass, int passengersCount, String seatPreference) {
        double total = flight.getPrice() * passengersCount;
        if (seatClass.equalsIgnoreCase("Business")) total *= 1.35;
        if (seatPreference.equalsIgnoreCase("Window")) total += 300;

        Booking booking = new Booking("PNR" + (1000 + bookings.size()), passenger, flight, seatClass, passengersCount, seatPreference, total, "Paid");
        bookings.add(booking);
        return booking;
    }
}
