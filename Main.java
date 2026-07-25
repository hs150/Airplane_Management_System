import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }

    private static void createAndShowGui() {
        AirlineSystem system = new AirlineSystem();

        JFrame frame = new JFrame("Airline Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(560, 620);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(12, 12));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Book Your Flight");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        addField(formPanel, gbc, "Username", new JTextField(20), 1);
        addField(formPanel, gbc, "Password", new JPasswordField(20), 2);
        addField(formPanel, gbc, "Name", new JTextField(20), 3);
        addField(formPanel, gbc, "Email", new JTextField(20), 4);
        addField(formPanel, gbc, "Phone Number", new JTextField(20), 5);

        JLabel originLabel = new JLabel("Origin:");
        JComboBox<String> originBox = new JComboBox<>(system.getOrigins());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        formPanel.add(originLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(originBox, gbc);

        JLabel destinationLabel = new JLabel("Destination:");
        JComboBox<String> destinationBox = new JComboBox<>(system.getDestinations());
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(destinationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(destinationBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        JButton registerButton = new JButton("Register & Book");
        JButton loginButton = new JButton("Login");
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setBorder(BorderFactory.createTitledBorder("Booking Result"));

        registerButton.addActionListener(event -> {
            JTextField usernameField = (JTextField) formPanel.getComponent(3);
            JPasswordField passwordField = (JPasswordField) formPanel.getComponent(5);
            JTextField nameField = (JTextField) formPanel.getComponent(7);
            JTextField emailField = (JTextField) formPanel.getComponent(9);
            JTextField phoneField = (JTextField) formPanel.getComponent(11);

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phoneNumber = phoneField.getText().trim();
            String origin = (String) originBox.getSelectedItem();
            String destination = (String) destinationBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!system.isValidPhoneNumber(phoneNumber)) {
                JOptionPane.showMessageDialog(frame, "Phone number must be exactly 10 digits.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (origin.equals(destination)) {
                JOptionPane.showMessageDialog(frame, "Origin and destination cannot be the same.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                AirlineSystem.Customer customer = system.registerCustomer(username, password, name, email, phoneNumber);
                customer.setOrigin(origin);
                customer.setDestination(destination);
                customer.setPrice(system.getRandomPrice());
                resultArea.setText("Registration successful!\n\n" + customer.getSummary());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Registration Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        loginButton.addActionListener(event -> {
            JTextField usernameField = (JTextField) formPanel.getComponent(3);
            JPasswordField passwordField = (JPasswordField) formPanel.getComponent(5);
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String origin = (String) originBox.getSelectedItem();
            String destination = (String) destinationBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter username and password to login.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (origin.equals(destination)) {
                JOptionPane.showMessageDialog(frame, "Origin and destination cannot be the same.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                AirlineSystem.Customer customer = system.loginCustomer(username, password);
                customer.setOrigin(origin);
                customer.setDestination(destination);
                customer.setPrice(system.getRandomPrice());
                resultArea.setText("Login successful!\n\n" + customer.getSummary());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Login Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(resultScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent component, int row) {
        JLabel label = new JLabel(labelText + ":");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }
}

