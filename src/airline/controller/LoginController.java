package airline.controller;

import airline.service.AirlineService;
import airline.view.DashboardView;
import airline.view.LoginView;

public class LoginController {
    private final AirlineService service;
    private final LoginView view;

    public LoginController(AirlineService service, LoginView view) {
        this.service = service;
        this.view = view;
        init();
    }

    private void init() {
        view.addLoginListener(e -> {
            String username = view.getUsername();
            String password = view.getPassword();
            if (username.isEmpty() || password.isEmpty()) {
                view.showMessage("Please enter both username and password.");
                return;
            }
            if (service.login(username, password)) {
                view.showMessage("Login successful!");
                view.dispose();
                new DashboardController(service, new DashboardView(service.getUser(username).getFullName()));
            } else {
                view.showMessage("Invalid username or password.");
            }
        });
    }
}
