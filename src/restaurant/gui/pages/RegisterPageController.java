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
import restaurant.models.users.UserRole;

public class RegisterPageController {

    public Button signInBtn;
    public TextField fullNameTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;


    private UserRepository userRepository = new UserRepository();

    private Validations validations = new Validations();
    private Utilities utilities = new Utilities();
    private Alerts alerts = new Alerts();

    @FXML
    private void handleSubmitActionButton(ActionEvent actionEvent) {

        if (!validateForm()) return;

        String fullName = fullNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        var user = userRepository.signUp(username, password, fullName, UserRole.CUSTOMER);
        if (user == null)
            alerts.showErrorAlert("Existing username", "A user with the same username already exists");
        else {
            alerts.showSuccessAlert("Registered Successfully", "You have signed up successfully please login with your credentials");
            navigateToLoginPage();
        }
    }

    @FXML
    private void handleSignInActionButton(ActionEvent actionEvent) {
        navigateToLoginPage();
    }

    private void navigateToLoginPage()
    {
        Stage stage;
        stage = (Stage)signInBtn.getScene().getWindow();
        utilities.showPageWithoutController("../pages/LoginPage.fxml", "Login", 1200, 700, stage);
    }

    private boolean validateForm()
    {
        var v1 = validations.validateFullNameTextField(fullNameTextField);
        var v2 = validations.validateUsernameTextField(usernameTextField);
        var v3 = validations.validatePasswordField(passwordTextField);

        return v1 && v2 && v3;
    }
}
