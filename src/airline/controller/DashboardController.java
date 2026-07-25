package airline.controller;

import airline.model.*;
import airline.service.AirlineService;
import airline.view.DashboardView;

public class DashboardController {
    private final AirlineService service;
    private final DashboardView view;

    public DashboardController(AirlineService service, DashboardView view) {
        this.service = service;
        this.view = view;
        init();
    }

    private void init() {
        view.setSummaryValues(
                String.valueOf(service.getFlights().size()),
                String.valueOf(service.getPassengers().size()),
                String.valueOf(service.getBookings().size()),
                "3"
        );

        view.addPassengerListener(e -> {
            Passenger p = new Passenger("P001", "John Doe", 32, "Male", "P12345", "Indian", "9876543210", "john@example.com", "Delhi", "AI101", "12A", "2026-07-26");
            service.addPassenger(p);
            view.showMessage("Passenger added successfully.");
            view.refreshPassengerTable(service.getPassengers());
        });

        view.addFlightListener(e -> {
            Flight f = new Flight("AI303", "Bangalore", "Chennai", "16:00", "18:20", "2h20m", "Airbus A321", "S. Kumar", 200, 70, 130, 8100, "Scheduled");
            service.addFlight(f);
            view.showMessage("Flight added successfully.");
            view.refreshFlightTable(service.getFlights());
        });

        view.addBookingListener(e -> {
            Passenger passenger = service.getPassengers().isEmpty() ? null : service.getPassengers().get(0);
            Flight flight = service.getFlights().isEmpty() ? null : service.getFlights().get(0);
            if (passenger != null && flight != null) {
                Booking booking = service.createBooking(passenger, flight, "Business", 1, "Window");
                view.showMessage("Booking created: " + booking.getPnr());
                view.refreshBookingTable(service.getBookings());
            }
        });
    }
}
