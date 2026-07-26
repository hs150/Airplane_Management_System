package airline.service;

import airline.model.Booking;
import airline.model.Flight;
import airline.model.Passenger;
import airline.utils.IdentifierGenerator;
import airline.utils.SearchUtils;
import java.util.ArrayList;
import java.util.List;

/** Coordinates bookings with passenger and flight services, including seat reservation/cancellation. */
public class BookingService {
    private static final double BUSINESS_CLASS_MULTIPLIER = 1.35;
    private static final double WINDOW_SEAT_FEE = 300;

    private final List<Booking> bookings;
    private final PassengerService passengerService;
    private final FlightService flightService;

    public BookingService(List<Booking> seedBookings, PassengerService passengerService, FlightService flightService) {
        bookings = new ArrayList<>(seedBookings);
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    public List<Booking> getAll() {
        return List.copyOf(bookings);
    }

    public List<Booking> search(String query) {
        return bookings.stream()
                .filter(booking -> matches(booking, query))
                .toList();
    }

    public Booking create(String passengerId, String flightNumber, String seatClass, int passengerCount, String seatPreference) {
        Passenger passenger = requirePassenger(passengerId);
        Flight flight = requireBookableFlight(flightNumber, passengerCount);
        double total = calculateTotal(flight, seatClass, passengerCount, seatPreference);

        flight.reserveSeats(passengerCount);
        Booking booking = new Booking(nextPnr(), passenger, flight, seatClass, passengerCount, seatPreference, total, "Pending");
        bookings.add(booking);
        return booking;
    }

    public void cancel(String pnr) {
        Booking booking = findBooking(pnr);
        if (booking.isCancelled()) {
            throw new IllegalArgumentException("This booking is already cancelled.");
        }
        booking.getFlight().releaseSeats(booking.getPassengers());
        booking.cancel();
    }

    public boolean hasActiveBookingForPassenger(String passengerId) {
        return bookings.stream().anyMatch(booking -> !booking.isCancelled()
                && booking.getPassenger().getPassengerId().equals(passengerId));
    }

    public boolean hasActiveBookingForFlight(String flightNumber) {
        return bookings.stream().anyMatch(booking -> !booking.isCancelled()
                && booking.getFlight().getFlightNumber().equals(flightNumber));
    }

    private boolean matches(Booking booking, String query) {
        return SearchUtils.contains(booking.getPnr(), query)
                || SearchUtils.contains(booking.getPassenger().getFullName(), query)
                || SearchUtils.contains(booking.getFlight().getFlightNumber(), query)
                || SearchUtils.contains(booking.getStatus(), query);
    }

    private Passenger requirePassenger(String passengerId) {
        Passenger passenger = passengerService.find(passengerId);
        if (passenger == null) {
            throw new IllegalArgumentException("Select an existing passenger.");
        }
        return passenger;
    }

    private Flight requireBookableFlight(String flightNumber, int passengerCount) {
        Flight flight = flightService.find(flightNumber);
        if (flight == null) {
            throw new IllegalArgumentException("Select an existing flight.");
        }
        if ("Cancelled".equalsIgnoreCase(flight.getStatus())) {
            throw new IllegalArgumentException("Bookings are not allowed on a cancelled flight.");
        }
        if (passengerCount <= 0 || passengerCount > flight.getAvailableSeats()) {
            throw new IllegalArgumentException("The flight does not have enough available seats.");
        }
        return flight;
    }

    private double calculateTotal(Flight flight, String seatClass, int passengerCount, String seatPreference) {
        double total = flight.getPrice() * passengerCount;
        if ("Business".equalsIgnoreCase(seatClass)) {
            total *= BUSINESS_CLASS_MULTIPLIER;
        }
        if ("Window".equalsIgnoreCase(seatPreference)) {
            total += WINDOW_SEAT_FEE;
        }
        return total;
    }

    private Booking findBooking(String pnr) {
        return bookings.stream()
                .filter(booking -> booking.getPnr().equals(pnr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));
    }

    private String nextPnr() {
        return IdentifierGenerator.next("PNR", bookings.stream().map(Booking::getPnr).toList(), 1001, 4);
    }
}
