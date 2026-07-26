package airline.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JCheckBox showPasswordCheck = new JCheckBox("Show Password");
    private final JButton loginButton = new JButton("Login");
    private final JButton exitButton = new JButton("Exit");
    private final JLabel messageLabel = new JLabel();

    public LoginView() {
        setTitle("SkyLine Airways - Login");
        setSize(980, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setBackground(new Color(21, 101, 192));
        left.setLayout(new BorderLayout());
        left.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JLabel logo = new JLabel("✈ SkyLine Airways", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(Color.WHITE);
        left.add(logo, BorderLayout.NORTH);
        JLabel art = new JLabel("Flight Operations Dashboard", SwingConstants.CENTER);
        art.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        art.setForeground(new Color(233, 240, 255));
        left.add(art, BorderLayout.CENTER);

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(Color.WHITE);
        right.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        right.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        right.add(new JLabel("Username"), gbc);
        gbc.gridx = 1;
        right.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        right.add(new JLabel("Password"), gbc);
        gbc.gridx = 1;
        right.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        right.add(showPasswordCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setOpaque(false);
        buttons.add(loginButton);
        buttons.add(exitButton);
        right.add(buttons, gbc);

        gbc.gridy = 5;
        messageLabel.setForeground(new Color(21, 101, 192));
        right.add(messageLabel, gbc);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.CENTER);

        showPasswordCheck.addActionListener(e -> {
            JCheckBox c = (JCheckBox) e.getSource();
            passwordField.setEchoChar(c.isSelected() ? (char) 0 : '•');
        });

        setVisible(true);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }
}
