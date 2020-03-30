package restaurant.gui.pages;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import restaurant.gui.utils.Utilities;
import restaurant.reservation.Reservation;
import restaurant.services.ReservationRepository;
import restaurant.users.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WaiterDashboardController implements Initializable {
    public Label welcomeLabel;
    public ListView<String> reservationsListView;
    private Utilities utilities= new Utilities();

    private ReservationRepository reservationRepository = new ReservationRepository();
    private User user;

    public WaiterDashboardController(User user)
    {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String fullName = user.getName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        welcomeLabel.setText(String.format("Hello, %s!", firstName));
        fillList();
    }

    private void fillList()
    {
        ArrayList<Reservation> reservations = reservationRepository.getTodayReservations();
        utilities.populateListView(reservationsListView, reservations);
    }
}
