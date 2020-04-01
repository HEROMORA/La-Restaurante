package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import restaurant.appUtils.AppUtilities;
import restaurant.data.repositories.ReservationRepository;
import restaurant.gui.utils.Alerts;
import restaurant.gui.utils.Utilities;
import restaurant.gui.utils.Validations;
import restaurant.users.User;

import java.text.ParseException;
import java.time.LocalDate;

public class CustomerDashBoardPageController {

    public ComboBox numberOfSeatsComboBox;
    public DatePicker datePicker;
    public ComboBox fromHoursComboBox;
    public ComboBox fromMinsComboBox;
    public ComboBox toHoursComboBox;
    public ComboBox toMinsComboBox;
    public Button smokingBtn;
    public Button nonSmokingBtn;
    public Button reserveBtn;

    private Boolean isSmoking = null;

    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();
    private AppUtilities appUtilities = new AppUtilities();
    private ReservationRepository reservationRepository = new ReservationRepository();
    private Utilities utilities = new Utilities();

    private User user;

    public CustomerDashBoardPageController(User user)
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

        //utilities.showOrdersPage("../pages/OrderPage.fxml","Make an Order",1200,700,(Stage)reserveBtn.getScene().getWindow());

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



    private boolean validateInput()
    {
        var v1 = validations.validateEmptyComboBox(fromHoursComboBox);
        var v2 = validations.validateEmptyComboBox(fromMinsComboBox);
        var v3 = validations.validateEmptyComboBox(toHoursComboBox);
        var v4 = validations.validateEmptyComboBox(toMinsComboBox);
        var v5 = validations.validateSmokingType(isSmoking);
        var v6 = validations.validateDate(datePicker);


        return v1 && v2 && v3 && v4 && v5 && v6;

    }

}
