package airline.view;

import airline.model.Flight;
import airline.model.Passenger;
import airline.utils.DialogFormUtils;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Collects booking input; booking rules remain in {@code BookingService}. */
public class BookingDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public record Request(String passengerId, String flightNumber, String seatClass, int passengerCount, String seatPreference) {
    }

    private final JComboBox<Passenger> passengerComboBox;
    private final JComboBox<Flight> flightComboBox;
    private final JComboBox<String> seatClassComboBox = new JComboBox<>(new String[]{"Economy", "Business"});
    private final JTextField passengerCountField = new JTextField("1", 8);
    private final JComboBox<String> preferenceComboBox = new JComboBox<>(new String[]{"No preference", "Window", "Aisle"});
    private transient Request result;

    private BookingDialog(Frame owner, List<Passenger> passengers, List<Flight> flights) {
        super(owner, "Create Booking", true);
        passengerComboBox = new JComboBox<>(passengers.toArray(Passenger[]::new));
        flightComboBox = new JComboBox<>(flights.toArray(Flight[]::new));
        add(createForm(), BorderLayout.CENTER);
        add(createActions(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    public static Request showDialog(Frame owner, List<Passenger> passengers, List<Flight> flights) {
        if (passengers.isEmpty() || flights.isEmpty()) {
            JOptionPane.showMessageDialog(owner, "Add at least one passenger and one flight before creating a booking.",
                    "Booking unavailable", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        BookingDialog dialog = new BookingDialog(owner, passengers, flights);
        dialog.setVisible(true);
        return dialog.result;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        addRow(form, "Passenger *", passengerComboBox);
        addRow(form, "Flight *", flightComboBox);
        addRow(form, "Class", seatClassComboBox);
        addRow(form, "Seats *", passengerCountField);
        addRow(form, "Preference", preferenceComboBox);
        return form;
    }

    private void addRow(JPanel form, String label, java.awt.Component component) {
        form.add(new JLabel(label));
        form.add(component);
    }

    private JPanel createActions() {
        JButton cancelButton = new JButton("Cancel");
        JButton createButton = new JButton("Create");
        cancelButton.addActionListener(event -> dispose());
        createButton.addActionListener(event -> save());
        return DialogFormUtils.actions(cancelButton, createButton);
    }

    private void save() {
        try {
            int passengerCount = Integer.parseInt(passengerCountField.getText().trim());
            if (passengerCount <= 0) {
                throw new NumberFormatException();
            }
            Passenger passenger = (Passenger) passengerComboBox.getSelectedItem();
            Flight flight = (Flight) flightComboBox.getSelectedItem();
            result = new Request(passenger.getPassengerId(), flight.getFlightNumber(),
                    (String) seatClassComboBox.getSelectedItem(), passengerCount,
                    (String) preferenceComboBox.getSelectedItem());
            dispose();
        } catch (NumberFormatException exception) {
            DialogFormUtils.showValidationError(this, "Seats must be a positive whole number.");
        }
    }
}
