package airline.view;

import airline.model.Passenger;
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

/** Modal form used for both adding and editing a passenger. */
public class PassengerDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JTextField nameField = new JTextField(18);
    private final JTextField ageField = new JTextField(18);
    private final JTextField emailField = new JTextField(18);
    private final JTextField phoneField = new JTextField(18);
    private final JTextField passportField = new JTextField(18);
    private final JTextField nationalityField = new JTextField(18);
    private final JTextField addressField = new JTextField(18);
    private final JTextField assignedFlightField = new JTextField(18);
    private final JTextField seatField = new JTextField(18);
    private final JTextField bookingDateField = new JTextField(18);
    private final JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other", "Prefer not to say"});
    private transient Passenger result;

    private PassengerDialog(Frame owner, Passenger existingPassenger) {
        super(owner, existingPassenger == null ? "Add Passenger" : "Edit Passenger", true);
        add(createForm(), BorderLayout.CENTER);
        add(createActions(existingPassenger), BorderLayout.SOUTH);
        if (existingPassenger != null) {
            populate(existingPassenger);
        }
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public static Passenger showDialog(Frame owner, Passenger existingPassenger) {
        PassengerDialog dialog = new PassengerDialog(owner, existingPassenger);
        dialog.setVisible(true);
        return dialog.result;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        GridBagConstraints constraints = DialogFormUtils.newConstraints();
        DialogFormUtils.addRow(form, constraints, 0, "Full name *", nameField);
        DialogFormUtils.addRow(form, constraints, 1, "Age *", ageField);
        DialogFormUtils.addRow(form, constraints, 2, "Email *", emailField);
        DialogFormUtils.addRow(form, constraints, 3, "Phone (10 digits) *", phoneField);
        DialogFormUtils.addRow(form, constraints, 4, "Gender", genderComboBox);
        DialogFormUtils.addRow(form, constraints, 5, "Passport", passportField);
        DialogFormUtils.addRow(form, constraints, 6, "Nationality", nationalityField);
        DialogFormUtils.addRow(form, constraints, 7, "Address", addressField);
        DialogFormUtils.addRow(form, constraints, 8, "Assigned flight", assignedFlightField);
        DialogFormUtils.addRow(form, constraints, 9, "Seat", seatField);
        DialogFormUtils.addRow(form, constraints, 10, "Booking date", bookingDateField);
        return form;
    }

    private JPanel createActions(Passenger existingPassenger) {
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");
        cancelButton.addActionListener(event -> dispose());
        saveButton.addActionListener(event -> save(existingPassenger == null ? null : existingPassenger.getPassengerId()));
        return DialogFormUtils.actions(cancelButton, saveButton);
    }

    private void populate(Passenger passenger) {
        nameField.setText(passenger.getFullName());
        ageField.setText(String.valueOf(passenger.getAge()));
        emailField.setText(passenger.getEmail());
        phoneField.setText(passenger.getPhone());
        genderComboBox.setSelectedItem(passenger.getGender());
        passportField.setText(passenger.getPassportNumber());
        nationalityField.setText(passenger.getNationality());
        addressField.setText(passenger.getAddress());
        assignedFlightField.setText(passenger.getFlightAssigned());
        seatField.setText(passenger.getSeatNumber());
        bookingDateField.setText(passenger.getBookingDate());
    }

    private void save(String passengerId) {
        try {
            result = new Passenger(
                    passengerId, nameField.getText().trim(), Integer.parseInt(ageField.getText().trim()),
                    (String) genderComboBox.getSelectedItem(), passportField.getText().trim(), nationalityField.getText().trim(),
                    phoneField.getText().trim(), emailField.getText().trim(), addressField.getText().trim(),
                    assignedFlightField.getText().trim(), seatField.getText().trim(), bookingDateField.getText().trim());
            dispose();
        } catch (NumberFormatException exception) {
            DialogFormUtils.showValidationError(this, "Age must be a whole number.");
        }
    }
}
