package airline.service;

import airline.model.Passenger;
import airline.utils.IdentifierGenerator;
import airline.utils.SearchUtils;
import java.util.ArrayList;
import java.util.List;

/** Owns passenger validation, generated IDs, and in-memory CRUD operations. */
public class PassengerService {
    private final List<Passenger> passengers;

    public PassengerService(List<Passenger> seedPassengers) {
        passengers = new ArrayList<>(seedPassengers);
    }

    public List<Passenger> getAll() {
        return List.copyOf(passengers);
    }

    public Passenger find(String passengerId) {
        return passengers.stream()
                .filter(passenger -> passenger.getPassengerId().equals(passengerId))
                .findFirst()
                .orElse(null);
    }

    public List<Passenger> search(String query) {
        return passengers.stream()
                .filter(passenger -> matches(passenger, query))
                .toList();
    }

    public Passenger save(Passenger passenger) {
        validate(passenger);
        if (isNew(passenger)) {
            passenger.setPassengerId(nextId());
            passengers.add(passenger);
            return passenger;
        }
        return replace(passenger);
    }

    public void delete(String passengerId) {
        if (!passengers.removeIf(passenger -> passenger.getPassengerId().equals(passengerId))) {
            throw new IllegalArgumentException("Passenger not found.");
        }
    }

    private boolean matches(Passenger passenger, String query) {
        return SearchUtils.contains(passenger.getPassengerId(), query)
                || SearchUtils.contains(passenger.getFullName(), query)
                || SearchUtils.contains(passenger.getEmail(), query)
                || SearchUtils.contains(passenger.getPhone(), query);
    }

    private boolean isNew(Passenger passenger) {
        return passenger.getPassengerId() == null || passenger.getPassengerId().isBlank();
    }

    private Passenger replace(Passenger updatedPassenger) {
        for (int index = 0; index < passengers.size(); index++) {
            if (passengers.get(index).getPassengerId().equals(updatedPassenger.getPassengerId())) {
                passengers.set(index, updatedPassenger);
                return updatedPassenger;
            }
        }
        throw new IllegalArgumentException("Passenger does not exist.");
    }

    private void validate(Passenger passenger) {
        if (passenger.getFullName() == null || passenger.getFullName().isBlank()) {
            throw new IllegalArgumentException("Passenger name is required.");
        }
        if (passenger.getAge() <= 0 || passenger.getAge() > 120) {
            throw new IllegalArgumentException("Enter a valid age.");
        }
        if (passenger.getEmail() == null || !passenger.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        if (passenger.getPhone() == null || !passenger.getPhone().matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        }
    }

    private String nextId() {
        return IdentifierGenerator.next("P", passengers.stream().map(Passenger::getPassengerId).toList(), 1, 3);
    }
}
