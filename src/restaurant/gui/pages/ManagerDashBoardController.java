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
import restaurant.models.users.Manager;
import restaurant.models.users.User;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/*
    Controller for the manager dashboard page
    Helps the manager see today's reservations and money paid with each reservation
    Helps the manager see total income of the restaurant today
    Manager can access the employee sign up page which he can use when he hires a new employee
 */
public class ManagerDashBoardController implements Initializable {
    public Label welcomeLabel;
    public Label totalTodayIncomeLabel;
    public Label reservationPaidPriceLabel;
    public Button employeeSignUpButton;
    public Button logoutButton;
    public TableView<Reservation> reservationTableView;
    public TableView<OrderDetails> ordersTableView;

    private ReservationRepository reservationRepository = new ReservationRepository();
    private OrderRepository orderRepository = new OrderRepository();
    private Navigation navigation = new Navigation();
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
                var order = orderRepository.getOrderByCustomerUserNameAndDate(reservation.getCustomerUserName(), reservation.getReservationDate());

                ObservableList<OrderDetails> data = FXCollections.observableArrayList(order.getOrdersDetails());
                ordersTableView.setItems(data);

                reservationPaidPriceLabel.setText(order.calculateTotalPrice().toString());
            } catch (NullPointerException ex) {
                //User may have not ordered yet
            }
        });
    }


    @FXML
    private void handleEmployeeSignUpBtnClick(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage)employeeSignUpButton.getScene().getWindow();
        navigation.showEmployeeSignUp(stage,(Manager) user);
    }

    @FXML
    private void handleLogoutActionButton(ActionEvent actionEvent) {
        navigation.logout((Stage)logoutButton.getScene().getWindow());
    }

    private void getTodayTotalIncome()
    {
        try {
            BigDecimal totalPrice = orderRepository.getTodayTotalPrice();
            totalTodayIncomeLabel.setText(totalPrice.toString());
        } catch (NullPointerException ex) {
            // Not showing error if the list is empty because there are no reservations yet
        }

    }
}
