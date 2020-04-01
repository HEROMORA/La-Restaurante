package restaurant.gui.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import restaurant.dish.Dish;
import restaurant.dish.DishType;
import restaurant.gui.utils.Utilities;
import restaurant.order.OrderDetails;
import restaurant.services.DishRepository;

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
    private OrderDetails orderDetails = new OrderDetails();

    private DishRepository dr = new DishRepository();

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

        orderDetails.setDish(selectedDish);
        orderDetails.setQuantity(Integer.parseInt(quantityTextField.getText()));

        oOrderDetails.add(orderDetails);

        cartTableView.setItems(oOrderDetails);
    }

    @FXML
    private void handleOrderActionBtnClick(ActionEvent actionEvent){
        
    }

}
