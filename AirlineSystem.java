import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AirlineSystem {
    private final Map<String, String> userCredentials = new HashMap<>();
    private final Map<String, Customer> registeredUsers = new HashMap<>();
    private final String[] origins = {"Mumbai", "Delhi", "Kolkata", "Bangalore", "Prayagraj", "Kanpur"};
    private final String[] destinations = {"Mumbai", "Delhi", "Kolkata", "Bangalore", "Prayagraj", "Kanpur"};
    private final int[] prices = {5000, 7000, 8000, 9000, 2000};
    private final Random random = new Random();

    public String[] getOrigins() {
        return origins;
    }

    public String[] getDestinations() {
        return destinations;
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


