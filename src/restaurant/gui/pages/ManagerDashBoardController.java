package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import restaurant.gui.utils.Alerts;
import restaurant.order.OrderDetails;
import restaurant.reservation.Reservation;
import restaurant.services.OrderRepository;
import restaurant.services.ReservationRepository;
import restaurant.users.User;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerDashBoardController implements Initializable {
    public Label welcomeLabel;
    public Label totalTodayIncomeLabel;
    public TableView<Reservation> reservationTableView;
    public TableView<OrderDetails> ordersTableView;

    private ReservationRepository reservationRepository = new ReservationRepository();
    private OrderRepository orderRepository = new OrderRepository();
    private Alerts alerts = new Alerts();

    private User user;
    public ManagerDashBoardController(User user)
    {
        this.user = user;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greet();
        fillList();
        listenForSelections();
        getTodayTotalIncome();
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

    private void getTodayTotalIncome()
    {
        try {
            BigDecimal totalPrice = orderRepository.getTodayTotalPrice();
            totalTodayIncomeLabel.setText(totalPrice.toString());
        } catch (NullPointerException ex) {

        }

    }
}
