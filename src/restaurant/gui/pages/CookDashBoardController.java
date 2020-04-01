package restaurant.gui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import restaurant.appUtils.AppUtilities;
import restaurant.order.Order;
import restaurant.data.repositories.OrderRepository;
import restaurant.users.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CookDashBoardController implements Initializable {
    public Label welcomeLabel;
    public ListView<String> todayOrdersListView;

    private User user;
    private AppUtilities appUtilities = new AppUtilities();
    private OrderRepository ordersRepository = new OrderRepository();

    public CookDashBoardController(User user)
    {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greet();
        fillList();
    }

    private void greet()
    {
        String fullName = user.getName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        welcomeLabel.setText(String.format("Hello, %s!", firstName));
    }

    private void fillList()
    {
        var orders = ordersRepository.getTodayOrders();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Order order:orders)
        {
            String orderString = appUtilities.getOrderDetailsForCook(order);
            items.add(orderString);
        }

        todayOrdersListView.setItems(items);
    }
}

