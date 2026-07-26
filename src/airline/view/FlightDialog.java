package airline.view;

import airline.model.Flight;
import airline.utils.DialogFormUtils;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Modal form used for both adding and editing a flight. */
public class FlightDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JTextField sourceField = new JTextField(16);
    private final JTextField destinationField = new JTextField(16);
    private final JTextField departureField = new JTextField(16);
    private final JTextField arrivalField = new JTextField(16);
    private final JTextField durationField = new JTextField(16);
    private final JTextField aircraftField = new JTextField(16);
    private final JTextField pilotField = new JTextField(16);
    private final JTextField capacityField = new JTextField(16);
    private final JTextField bookedSeatsField = new JTextField(16);
    private final JTextField priceField = new JTextField(16);
    private final JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Scheduled", "On Time", "Delayed", "Cancelled"});
    private transient Flight result;

    private FlightDialog(Frame owner, Flight existingFlight) {
        super(owner, existingFlight == null ? "Add Flight" : "Edit Flight", true);
        add(createForm(), BorderLayout.CENTER);
        add(createActions(existingFlight), BorderLayout.SOUTH);
        if (existingFlight != null) {
            populate(existingFlight);
        }
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public static Flight showDialog(Frame owner, Flight existingFlight) {
        FlightDialog dialog = new FlightDialog(owner, existingFlight);
        dialog.setVisible(true);
        return dialog.result;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        GridBagConstraints constraints = DialogFormUtils.newConstraints();
        DialogFormUtils.addRow(form, constraints, 0, "Source *", sourceField);
        DialogFormUtils.addRow(form, constraints, 1, "Destination *", destinationField);
        DialogFormUtils.addRow(form, constraints, 2, "Departure", departureField);
        DialogFormUtils.addRow(form, constraints, 3, "Arrival", arrivalField);
        DialogFormUtils.addRow(form, constraints, 4, "Duration", durationField);
        DialogFormUtils.addRow(form, constraints, 5, "Aircraft", aircraftField);
        DialogFormUtils.addRow(form, constraints, 6, "Pilot", pilotField);
        DialogFormUtils.addRow(form, constraints, 7, "Capacity *", capacityField);
        DialogFormUtils.addRow(form, constraints, 8, "Booked seats", bookedSeatsField);
        DialogFormUtils.addRow(form, constraints, 9, "Price *", priceField);
        DialogFormUtils.addRow(form, constraints, 10, "Status", statusComboBox);
        return form;
    }

    private JPanel createActions(Flight existingFlight) {
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");
        cancelButton.addActionListener(event -> dispose());
        saveButton.addActionListener(event -> save(existingFlight == null ? null : existingFlight.getFlightNumber()));
        return DialogFormUtils.actions(cancelButton, saveButton);
    }

    private void populate(Flight flight) {
        sourceField.setText(flight.getSource());
        destinationField.setText(flight.getDestination());
        departureField.setText(flight.getDepartureTime());
        arrivalField.setText(flight.getArrivalTime());
        durationField.setText(flight.getDuration());
        aircraftField.setText(flight.getAircraft());
        pilotField.setText(flight.getPilot());
        capacityField.setText(String.valueOf(flight.getCapacity()));
        bookedSeatsField.setText(String.valueOf(flight.getBookedSeats()));
        priceField.setText(String.valueOf(flight.getPrice()));
        statusComboBox.setSelectedItem(flight.getStatus());
    }

    private void save(String flightNumber) {
        try {
            int capacity = Integer.parseInt(capacityField.getText().trim());
            int bookedSeats = Integer.parseInt(bookedSeatsField.getText().trim());
            if (bookedSeats > capacity) {
                throw new IllegalArgumentException("Booked seats cannot exceed capacity.");
            }
            result = new Flight(
                    flightNumber, sourceField.getText().trim(), destinationField.getText().trim(), departureField.getText().trim(),
                    arrivalField.getText().trim(), durationField.getText().trim(), aircraftField.getText().trim(), pilotField.getText().trim(),
                    capacity, bookedSeats, capacity - bookedSeats, Double.parseDouble(priceField.getText().trim()),
                    (String) statusComboBox.getSelectedItem());
            dispose();
        } catch (NumberFormatException exception) {
            DialogFormUtils.showValidationError(this, "Capacity, booked seats, and price must be numbers.");
        } catch (IllegalArgumentException exception) {
            DialogFormUtils.showValidationError(this, exception.getMessage());
        }
    }
}
