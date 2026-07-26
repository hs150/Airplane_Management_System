package airline.service;

import airline.database.PreviewDataStore;
import airline.model.Booking;
import airline.model.Flight;
import airline.model.Passenger;
import airline.model.UserAccount;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Application facade. Authentication stays here; each business area has its own service. */
public class AirlineService {
    private final Map<String, UserAccount> users = new HashMap<>();
    private final PassengerService passengerService;
    private final FlightService flightService;
    private final BookingService bookingService;

    public AirlineService() {
        users.put("admin", new UserAccount("admin", "admin123", "Admin User"));
        PreviewDataStore store = new PreviewDataStore();
        passengerService = new PassengerService(store.getPassengers());
        flightService = new FlightService(store.getFlights());
        bookingService = new BookingService(store.getBookings(), passengerService, flightService);
    }

    public boolean login(String username, String password) {
        UserAccount user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public UserAccount getUser(String username) { return users.get(username); }
    public List<Passenger> getPassengers() { return passengerService.getAll(); }
    public List<Flight> getFlights() { return flightService.getAll(); }
    public List<Booking> getBookings() { return bookingService.getAll(); }
    public Passenger findPassenger(String id) { return passengerService.find(id); }
    public Flight findFlight(String number) { return flightService.find(number); }
    public List<Passenger> searchPassengers(String query) { return passengerService.search(query); }
    public List<Flight> searchFlights(String query) { return flightService.search(query); }
    public List<Booking> searchBookings(String query) { return bookingService.search(query); }
    public Passenger savePassenger(Passenger passenger) { return passengerService.save(passenger); }
    public Flight saveFlight(Flight flight) { return flightService.save(flight); }
    public void deletePassenger(String id) {
        if (bookingService.hasActiveBookingForPassenger(id)) throw new IllegalArgumentException("Cancel this passenger's active bookings first.");
        passengerService.delete(id);
    }
    public void deleteFlight(String number) {
        if (bookingService.hasActiveBookingForFlight(number)) throw new IllegalArgumentException("Cancel active bookings before deleting this flight.");
        flightService.delete(number);
    }
    public Booking createBooking(String passengerId, String flightNumber, String seatClass, int count, String preference) {
        return bookingService.create(passengerId, flightNumber, seatClass, count, preference);
    }
    public void cancelBooking(String pnr) { bookingService.cancel(pnr); }
}
