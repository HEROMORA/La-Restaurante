package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import restaurant.data.repositories.OrderRepository;
import restaurant.data.repositories.ReservationRepository;
import restaurant.gui.guiUtils.Alerts;
import restaurant.gui.guiUtils.Navigation;
import restaurant.models.order.OrderDetails;
import restaurant.models.reservation.Reservation;
import restaurant.models.users.User;

import java.net.URL;
import java.util.ResourceBundle;

/*
Controller for cook dashboard
Helps the cook see the upcoming reservations and orders of each reservation
 */
public class CookDashBoardController implements Initializable {
    public Label welcomeLabel;
    public TableView<Reservation> reservationTableView;
    public TableView<OrderDetails> ordersTableView;
    public Button logoutButton;

    private User user;
    private Alerts alerts = new Alerts();
    private Navigation navigation = new Navigation();
    private ReservationRepository reservationRepository = new ReservationRepository();
    private OrderRepository orderRepository = new OrderRepository();

    public CookDashBoardController(User user)
    {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greet();
        fillList();
        listenForSelections();
    }

    @FXML
    private void handleLogoutActionButton(ActionEvent actionEvent) {
        navigation.logout((Stage)logoutButton.getScene().getWindow());
    }

    private void greet()
    {
        String fullName = user.getName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        welcomeLabel.setText(String.format("Hello, %s!", firstName));
    }

    //fills the reservation table view with today's reservations
    private void fillList()
    {
        try {
            ObservableList<Reservation> data = FXCollections.observableArrayList(reservationRepository.getTodayReservations());
            reservationTableView.setItems(data);
        } catch (NullPointerException ex)
        {
            alerts.showInfoAlert("No Data", "No Reservations is made for today yet");
        }
    }

    //action listeners for the reservation table view
    private void listenForSelections()
    {
        reservationTableView.setOnMouseClicked(event -> {
            try {
                var reservation = reservationTableView.getSelectionModel().getSelectedItem();
                var order = orderRepository.getOrderByCustomerUserNameAndDate(reservation.getCustomerUserName(), reservation.getReservationDate());

                ObservableList<OrderDetails> data = FXCollections.observableArrayList(order.getOrdersDetails());
                ordersTableView.setItems(data);
            } catch (NullPointerException ex) {
                //User may have not ordered yet
            }
        });
    }
}

