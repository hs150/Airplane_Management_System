import airline.controller.LoginController;
import airline.service.AirlineService;
import airline.view.LoginView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AirlineService service = new AirlineService();
            LoginView loginView = new LoginView();
            new LoginController(service, loginView);
        });
    }
}
