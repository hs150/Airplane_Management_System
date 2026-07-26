import airline.controller.DashboardController;
import airline.service.AirlineService;
import airline.view.DashboardView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AirlineService service = new AirlineService();
            DashboardView dashboardView = new DashboardView("Preview Access");
            new DashboardController(service, dashboardView);
        });
    }
}
