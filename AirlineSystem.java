import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AirlineSystem {
    private final Map<String, String> userCredentials = new HashMap<>();
    private final Map<String, Customer> registeredUsers = new HashMap<>();
    private final String[] origins = {"Mumbai", "Delhi", "Kolkata", "Bangalore", "Prayagraj", "Kanpur"};
    private final String[] destinations = {"Mumbai", "Delhi", "Kolkata", "Bangalore", "Prayagraj", "Kanpur"};
    private final int[] prices = {5000, 7000, 8000, 9000, 2000};
    private final Random random = new Random();
    private final List<Flight> flightOptions = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private int bookingCounter = 1000;

    public AirlineSystem() {
        flightOptions.add(new Flight("AI-101", "Mumbai", "Delhi", "08:30", 6200));
        flightOptions.add(new Flight("AI-202", "Delhi", "Kolkata", "11:45", 7400));
        flightOptions.add(new Flight("AI-303", "Kolkata", "Bangalore", "15:20", 8900));
        flightOptions.add(new Flight("AI-404", "Bangalore", "Prayagraj", "19:10", 5200));
        flightOptions.add(new Flight("AI-505", "Prayagraj", "Kanpur", "21:50", 4100));
    }

    public String[] getOrigins() {
        return origins;
    }

    public String[] getDestinations() {
        return destinations;
    }

    public List<Flight> getFlightOptions() {
        return flightOptions;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    public Customer registerCustomer(String userName, String passWord, String name, String email, String phoneNumber) {
        if (registeredUsers.containsKey(userName)) {
            throw new IllegalArgumentException("Username already exists. Please choose another one.");
        }

        Customer customer = new Customer(userName, passWord, name, email, phoneNumber);
        registeredUsers.put(userName, customer);
        userCredentials.put(userName, passWord);
        return customer;
    }

    public Customer loginCustomer(String userName, String passWord) {
        if (!userCredentials.containsKey(userName)) {
            throw new IllegalArgumentException("Username not found.");
        }

        if (!userCredentials.get(userName).equals(passWord)) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        return registeredUsers.get(userName);
    }

    public int getRandomPrice() {
        return prices[random.nextInt(prices.length)];
    }

    public Booking createBooking(Customer customer, Flight flight, String seatClass, int passengers, String seatPreference) {
        int fareMultiplier = seatClass.equalsIgnoreCase("Business") ? 2 : 1;
        int totalPrice = flight.getBasePrice() * fareMultiplier * passengers;
        int totalWithPreference = seatPreference.equalsIgnoreCase("Window") ? totalPrice + 300 : totalPrice;

        Booking booking = new Booking(
                "BK" + bookingCounter++,
                customer.getName(),
                flight,
                seatClass,
                passengers,
                seatPreference,
                totalWithPreference
        );
        bookings.add(booking);
        return booking;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public static class Flight {
        private final String flightNumber;
        private final String origin;
        private final String destination;
        private final String departureTime;
        private final int basePrice;

        public Flight(String flightNumber, String origin, String destination, String departureTime, int basePrice) {
            this.flightNumber = flightNumber;
            this.origin = origin;
            this.destination = destination;
            this.departureTime = departureTime;
            this.basePrice = basePrice;
        }

        public String getFlightNumber() {
            return flightNumber;
        }

        public String getOrigin() {
            return origin;
        }

        public String getDestination() {
            return destination;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public int getBasePrice() {
            return basePrice;
        }

        @Override
        public String toString() {
            return flightNumber + " | " + origin + " -> " + destination + " | Time: " + departureTime + " | Price: " + basePrice;
        }
    }

    public static class Booking {
        private final String bookingId;
        private final String customerName;
        private final Flight flight;
        private final String seatClass;
        private final int passengers;
        private final String seatPreference;
        private final int price;

        public Booking(String bookingId, String customerName, Flight flight, String seatClass, int passengers, String seatPreference, int price) {
            this.bookingId = bookingId;
            this.customerName = customerName;
            this.flight = flight;
            this.seatClass = seatClass;
            this.passengers = passengers;
            this.seatPreference = seatPreference;
            this.price = price;
        }

        public String getBookingId() {
            return bookingId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getPrice() {
            return price;
        }

        public String getSummary() {
            return "Booking ID: " + bookingId + "\n"
                    + "Customer: " + customerName + "\n"
                    + "Flight: " + flight.getFlightNumber() + " (" + flight.getOrigin() + " -> " + flight.getDestination() + ")\n"
                    + "Departure: " + flight.getDepartureTime() + "\n"
                    + "Class: " + seatClass + "\n"
                    + "Passengers: " + passengers + "\n"
                    + "Seat Preference: " + seatPreference + "\n"
                    + "Total Price: " + price;
        }
    }

    public static class Customer {
        private final String userName;
        private final String passWord;
        private final String name;
        private final String email;
        private final String phoneNumber;
        private String origin;
        private String destination;
        private int price;

        public Customer(String userName, String passWord, String name, String email, String phoneNumber) {
            this.userName = userName;
            this.passWord = passWord;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getSummary() {
            return "Hello, " + name + "!\n"
                    + "Origin: " + origin + "\n"
                    + "Destination: " + destination + "\n"
                    + "Phone: " + phoneNumber + "\n"
                    + "Price: " + price;
        }
    }
}


