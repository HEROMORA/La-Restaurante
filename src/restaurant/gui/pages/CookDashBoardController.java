package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import restaurant.data.repositories.OrderRepository;
import restaurant.data.repositories.ReservationRepository;
import restaurant.gui.utils.Alerts;
import restaurant.models.order.OrderDetails;
import restaurant.models.reservation.Reservation;
import restaurant.models.users.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CookDashBoardController implements Initializable {
    public Label welcomeLabel;
    public TableView<Reservation> reservationTableView;
    public TableView<OrderDetails> ordersTableView;

    private User user;
    private Alerts alerts = new Alerts();
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

    private void greet()
    {
        String fullName = user.getName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        welcomeLabel.setText(String.format("Hello, %s!", firstName));
    }

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

    private void listenForSelections()
    {
        reservationTableView.setOnMouseClicked(event -> {
            try {
                var reservation = reservationTableView.getSelectionModel().getSelectedItem();
                var order = orderRepository.getOrderByCustomerName(reservation.getCustomerUserName());

                ObservableList<OrderDetails> data = FXCollections.observableArrayList(order.getOrdersDetails());
                ordersTableView.setItems(data);
            } catch (NullPointerException ex) {
                //User may have not ordered yet
            }
        });
    }
}

