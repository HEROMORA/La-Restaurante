package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import restaurant.gui.guiUtils.Alerts;
import restaurant.gui.guiUtils.Navigation;
import restaurant.gui.guiUtils.Validations;
import restaurant.data.repositories.UserRepository;
import restaurant.models.users.User;

public class LoginController {

    public Button signUpBtn;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button signInBtn;

    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();
    private Navigation navigation = new Navigation();

    private UserRepository userRepository = new UserRepository();

    @FXML
    private void handleSubmitActionButton(ActionEvent actionEvent) {

        if (!validateForm()) return;

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User user = userRepository.login(username, password);
        if (user == null) { alerts.showErrorAlert("False Credentials", "Wrong username or password"); return;}
        Stage stage = (Stage)signInBtn.getScene().getWindow();

        navigation.setLoggedInUser(user);
        navigation.showPageByRole(user, stage);
    }

    @FXML
    private void handleSignUpActionButton(ActionEvent actionEvent) {
        Stage stage = (Stage)signUpBtn.getScene().getWindow();
        navigation.showPageWithoutController("../pages/RegisterPage.fxml", "Register", 1200, 700, stage);
    }

    private boolean validateForm()
    {
        var v1 = validations.validateEmptyTextField(usernameTextField);
        var v2 = validations.validateEmptyTextField(passwordTextField);

        return v1 && v2;
    }
}
