package airline.view;

import airline.model.Booking;
import airline.model.Flight;
import airline.model.Passenger;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardView extends JFrame {
    private final JLabel userLabel = new JLabel();
    private final JLabel flightsLabel = new JLabel();
    private final JLabel passengersLabel = new JLabel();
    private final JLabel bookingsLabel = new JLabel();
    private final JLabel delayedLabel = new JLabel();
    private final JTable passengerTable = new JTable();
    private final JTable flightTable = new JTable();
    private final JTable bookingTable = new JTable();
    private final JButton addPassengerButton = new JButton("Add Passenger");
    private final JButton addFlightButton = new JButton("Add Flight");
    private final JButton addBookingButton = new JButton("Create Booking");

    public DashboardView(String loggedUser) {
        setTitle("SkyLine Airways - Public Preview");
        setSize(1280, 780);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(21, 101, 192));
        top.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("SkyLine Airways Management Preview");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        top.add(title, BorderLayout.WEST);
        userLabel.setForeground(new Color(232, 244, 255));
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        top.add(userLabel, BorderLayout.EAST);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel summary = new JPanel(new GridLayout(1, 5, 10, 10));
        summary.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        summary.add(card("Flights", flightsLabel));
        summary.add(card("Passengers", passengersLabel));
        summary.add(card("Bookings", bookingsLabel));
        summary.add(card("Delayed", delayedLabel));
        summary.add(card("Status", new JLabel("Operational")));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(addPassengerButton);
        actions.add(addFlightButton);
        actions.add(addBookingButton);

        JPanel tables = new JPanel(new GridLayout(1, 3, 10, 10));
        tables.add(createTablePanel("Passengers", passengerTable));
        tables.add(createTablePanel("Flights", flightTable));
        tables.add(createTablePanel("Bookings", bookingTable));

        center.add(summary, BorderLayout.NORTH);
        center.add(actions, BorderLayout.CENTER);
        center.add(tables, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        setVisible(true);

        userLabel.setText("Public Preview • " + loggedUser + " • No login required");
        setSummaryValues("3", "2", "2", "1");
    }

    private JPanel card(String title, JLabel valueLabel) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 232), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(titleLabel);
        panel.add(valueLabel);
        return panel;
    }

    private JPanel createTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        table.setFillsViewportHeight(true);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    public void setSummaryValues(String flights, String passengers, String bookings, String delayed) {
        flightsLabel.setText(flights);
        passengersLabel.setText(passengers);
        bookingsLabel.setText(bookings);
        delayedLabel.setText(delayed);
    }

    public void refreshPassengerTable(List<Passenger> passengers) {
        String[] columns = {"ID", "Name", "Flight", "Seat"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Passenger p : passengers) {
            model.addRow(new Object[]{p.getPassengerId(), p.getFullName(), p.getFlightAssigned(), p.getSeatNumber()});
        }
        passengerTable.setModel(model);
    }

    public void refreshFlightTable(List<Flight> flights) {
        String[] columns = {"Flight", "Source", "Destination", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Flight f : flights) {
            model.addRow(new Object[]{f.getFlightNumber(), f.getSource(), f.getDestination(), f.getStatus()});
        }
        flightTable.setModel(model);
    }

    public void refreshBookingTable(List<Booking> bookings) {
        String[] columns = {"PNR", "Passenger", "Flight", "Total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Booking b : bookings) {
            model.addRow(new Object[]{b.getPnr(), b.getPassenger().getFullName(), b.getFlight().getFlightNumber(), b.getTotal()});
        }
        bookingTable.setModel(model);
    }

    public void addPassengerListener(ActionListener listener) {
        addPassengerButton.addActionListener(listener);
    }

    public void addFlightListener(ActionListener listener) {
        addFlightButton.addActionListener(listener);
    }

    public void addBookingListener(ActionListener listener) {
        addBookingButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
