package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import restaurant.data.repositories.DishRepository;
import restaurant.data.repositories.OrderRepository;
import restaurant.gui.utils.Alerts;
import restaurant.gui.utils.Utilities;
import restaurant.gui.utils.Validations;
import restaurant.models.dish.Dish;
import restaurant.models.dish.DishType;
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
    public Label taxesLabel;
    public Label priceWithoutTaxesLabel;

    private Dish selectedDish;
    private OrderDetails selectedOrderDetails;
    private ObservableList<OrderDetails> oOrderDetails= FXCollections.observableArrayList();
    private Reservation reservation;
    private Alerts alerts = new Alerts();
    private Utilities utilities = new Utilities();
    private DishRepository dr = new DishRepository();
    private Validations validations = new Validations();

    public OrderController(Reservation reservation)
    {
        this.reservation = reservation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateLists();
        listenForClicks();
    }

    @FXML
    private void handleAddToCartBtnClick(ActionEvent actionEvent){

        if (!validateAddToCartClick()) return;

        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setDish(selectedDish);
        orderDetails.setQuantity(Integer.parseInt(quantityTextField.getText()));

        oOrderDetails.add(orderDetails);

        cartTableView.setItems(oOrderDetails);

        handleLabels("add",orderDetails);
    }

    @FXML
    private void handleRemoveFromCartBtnClick(ActionEvent actionEvent) {
        if(selectedOrderDetails == null) {alerts.showErrorAlert("Invalid removal", "Please select an item before clicking remove from cart"); return;}

        handleLabels("remove",selectedOrderDetails);

        oOrderDetails.remove(selectedOrderDetails);
    }

    private void handleLabels(String operation,OrderDetails orderDetails){
        BigDecimal totalPrice = BigDecimal.valueOf(Double.parseDouble( totalPriceLabel.getText()));
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceWithoutTaxesLabel.getText()));
        BigDecimal taxes = BigDecimal.valueOf(Double.parseDouble(taxesLabel.getText()));
        switch (operation){
            case "add":
                totalPrice = totalPrice.add(orderDetails.getOrderDetailPrice());
                taxes = taxes.add(orderDetails.calculateTax());
                price = price.add(orderDetails.calculateSubTotal());
                break;
            case "remove":
                totalPrice = totalPrice.subtract(orderDetails.getOrderDetailPrice());
                taxes = taxes.subtract(orderDetails.calculateTax());
                price = price.subtract(orderDetails.calculateSubTotal());
                break;
        }
        totalPriceLabel.setText(totalPrice.toString());
        taxesLabel.setText(taxes.toString());
        priceWithoutTaxesLabel.setText(price.toString());
    }

    @FXML
    private void handleOrderActionBtnClick(ActionEvent actionEvent){

        if (!validateOrderClick()) return;

        OrderRepository orderRepository = new OrderRepository();

        ArrayList<OrderDetails> orderDetails = new ArrayList<OrderDetails>(oOrderDetails);

        Order order = orderRepository.makeOrder(reservation.getReservationDate(),reservation.getCustomerUserName(),reservation.getTableNum(),orderDetails);

        orderRepository.saveOrder(order);

        alerts.showSuccessAlert("Order Completed", "You've ordered successfully!");
    }

    private void populateLists()
    {
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
    }

    private void listenForClicks()
    {
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

        cartTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            selectedOrderDetails = cartTableView.getSelectionModel().getSelectedItem();
        });
    }


    private boolean validateAddToCartClick()
    {
        var v1 = validations.validatePositiveIntegerNumericTextField(quantityTextField);
        return v1;
    }

    private boolean validateOrderClick()
    {
        var v1 =validations.validateEmptyCart(cartTableView);

        return v1;
    }

}
