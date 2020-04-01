package restaurant.gui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import restaurant.gui.pages.CookDashBoardController;
import restaurant.gui.pages.CustomerDashBoardPageController;
import restaurant.gui.pages.OrderController;
import restaurant.gui.pages.WaiterDashboardController;
import restaurant.users.User;

import java.io.IOException;

public class Utilities {

    private Alerts alerts = new Alerts();
    private User loggedInUser;

    public void showPage(String fileName ,String title, int width, int height, Stage stage)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
            GridPane grid = createControllerInstance(this.loggedInUser,loader);
            stage.setTitle(title);
            Scene sc = new Scene(grid, width, height);
            stage.setScene(sc);
            stage.show();
        } catch (IOException ex) {
            alerts.showErrorAlert("Data Error", "Something wrong happened!");
        }

    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    private GridPane createControllerInstance(User user , FXMLLoader loader) throws IOException {

        switch (user.getUserRole()){

            case CUSTOMER:
                CustomerDashBoardPageController cdc = new CustomerDashBoardPageController(user);
                loader.setController(cdc);
                break;

            case WAITER:
                WaiterDashboardController wdc = new WaiterDashboardController(user);
                loader.setController(wdc);
                break;

            case MANAGER:
                break;

            case COOK:
                CookDashBoardController cookdc = new CookDashBoardController(user);
                loader.setController(cookdc);
                break;
        }
        return loader.load();
    }

    public void showPageByRole(User user, Stage stage) {
        int width = 1200;
        int height = 700;

        switch (user.getUserRole()) {
            case CUSTOMER:
                showPage("../pages/CustomerDashBoardPage.fxml", "Hello Customer", width, height, stage);
                break;
            case COOK:
                showPage("../pages/CookDashBoardPage.fxml", "Hello Cook", width, height, stage);
                break;
            case WAITER:
                showPage("../pages/WaiterDashboardPage.fxml", "Hello Waiter", width, height, stage);
                break;
            case MANAGER:
                break;
        }
    }
    public void showOrdersPage(String fileName ,String title, int width, int height, Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
            OrderController oc = new OrderController();
            loader.setController(oc);
            GridPane grid = loader.load();
            stage.setTitle(title);
            Scene sc = new Scene(grid, width, height);
            stage.setScene(sc);
            stage.show();
        } catch (IOException ex) {
            alerts.showErrorAlert("Data Error", "Something wrong happened!");
        }
    }
}
