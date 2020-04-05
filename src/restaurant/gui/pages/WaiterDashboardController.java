package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import restaurant.data.repositories.ReservationRepository;
import restaurant.gui.guiUtils.Navigation;
import restaurant.models.reservation.Reservation;
import restaurant.models.users.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
    Controller for waiter dashboard
    Helps the waiter see today's reservations and tables so that he can serve them
 */
public class WaiterDashboardController implements Initializable {
    public Label welcomeLabel;
    public Button logoutButton;
    public TableView<Reservation> reservationsTableView;
    public ListView<String> reservationsListView;

    private Navigation navigation = new Navigation();
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

    @FXML
    private void handleLogoutActionButton(ActionEvent actionEvent) {
        navigation.logout((Stage)logoutButton.getScene().getWindow());
    }

    private void fillList()
    {
        ArrayList<Reservation> reservations = reservationRepository.getTodayReservations();
        ObservableList<Reservation> items = FXCollections.observableArrayList(reservations);
        reservationsTableView.setItems(items);
    }
}
