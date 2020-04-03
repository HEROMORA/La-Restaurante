package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import restaurant.data.repositories.OrderRepository;
import restaurant.data.repositories.ReservationRepository;
import restaurant.models.order.Order;
import restaurant.models.order.OrderDetails;
import restaurant.models.reservation.Reservation;
import restaurant.models.users.User;

import java.net.URL;
import java.util.ResourceBundle;

public class AlreadyReservedController implements Initializable {

    public Label welcomeLabel;
    public TableView<OrderDetails> orderDetailsTableView;
    public Label dateLabel;

    private User user;
    private Reservation reservation;
    private Order order;

    private ReservationRepository reservationRepository = new ReservationRepository();
    private OrderRepository orderRepository = new OrderRepository();

    public AlreadyReservedController(User user)
    {
        this.user = user;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greet();
        getReservationData();
        setData();
    }

    private void greet()
    {
        String fullName = user.getName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        welcomeLabel.setText(String.format("Hello, %s!", firstName));
    }


    private void getReservationData()
    {
        String username = user.getUsername();
        order = orderRepository.getOrderByCustomerName(username);
        reservation = reservationRepository.getReservationByCustomerUsername(username);
    }

    private void setData()
    {
        ObservableList<OrderDetails> data = FXCollections.observableArrayList(order.getOrdersDetails());
        orderDetailsTableView.setItems(data);

        dateLabel.setText(reservation.getReservationDate().toString());
    }
}
