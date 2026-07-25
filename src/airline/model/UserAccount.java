package airline.model;

public class UserAccount {
    private final String username;
    private final String password;
    private final String fullName;

    public UserAccount(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }
}
