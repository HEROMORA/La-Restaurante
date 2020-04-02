package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import restaurant.data.repositories.DishRepository;
import restaurant.data.repositories.OrderRepository;
import restaurant.models.dish.Dish;
import restaurant.models.dish.DishType;
import restaurant.gui.utils.Alerts;
import restaurant.models.order.Order;
import restaurant.models.order.OrderDetails;
import restaurant.models.reservation.Reservation;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public GridPane grid;
    public TableView<Dish> mainCourseTableView;
    public TableView<Dish> appetizersTableView;
    public TableView<Dish> dessertsTableView;
    public Button orderBtn;
    public Label totalPriceLabel;
    public TextField quantityTextField;
    public TableView<OrderDetails> cartTableView;
    private Dish selectedDish;
    private ObservableList<OrderDetails> oOrderDetails= FXCollections.observableArrayList();
    private Reservation reservation;
    private Alerts alerts = new Alerts();

    private DishRepository dr = new DishRepository();

    public OrderController(Reservation reservation)
    {
        this.reservation = reservation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Code for main dishes' table view
        ArrayList<Dish> mainCourseDishes =dr.getDishesByType(DishType.MAINDISH);
        ObservableList<Dish> oMainCourseDishes = FXCollections.observableArrayList(mainCourseDishes);
        mainCourseTableView.setItems(oMainCourseDishes);

        //Code for appetizers' table view
        ArrayList<Dish> appetizerDishes =dr.getDishesByType(DishType.APPETIZER);
        ObservableList<Dish> oAppetizerDishes = FXCollections.observableArrayList(appetizerDishes);
        appetizersTableView.setItems(oAppetizerDishes);

        //Code for desserts' table view
        ArrayList<Dish> dessertDishes =dr.getDishesByType(DishType.DESERT);
        ObservableList<Dish> oDessertDishes = FXCollections.observableArrayList(dessertDishes);
        dessertsTableView.setItems(oDessertDishes);

        mainCourseTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection != null){
                appetizersTableView.getSelectionModel().clearSelection();
                dessertsTableView.getSelectionModel().clearSelection();
            }
            selectedDish = mainCourseTableView.getSelectionModel().getSelectedItem();
        });
        appetizersTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection != null){
                mainCourseTableView.getSelectionModel().clearSelection();
                dessertsTableView.getSelectionModel().clearSelection();
            }
            selectedDish = appetizersTableView.getSelectionModel().getSelectedItem();
        });
        dessertsTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection != null){
                appetizersTableView.getSelectionModel().clearSelection();
                mainCourseTableView.getSelectionModel().clearSelection();
            }
            selectedDish = dessertsTableView.getSelectionModel().getSelectedItem();
        });

    }

    @FXML
    private void handleAddToCartBtnClick(ActionEvent actionEvent){
        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setDish(selectedDish);
        orderDetails.setQuantity(Integer.parseInt(quantityTextField.getText()));

        oOrderDetails.add(orderDetails);

        cartTableView.setItems(oOrderDetails);

        BigDecimal totalPrice = BigDecimal.valueOf(Double.parseDouble( totalPriceLabel.getText()));
        totalPrice = totalPrice.add(orderDetails.calculateSubTotal());

        totalPriceLabel.setText(totalPrice.toString());
    }

    @FXML
    private void handleOrderActionBtnClick(ActionEvent actionEvent){
        OrderRepository orderRepository = new OrderRepository();

        ArrayList<OrderDetails> orderDetails = new ArrayList<OrderDetails>(oOrderDetails);

        Order order = orderRepository.makeOrder(reservation.getReservationDate(),reservation.getCustomerUserName(),reservation.getTableNum(),orderDetails);

        orderRepository.saveOrder(order);

        alerts.showSuccessAlert("Order Completed", "You've ordered successfully!");
    }

}
