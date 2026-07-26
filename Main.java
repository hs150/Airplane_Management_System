public class Main {
    public static void main(String[] args) {
        AirlineSystem system = new AirlineSystem();

        AirlineSystem.Customer customer = system.registerCustomer(
                "demo",
                "demo123",
                "Demo User",
                "demo@example.com",
                "9876543210"
        );

        AirlineSystem.Flight flight = system.getFlightOptions().get(0);
        AirlineSystem.Booking booking = system.createBooking(customer, flight, "Economy", 1, "Window");

        System.out.println("Welcome to the Airline Management System!");
        System.out.println("Registered customer: " + customer.getName());
        System.out.println("Booking created: " + booking.getBookingId());
        System.out.println(booking.getSummary());
    }
}

