package restaurant.gui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
                break;
            case WAITER:
                WaiterDashboardController wdc = new WaiterDashboardController(user);
                loader.setController(wdc);
                break;
            case MANAGER:
                break;
            case COOK:
                break;
        }
        return loader.load();
    }
}
