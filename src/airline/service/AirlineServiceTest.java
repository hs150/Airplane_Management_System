package airline.service;

import airline.model.Booking;
import airline.model.Flight;
import airline.model.Passenger;

/** Lightweight executable regression test; migrate to JUnit when a build tool is added. */
public class AirlineServiceTest {
    public static void main(String[] args) {
        AirlineService service = new AirlineService();
        assertTrue(service.login("admin", "admin123"), "admin credentials should work");
        Passenger added = service.savePassenger(new Passenger(null, "Test Passenger", 25, "Other", "", "Indian", "9000000000", "test@example.com", "", "", "", ""));
        assertTrue(added.getPassengerId().matches("P\\d{3}"), "passenger ID should be generated");
        Flight flight = service.getFlights().getFirst();
        int availableBefore = flight.getAvailableSeats();
        Booking booking = service.createBooking(added.getPassengerId(), flight.getFlightNumber(), "Economy", 1, "Aisle");
        assertTrue(flight.getAvailableSeats() == availableBefore - 1, "booking should reserve a seat");
        service.cancelBooking(booking.getPnr());
        assertTrue(booking.isCancelled() && flight.getAvailableSeats() == availableBefore, "cancellation should release the seat");
        try {
            service.savePassenger(new Passenger(null, "Bad Email", 25, "Other", "", "Indian", "9000000000", "invalid", "", "", "", ""));
            throw new AssertionError("invalid email should fail validation");
        } catch (IllegalArgumentException expected) { }
        System.out.println("AirlineService tests passed");
    }
    private static void assertTrue(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
