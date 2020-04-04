package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import restaurant.data.repositories.ReservationRepository;
import restaurant.data.repositories.UserRepository;
import restaurant.gui.guiUtils.Alerts;
import restaurant.gui.guiUtils.Navigation;
import restaurant.gui.guiUtils.Validations;
import restaurant.models.reservation.Reservation;
import restaurant.models.users.User;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LoginController {

    public Button signUpBtn;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button signInBtn;

    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();
    private Navigation navigation = new Navigation();

    private UserRepository userRepository = new UserRepository();
    private ReservationRepository reservationRepository = new ReservationRepository();

    @FXML
    private void handleSubmitActionButton(ActionEvent actionEvent) {

        if (!validateForm()) return;

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User user = userRepository.login(username, password);
        if (user == null) { alerts.showErrorAlert("False Credentials", "Wrong username or password"); return;}
        Stage stage = (Stage)signInBtn.getScene().getWindow();

        showNextPage(user,stage);
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


    private void showNextPage(User user,Stage stage){
        boolean result = hasCurrentReservation(user);

        if(!result){
            navigation.setLoggedInUser(user);
            navigation.showPageByRole(user, stage);
        }
        else{
            navigation.showAlreadyReservedController(stage,user);
        }
    }

    private boolean hasCurrentReservation(User user)
    {
        Reservation res = reservationRepository.getReservationByCustomerUsername(user.getUsername());
        if (res == null) return false;

        var endDate = res.getEndReservationDate();
        LocalDateTime localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return LocalDateTime.now().isBefore(localEndDate);
    }
}
