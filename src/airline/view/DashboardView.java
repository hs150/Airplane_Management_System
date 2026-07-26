package airline.view;

import airline.model.Booking;
import airline.model.Flight;
import airline.model.Passenger;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class DashboardView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JLabel flightsLabel = new JLabel();
    private final JLabel passengersLabel = new JLabel();
    private final JLabel bookingsLabel = new JLabel();
    private final JLabel delayedLabel = new JLabel();
    private final JTable passengerTable = new JTable();
    private final JTable flightTable = new JTable();
    private final JTable bookingTable = new JTable();
    private final JButton addPassengerButton = new JButton("Add");
    private final JButton editPassengerButton = new JButton("Edit");
    private final JButton deletePassengerButton = new JButton("Delete");
    private final JButton addFlightButton = new JButton("Add");
    private final JButton editFlightButton = new JButton("Edit");
    private final JButton deleteFlightButton = new JButton("Delete");
    private final JButton addBookingButton = new JButton("Create booking");
    private final JButton cancelBookingButton = new JButton("Cancel selected");
    private final JTextField passengerSearch = new JTextField(12);
    private final JTextField flightSearch = new JTextField(12);
    private final JTextField bookingSearch = new JTextField(12);

    public DashboardView(String loggedUser) {
        setTitle("SkyLine Airways - Management");
        setSize(1280, 760); setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(EXIT_ON_CLOSE); setLocationRelativeTo(null); setLayout(new BorderLayout(12, 12));
        add(header(loggedUser), BorderLayout.NORTH);
        JPanel content = new JPanel(new BorderLayout(10, 10)); content.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));
        JPanel summary = new JPanel(new GridLayout(1, 4, 10, 10));
        summary.add(card("Flights", flightsLabel)); summary.add(card("Passengers", passengersLabel)); summary.add(card("Bookings", bookingsLabel)); summary.add(card("Delayed", delayedLabel));
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Passengers", modulePanel(passengerTable, passengerSearch, addPassengerButton, editPassengerButton, deletePassengerButton));
        tabs.addTab("Flights", modulePanel(flightTable, flightSearch, addFlightButton, editFlightButton, deleteFlightButton));
        tabs.addTab("Bookings", modulePanel(bookingTable, bookingSearch, addBookingButton, cancelBookingButton));
        content.add(summary, BorderLayout.NORTH); content.add(tabs, BorderLayout.CENTER); add(content, BorderLayout.CENTER);
        setVisible(true);
    }
    private JPanel header(String user) { JPanel top = new JPanel(new BorderLayout()); top.setBackground(new Color(21, 101, 192)); top.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20)); JLabel title = new JLabel("SkyLine Airways Management"); title.setForeground(Color.WHITE); title.setFont(new Font("Segoe UI", Font.BOLD, 20)); JLabel account = new JLabel("Signed in as " + user); account.setForeground(new Color(232, 244, 255)); top.add(title, BorderLayout.WEST); top.add(account, BorderLayout.EAST); return top; }
    private JPanel card(String title, JLabel value) { JPanel panel = new JPanel(); panel.setBackground(Color.WHITE); panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220,224,232)), BorderFactory.createEmptyBorder(12,12,12,12))); panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); JLabel label = new JLabel(title); label.setFont(new Font("Segoe UI", Font.BOLD, 13)); value.setFont(new Font("Segoe UI", Font.BOLD, 22)); panel.add(label); panel.add(value); return panel; }
    private JPanel modulePanel(JTable table, JTextField search, JButton... buttons) { JPanel panel = new JPanel(new BorderLayout(8, 8)); panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8)); JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT)); toolbar.add(new JLabel("Search:")); toolbar.add(search); for (JButton button : buttons) toolbar.add(button); table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); table.setAutoCreateRowSorter(true); table.setFillsViewportHeight(true); panel.add(toolbar, BorderLayout.NORTH); panel.add(new JScrollPane(table), BorderLayout.CENTER); return panel; }
    public void setSummaryValues(String flights, String passengers, String bookings, String delayed) { flightsLabel.setText(flights); passengersLabel.setText(passengers); bookingsLabel.setText(bookings); delayedLabel.setText(delayed); }
    public void refreshPassengerTable(List<Passenger> list) { DefaultTableModel model = nonEditable("ID", "Name", "Age", "Email", "Phone", "Flight", "Seat"); for (Passenger p : list) model.addRow(new Object[]{p.getPassengerId(),p.getFullName(),p.getAge(),p.getEmail(),p.getPhone(),p.getFlightAssigned(),p.getSeatNumber()}); passengerTable.setModel(model); }
    public void refreshFlightTable(List<Flight> list) { DefaultTableModel model = nonEditable("Flight", "Source", "Destination", "Departure", "Capacity", "Available", "Price", "Status"); for (Flight f : list) model.addRow(new Object[]{f.getFlightNumber(),f.getSource(),f.getDestination(),f.getDepartureTime(),f.getCapacity(),f.getAvailableSeats(),f.getPrice(),f.getStatus()}); flightTable.setModel(model); }
    public void refreshBookingTable(List<Booking> list) { DefaultTableModel model = nonEditable("PNR", "Passenger", "Flight", "Class", "Seats", "Total", "Status"); for (Booking b : list) model.addRow(new Object[]{b.getPnr(),b.getPassenger().getFullName(),b.getFlight().getFlightNumber(),b.getSeatClass(),b.getPassengers(),b.getTotal(),b.getStatus()}); bookingTable.setModel(model); }
    private DefaultTableModel nonEditable(String... columns) { return new DefaultTableModel(columns, 0) { @Override public boolean isCellEditable(int row, int column) { return false; } }; }
    public String selectedPassengerId() { return selectedValue(passengerTable); } public String selectedFlightNumber() { return selectedValue(flightTable); } public String selectedBookingPnr() { return selectedValue(bookingTable); }
    private String selectedValue(JTable table) { int row = table.getSelectedRow(); return row < 0 ? null : String.valueOf(table.getValueAt(row, 0)); }
    public boolean confirm(String message) { return JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION; }
    public void addPassengerListener(ActionListener l) { addPassengerButton.addActionListener(l); } public void editPassengerListener(ActionListener l) { editPassengerButton.addActionListener(l); } public void deletePassengerListener(ActionListener l) { deletePassengerButton.addActionListener(l); }
    public void addFlightListener(ActionListener l) { addFlightButton.addActionListener(l); } public void editFlightListener(ActionListener l) { editFlightButton.addActionListener(l); } public void deleteFlightListener(ActionListener l) { deleteFlightButton.addActionListener(l); }
    public void addBookingListener(ActionListener l) { addBookingButton.addActionListener(l); } public void cancelBookingListener(ActionListener l) { cancelBookingButton.addActionListener(l); }
    public void passengerSearchListener(DocumentListener l) { passengerSearch.getDocument().addDocumentListener(l); } public void flightSearchListener(DocumentListener l) { flightSearch.getDocument().addDocumentListener(l); } public void bookingSearchListener(DocumentListener l) { bookingSearch.getDocument().addDocumentListener(l); }
    public String passengerQuery() { return passengerSearch.getText(); } public String flightQuery() { return flightSearch.getText(); } public String bookingQuery() { return bookingSearch.getText(); }
    public void showMessage(String message) { JOptionPane.showMessageDialog(this, message); } public void showError(String message) { JOptionPane.showMessageDialog(this, message, "Validation error", JOptionPane.ERROR_MESSAGE); }
}
