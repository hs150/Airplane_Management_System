package airline.model;

public class Passenger {
    private String passengerId;
    private String fullName;
    private int age;
    private String gender;
    private String passportNumber;
    private String nationality;
    private String phone;
    private String email;
    private String address;
    private String flightAssigned;
    private String seatNumber;
    private String bookingDate;

    public Passenger(String passengerId, String fullName, int age, String gender, String passportNumber,
                     String nationality, String phone, String email, String address,
                     String flightAssigned, String seatNumber, String bookingDate) {
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.flightAssigned = flightAssigned;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
    }

    public String getPassengerId() { return passengerId; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPassportNumber() { return passportNumber; }
    public String getNationality() { return nationality; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getFlightAssigned() { return flightAssigned; }
    public String getSeatNumber() { return seatNumber; }
    public String getBookingDate() { return bookingDate; }

    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setFlightAssigned(String flightAssigned) { this.flightAssigned = flightAssigned; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
}
