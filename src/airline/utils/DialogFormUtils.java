package airline.utils;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** Small reusable Swing helpers used by modal data-entry forms. */
public final class DialogFormUtils {
    private DialogFormUtils() {
    }

    public static GridBagConstraints newConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return constraints;
    }

    public static void addRow(JPanel form, GridBagConstraints constraints, int row, String label, JComponent field) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.weightx = 0;
        form.add(new JLabel(label), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        form.add(field, constraints);
    }

    public static JPanel actions(JButton cancelButton, JButton saveButton) {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(cancelButton);
        actions.add(saveButton);
        return actions;
    }

    public static void showValidationError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Validation error", JOptionPane.ERROR_MESSAGE);
    }
}
