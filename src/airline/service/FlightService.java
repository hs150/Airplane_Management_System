package airline.service;

import airline.model.Flight;
import airline.utils.IdentifierGenerator;
import airline.utils.SearchUtils;
import java.util.ArrayList;
import java.util.List;

/** Owns flight validation, generated flight numbers, and in-memory CRUD operations. */
public class FlightService {
    private final List<Flight> flights;

    public FlightService(List<Flight> seedFlights) {
        flights = new ArrayList<>(seedFlights);
    }

    public List<Flight> getAll() {
        return List.copyOf(flights);
    }

    public Flight find(String flightNumber) {
        return flights.stream()
                .filter(flight -> flight.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public List<Flight> search(String query) {
        return flights.stream()
                .filter(flight -> matches(flight, query))
                .toList();
    }

    public Flight save(Flight flight) {
        validate(flight);
        if (isNew(flight)) {
            flight.setFlightNumber(nextNumber());
            flights.add(flight);
            return flight;
        }
        return replace(flight);
    }

    public void delete(String flightNumber) {
        if (!flights.removeIf(flight -> flight.getFlightNumber().equals(flightNumber))) {
            throw new IllegalArgumentException("Flight not found.");
        }
    }

    private boolean matches(Flight flight, String query) {
        return SearchUtils.contains(flight.getFlightNumber(), query)
                || SearchUtils.contains(flight.getSource(), query)
                || SearchUtils.contains(flight.getDestination(), query)
                || SearchUtils.contains(flight.getStatus(), query);
    }

    private boolean isNew(Flight flight) {
        return flight.getFlightNumber() == null || flight.getFlightNumber().isBlank();
    }

    private Flight replace(Flight updatedFlight) {
        for (int index = 0; index < flights.size(); index++) {
            if (flights.get(index).getFlightNumber().equals(updatedFlight.getFlightNumber())) {
                flights.set(index, updatedFlight);
                return updatedFlight;
            }
        }
        throw new IllegalArgumentException("Flight does not exist.");
    }

    private void validate(Flight flight) {
        if (flight.getSource() == null || flight.getSource().isBlank()
                || flight.getDestination() == null || flight.getDestination().isBlank()) {
            throw new IllegalArgumentException("Source and destination are required.");
        }
        if (flight.getSource().equalsIgnoreCase(flight.getDestination())) {
            throw new IllegalArgumentException("Source and destination must be different.");
        }
        if (flight.getCapacity() <= 0 || flight.getBookedSeats() < 0 || flight.getAvailableSeats() < 0
                || flight.getBookedSeats() + flight.getAvailableSeats() != flight.getCapacity()) {
            throw new IllegalArgumentException("Capacity must equal booked plus available seats.");
        }
        if (flight.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
    }

    private String nextNumber() {
        return IdentifierGenerator.next("AI", flights.stream().map(Flight::getFlightNumber).toList(), 101, 3);
    }
}
