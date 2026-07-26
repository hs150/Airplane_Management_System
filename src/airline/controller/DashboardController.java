package airline.controller;

import airline.model.Flight;
import airline.model.Passenger;
import airline.service.AirlineService;
import airline.view.BookingDialog;
import airline.view.DashboardView;
import airline.view.FlightDialog;
import airline.view.PassengerDialog;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/** Connects dashboard UI events to the application service and refreshes displayed data. */
public class DashboardController {
    private final AirlineService service;
    private final DashboardView view;

    public DashboardController(AirlineService service, DashboardView view) {
        this.service = service;
        this.view = view;
        registerListeners();
        refreshDashboard();
    }

    private void registerListeners() {
        view.addPassengerListener(event -> savePassenger(null));
        view.editPassengerListener(event -> editSelectedPassenger());
        view.deletePassengerListener(event -> deleteSelectedPassenger());
        view.addFlightListener(event -> saveFlight(null));
        view.editFlightListener(event -> editSelectedFlight());
        view.deleteFlightListener(event -> deleteSelectedFlight());
        view.addBookingListener(event -> createBooking());
        view.cancelBookingListener(event -> cancelSelectedBooking());
        registerSearchListeners();
    }

    private void registerSearchListeners() {
        view.passengerSearchListener(onChange(this::refreshPassengerTable));
        view.flightSearchListener(onChange(this::refreshFlightTable));
        view.bookingSearchListener(onChange(this::refreshBookingTable));
    }

    private void editSelectedPassenger() {
        String passengerId = view.selectedPassengerId();
        if (passengerId == null) {
            view.showError("Select a passenger to edit.");
            return;
        }
        savePassenger(service.findPassenger(passengerId));
    }

    private void savePassenger(Passenger existingPassenger) {
        Passenger passenger = PassengerDialog.showDialog(view, existingPassenger);
        if (passenger == null) {
            return;
        }
        try {
            service.savePassenger(passenger);
            refreshDashboard();
            view.showMessage("Passenger " + actionName(existingPassenger == null) + " successfully.");
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void deleteSelectedPassenger() {
        String passengerId = view.selectedPassengerId();
        if (passengerId == null) {
            view.showError("Select a passenger to delete.");
            return;
        }
        if (!view.confirm("Delete passenger " + passengerId + "?")) {
            return;
        }
        try {
            service.deletePassenger(passengerId);
            refreshDashboard();
            view.showMessage("Passenger deleted.");
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void editSelectedFlight() {
        String flightNumber = view.selectedFlightNumber();
        if (flightNumber == null) {
            view.showError("Select a flight to edit.");
            return;
        }
        saveFlight(service.findFlight(flightNumber));
    }

    private void saveFlight(Flight existingFlight) {
        Flight flight = FlightDialog.showDialog(view, existingFlight);
        if (flight == null) {
            return;
        }
        try {
            service.saveFlight(flight);
            refreshDashboard();
            view.showMessage("Flight " + actionName(existingFlight == null) + " successfully.");
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void deleteSelectedFlight() {
        String flightNumber = view.selectedFlightNumber();
        if (flightNumber == null) {
            view.showError("Select a flight to delete.");
            return;
        }
        if (!view.confirm("Delete flight " + flightNumber + "?")) {
            return;
        }
        try {
            service.deleteFlight(flightNumber);
            refreshDashboard();
            view.showMessage("Flight deleted.");
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void createBooking() {
        BookingDialog.Request request = BookingDialog.showDialog(view, service.getPassengers(), service.getFlights());
        if (request == null) {
            return;
        }
        try {
            String pnr = service.createBooking(request.passengerId(), request.flightNumber(), request.seatClass(),
                    request.passengerCount(), request.seatPreference()).getPnr();
            refreshDashboard();
            view.showMessage("Booking " + pnr + " created successfully.");
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void cancelSelectedBooking() {
        String pnr = view.selectedBookingPnr();
        if (pnr == null) {
            view.showError("Select a booking to cancel.");
            return;
        }
        if (!view.confirm("Cancel booking " + pnr + "?")) {
            return;
        }
        try {
            service.cancelBooking(pnr);
            refreshDashboard();
            view.showMessage("Booking cancelled and seats released.");
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void refreshDashboard() {
        view.setSummaryValues(
                String.valueOf(service.getFlights().size()),
                String.valueOf(service.getPassengers().size()),
                String.valueOf(service.getBookings().size()),
                String.valueOf(service.getFlights().stream().filter(flight -> "Delayed".equalsIgnoreCase(flight.getStatus())).count()));
        refreshPassengerTable();
        refreshFlightTable();
        refreshBookingTable();
    }

    private void refreshPassengerTable() {
        view.refreshPassengerTable(service.searchPassengers(view.passengerQuery()));
    }

    private void refreshFlightTable() {
        view.refreshFlightTable(service.searchFlights(view.flightQuery()));
    }

    private void refreshBookingTable() {
        view.refreshBookingTable(service.searchBookings(view.bookingQuery()));
    }

    private String actionName(boolean isNew) {
        return isNew ? "added" : "updated";
    }

    private DocumentListener onChange(Runnable action) {
        return new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent event) { action.run(); }
            @Override public void removeUpdate(DocumentEvent event) { action.run(); }
            @Override public void changedUpdate(DocumentEvent event) { action.run(); }
        };
    }
}
