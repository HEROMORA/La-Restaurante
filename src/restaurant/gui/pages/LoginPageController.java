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
import restaurant.data.repositories.UserRepository;
import restaurant.users.User;

public class LoginPageController {

    public Button signUpBtn;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button signInBtn;

    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();
    private Utilities utilities = new Utilities();

    private UserRepository userRepository = new UserRepository();

    @FXML
    private void handleSubmitActionButton(ActionEvent actionEvent) {

        if (!validateForm()) return;

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User user = userRepository.login(username, password);
        if (user == null) { alerts.showErrorAlert("False Credentials", "Wrong username or password"); return;}
        Stage stage = (Stage)signInBtn.getScene().getWindow();

        utilities.setLoggedInUser(user);
        utilities.showPageByRole(user, stage);
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
