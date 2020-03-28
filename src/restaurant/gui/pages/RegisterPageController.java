package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import restaurant.gui.utils.Utilities;

public class RegisterPageController {
    @FXML
    private Button signInBtn;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    private Utilities utilities = new Utilities();

    public void handleSubmitActionButton(ActionEvent actionEvent) {
    }

    public void handleSignInActionButton(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage)signInBtn.getScene().getWindow();
        utilities.showPage("../pages/MainPage.fxml", "Login", 1200, 700, stage);
    }
}
