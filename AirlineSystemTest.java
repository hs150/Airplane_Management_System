public class AirlineSystemTest {
    public static void main(String[] args) {
        AirlineSystem system = new AirlineSystem();
        AirlineSystem.Customer customer = system.registerCustomer("alice", "pass123", "Alice", "alice@example.com", "1234567890");

        AirlineSystem.Flight flight = system.getFlightOptions().get(0);
        AirlineSystem.Booking booking = system.createBooking(customer, flight, "Business", 2, "Window");

        if (booking.getPrice() <= 0) {
            throw new AssertionError("Booking price should be positive");
        }

        if (booking.getBookingId().isEmpty()) {
            throw new AssertionError("Booking ID should be generated");
        }

        if (!booking.getCustomerName().equals("Alice")) {
            throw new AssertionError("Customer name should be stored");
        }

        System.out.println("AirlineSystem tests passed");
    }
}
