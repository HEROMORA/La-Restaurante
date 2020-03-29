package restaurant.gui.pages;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import restaurant.gui.utils.Alerts;
import restaurant.gui.utils.Validations;
import restaurant.services.ReservationRepository;
import restaurant.users.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    private boolean isSmoking;

    private Validations validations = new Validations();
    private Alerts alerts = new Alerts();
    private ReservationRepository reservationRepository = new ReservationRepository();

    User user;

    public CustomerDashBoardPageController(User user)
    {
        this.user = user;
    }


    public void handleSubmitActionButton(ActionEvent actionEvent) {
        if (!validateInput()) return;

        String username = user.getUsername();
        int numberOfSeats = Integer.parseInt(numberOfSeatsComboBox.getValue().toString());
        LocalDate localDate = datePicker.getValue();
        Date reservationDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        var reservation = reservationRepository.makeReservation(username, numberOfSeats, reservationDate);
        if (reservation == null) {
            alerts.showErrorAlert("FULLY BOOKED", "We apologize for not having a table that meets this requirements");
            return;
        }
        reservationRepository.saveReservation(reservation);
        alerts.showSuccessAlert("Booking Completed", "You've booked successfully!");
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
