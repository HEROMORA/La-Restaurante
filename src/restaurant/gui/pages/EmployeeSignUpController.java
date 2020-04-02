package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import restaurant.data.repositories.UserRepository;
import restaurant.gui.utils.Alerts;
import restaurant.gui.utils.Validations;

public class EmployeeSignUpController {
    public TextField fullNameTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public ComboBox<String> roleComboBox;

    private UserRepository userRepository = new UserRepository();
    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();

    @FXML
    private void handleSubmitActionButton(ActionEvent actionEvent) {
        if (!validateInput()) return;

        String role = roleComboBox.getValue();
        String username = usernameTextField.getText();
        String fullName = fullNameTextField.getText();
        String password = passwordTextField.getText();

        var user = userRepository.signUp(username, password, fullName, role);
        if (user == null)
            alerts.showErrorAlert("Existing username", "A user with the same username already exists");
        else {
            alerts.showSuccessAlert("Signed up", "Employee have signed up successfully !!");
        }
    }

    private boolean validateInput()
    {
        var v1 = validations.validateFullNameTextField(fullNameTextField);
        var v2 = validations.validateUsernameTextField(usernameTextField);
        var v3 = validations.validatePasswordField(passwordTextField);
        var v4 = validations.validateEmptyComboBox(roleComboBox);

        return v1 && v2 && v3 && v4;
    }
}
