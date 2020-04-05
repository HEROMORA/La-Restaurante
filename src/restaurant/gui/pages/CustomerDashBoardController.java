package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import restaurant.appUtils.AppUtilities;
import restaurant.data.repositories.ReservationRepository;
import restaurant.gui.guiUtils.Alerts;
import restaurant.gui.guiUtils.Navigation;
import restaurant.gui.guiUtils.Validations;
import restaurant.models.users.User;

import java.text.ParseException;
import java.time.LocalDate;

/*
Controller for Customer Dashboard page
Helps the user to make a reservation
 */
public class CustomerDashBoardController {

    public ComboBox numberOfSeatsComboBox;
    public DatePicker datePicker;
    public ComboBox fromHoursComboBox;
    public ComboBox fromMinsComboBox;
    public ComboBox toHoursComboBox;
    public ComboBox toMinsComboBox;
    public Button smokingBtn;
    public Button nonSmokingBtn;
    public Button reserveBtn;
    public Button logoutButton;

    private Boolean isSmoking = null;

    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();
    private AppUtilities appUtilities = new AppUtilities();
    private ReservationRepository reservationRepository = new ReservationRepository();
    private Navigation navigation = new Navigation();

    private User user;

    public CustomerDashBoardController(User user)
    {
        this.user = user;
    }

    @FXML
    private void handleSubmitActionButton(ActionEvent actionEvent) throws ParseException {
        if (!validateInput()) return;

        String username = user.getUsername();
        int numberOfSeats = Integer.parseInt(numberOfSeatsComboBox.getValue().toString());
        int fromHours = Integer.parseInt(fromHoursComboBox.getValue().toString());
        int fromMinutes = Integer.parseInt(fromMinsComboBox.getValue().toString());
        int toHours = Integer.parseInt(toHoursComboBox.getValue().toString());
        int toMinutes = Integer.parseInt(toMinsComboBox.getValue().toString());

        LocalDate localDate = datePicker.getValue();

        var reservationDate = appUtilities.getFullDate(localDate, fromHours, fromMinutes);
        var endReservationDate = appUtilities.getFullDate(localDate, toHours, toMinutes);

        var reservation = reservationRepository.makeReservation
                (username, numberOfSeats, reservationDate, endReservationDate, isSmoking);
        if (reservation == null) {
            alerts.showErrorAlert("FULLY BOOKED", "We apologize for not having a table that meets this requirements");
            return;
        }
        reservationRepository.saveReservation(reservation);

        navigation.setLoggedInUser(user);
        navigation.showOrdersPage("../pages/OrderPage.fxml","Make an Order",1200,700,(Stage)reserveBtn.getScene().getWindow(),reservation);

        alerts.showSuccessAlert("Booking Completed", "You've booked successfully!");
    }

    @FXML
    private void handleOnSmokingBtnClick(ActionEvent actionEvent) {
        isSmoking = true;
    }

    @FXML
    private void handleOnNonSmokingBtnClick(ActionEvent actionEvent) {
        isSmoking = false;
    }

    @FXML
    private void handleLogoutActionButton(ActionEvent actionEvent) {
        navigation.logout((Stage)logoutButton.getScene().getWindow());
    }


    //validations
    private boolean validateInput()
    {
        var v1 = validations.validateEmptyNumericComboBox(fromHoursComboBox);
        var v2 = validations.validateEmptyNumericComboBox(fromMinsComboBox);
        var v3 = validations.validateEmptyNumericComboBox(toHoursComboBox);
        var v4 = validations.validateEmptyNumericComboBox(toMinsComboBox);
        var v5 = validations.validateSmokingType(isSmoking);
        var v6 = validations.validateStartEndDates
                (fromHoursComboBox, fromMinsComboBox, toHoursComboBox, toMinsComboBox, datePicker);

        return v1 && v2 && v3 && v4 && v5  && v6;

    }

}
