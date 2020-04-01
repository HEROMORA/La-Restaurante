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
import restaurant.users.UserRole;

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
            //TODO: Navigate to the Customer Dashboard
        }
    }

    @FXML
    private void handleSignInActionButton(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage)signInBtn.getScene().getWindow();
        utilities.showPage("../pages/LoginPage.fxml", "Login", 1200, 700, stage);
    }

    private boolean validateForm()
    {
        var v1 = validations.validateEmptyTextField(fullNameTextField);
        var v2 = validations.validateEmptyTextField(usernameTextField);
        var v3 = validations.validateEmptyTextField(passwordTextField);
        var v4 = validations.isLongPassword(passwordTextField.getText());

        return v1 && v2 && v3 && v4;
    }
}
