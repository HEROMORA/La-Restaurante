package restaurant.gui.guiUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import restaurant.gui.pages.*;
import restaurant.models.reservation.Reservation;
import restaurant.models.users.Manager;
import restaurant.models.users.User;

import java.io.IOException;

public class Navigation {

    // This class provides navigation tools for the rest of the gui classes and helps them navigate smoothly
    // with the most kind of abstraction

    private Alerts alerts = new Alerts();
    private User loggedInUser;


    // Creates a new page and creates a controller which takes the logged in user as a parameter in constructor
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

    // this functions sets the logged in user object
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    // Creates a controller instance for the different roles of the application
    private GridPane createControllerInstance(User user , FXMLLoader loader) throws IOException {

        switch (user.getUserRole()){

            case CUSTOMER:
                CustomerDashBoardController cdc = new CustomerDashBoardController(user);
                loader.setController(cdc);
                break;

            case WAITER:
                WaiterDashboardController wdc = new WaiterDashboardController(user);
                loader.setController(wdc);
                break;

            case MANAGER:
                ManagerDashBoardController mdc = new ManagerDashBoardController(user);
                loader.setController(mdc);
                break;

            case COOK:
                CookDashBoardController cookdc = new CookDashBoardController(user);
                loader.setController(cookdc);
                break;
        }
        return loader.load();
    }

    // Shows the page depending on the user role
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
                showPage("../pages/ManagerDashBoardPage.fxml", "Hello Manager", width, height, stage);
                break;
        }
    }

    // function that loads the orders page with the required parameters for it controller
    public void showOrdersPage(String fileName , String title, int width, int height, Stage stage, Reservation res){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
            OrderController oc = new OrderController(res);
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

    // General function that loads the page without any additional parameters in construction
    public void showPageWithoutController(String fileName , String title, int width, int height, Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
            GridPane grid = loader.load();
            stage.setTitle(title);
            Scene sc = new Scene(grid, width, height);
            stage.setScene(sc);
            stage.show();
        } catch (IOException ex) {
            alerts.showErrorAlert("Data Error", "Something wrong happened!");
        }
    }

    //Function that loads the Employee Sign Up Page with required parameters
    public void showEmployeeSignUp(Stage stage, Manager manager){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../pages/EmployeeSignUpPage.fxml"));
            EmployeeSignUpController esuc = new EmployeeSignUpController();
            esuc.setManager(manager);
            loader.setController(esuc);
            GridPane grid = loader.load();
            stage.setTitle("Employee Sign Up");
            Scene sc = new Scene(grid, 1200, 700);
            stage.setScene(sc);
            stage.show();
        } catch (IOException ex) {
            alerts.showErrorAlert("Data Error", "Something wrong happened!");
        }
    }

    // Function that logs out the user and return him to sign in page
    public void logout(Stage stage)
    {
        int width = 1200; int height = 720;
        showPageWithoutController("../pages/LoginPage.fxml", "Login", width, height, stage);
    }

    // function that loads the already reserved page with the required parameters for it controller
    public void showAlreadyReservedController(Stage stage , User user){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../pages/AlreadyReservedPage.fxml"));
            AlreadyReservedController arc = new AlreadyReservedController(user);
            loader.setController(arc);
            GridPane grid = loader.load();
            stage.setTitle("Hello Customer");
            Scene sc = new Scene(grid, 1200, 700);
            stage.setScene(sc);
            stage.show();
        } catch (IOException ex) {
            alerts.showErrorAlert("Data Error", "Something wrong happened!");
        }

    }
}
