package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import restaurant.gui.utils.Alerts;
import restaurant.gui.utils.Utilities;
import restaurant.gui.utils.Validations;
import restaurant.services.UserRepository;

public class LoginPageController {

    public Button signUpBtn;
    public TextField usernameTextField;
    public PasswordField passwordTextField;

    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();
    private Utilities utilities = new Utilities();

    private UserRepository userRepository = new UserRepository();

    @FXML
    private void handleSubmitActionButton(ActionEvent actionEvent) {

        if (!validateForm()) return;

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        var res = userRepository.login(username, password);
        if (!res) { alerts.showErrorAlert("False Credentials", "Wrong username or password"); }

        // TODO: NAVIGATE TO THE DASHBOARD

    }

    @FXML
    private void handleSignUpActionButton(ActionEvent actionEvent) {
        Stage stage = (Stage)signUpBtn.getScene().getWindow();
        utilities.showPage("../pages/RegisterPage.fxml", "Register", 1200, 700, stage);
    }

    private boolean validateForm()
    {
        var v1 = validations.validateEmptyTextField(usernameTextField);
        var v2 = validations.validateEmptyTextField(passwordTextField);

        return v1 && v2;
    }
}
