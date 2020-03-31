package restaurant.gui.pages;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import restaurant.users.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CookDashBoardController implements Initializable {
    public Label welcomeLabel;
    public ListView todayOrdersListView;

    private User user;

    public CookDashBoardController(User user)
    {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greet();
    }

    private void greet()
    {
        String fullName = user.getName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        welcomeLabel.setText(String.format("Hello, %s!", firstName));
    }
}

