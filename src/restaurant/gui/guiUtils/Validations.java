package restaurant.gui.guiUtils;

import javafx.scene.control.*;
import restaurant.models.dish.Dish;

import java.time.LocalDate;
import java.time.LocalTime;

public class Validations {

    // This class provides the required validations to handle the user input errors in gui

    private Alerts alerts = new Alerts();

    public boolean validateEmptyTextField(TextField textField)
    {
        String text = textField.getText();
        if (text.equals(""))
        {
            alerts.showErrorAlert("Missing Field","You must enter data in the text fields");
            return false;
        }

        return true;
    }

    public boolean validatePositiveIntegerNumericTextField(TextField textField)
    {
        var v1 = validateEmptyTextField(textField);
        if (!v1) return false;

        int num;
         try {
             String x = textField.getText();
             num = Integer.parseInt(x);
             if (num <= 0) throw new NumberFormatException();
         } catch (NumberFormatException ex) {
             alerts.showErrorAlert("Invalid input", "Please Enter a positive number in the textfield");
             return false;
         }

         return true;
    }

    public boolean validateUsernameTextField(TextField textField)
    {
        var v1 = validateEmptyTextField(textField);
        if (!v1) return false;

        String username = textField.getText();
        if (username.contains(" ") || username.length() > 20 || username.length() < 5)
        {
            alerts.showErrorAlert("Invalid Username",
                    "Username should not contain spaces and should be between 5 and 20 chars");
            return false;
        }

        return true;
    }

    public boolean validateFullNameTextField(TextField textField)
    {
        var v1 = validateEmptyTextField(textField);
        if (!v1) return false;

        String fullName = textField.getText().toLowerCase();

        String[] numOfWords = fullName.split(" ");
        if (numOfWords.length == 1) {
            alerts.showErrorAlert("Invalid Full Name", "please user your full name for ex \"John Smith\"");
            return false;
        }

        for (int i = 0; i < fullName.length(); i++)
        {
            char c = fullName.charAt(i);
            if (!((c >= 'a' && c <= 'z') || c == ' '))
            {
                alerts.showErrorAlert("Invalid Full Name",  "Please use only alphabetical letters and spaces");
                return false;
            }
        }

        return true;
    }

    public boolean validatePasswordField(PasswordField passwordField)
    {
        var v1 = validateEmptyTextField(passwordField);
        if (!v1) return false;

        return isLongPassword(passwordField.getText());
    }

    public boolean isLongPassword(String password)
    {
        if (password.length() < 6)
        {
            alerts.showErrorAlert("Weak Password", "Please choose a password which is at least 6 chars long");
            return false;
        }

        return true;
    }

    public boolean validateEmptyComboBox(ComboBox comboBox)
    {
        try {
            String text = comboBox.getValue().toString();
        } catch (NullPointerException ex)  {
            alerts.showErrorAlert("Empty Combobox", "Please make sure you've filled all the combo boxes");
            return false;
        }
        return  true;
    }

    public boolean validateEmptyNumericComboBox(ComboBox comboBox)
    {
        try {
            String text = comboBox.getValue().toString();
            int x = Integer.parseInt(text);
        } catch (NullPointerException | NumberFormatException ex) {
            alerts.showErrorAlert("Empty Combobox", "Please make sure you've filled all the combo boxes");
            return false;
        }
        return  true;
    }

    public boolean validateSmokingType(Boolean isSmoking)
    {
        if (isSmoking == null) {
            alerts.showSuccessAlert("Smoking Type is Required",
                    "Please select whether you want to sit in smoking area or not");
            return  false;
        }

        return true;
    }

    public boolean validateDate(DatePicker datePicker)
    {
        try {
            String text = datePicker.getValue().toString();

            if (LocalDate.now().isAfter(datePicker.getValue())) {
                alerts.showErrorAlert("Invalid Date", "Please selected an Upcomming date");
                return false;
            }


        } catch (NullPointerException ex) {
            alerts.showErrorAlert("Empty Date Picker", "You should select a date");
            return false;
        }
        return  true;
    }

    public boolean validateStartEndDates(ComboBox fromHoursComboBox, ComboBox fromMinsComboBox,
                                         ComboBox toHoursComboBox, ComboBox toMinsComboBox, DatePicker datePicker)
    {
        if (!validateDate(datePicker)) return false;

        // checks if values are numeric types
        int fromHours, fromMinutes, toHours, toMinutes;
        try {
            fromHours = Integer.parseInt(fromHoursComboBox.getValue().toString());
            fromMinutes = Integer.parseInt(fromMinsComboBox.getValue().toString());
            toHours = Integer.parseInt(toHoursComboBox.getValue().toString());
            toMinutes = Integer.parseInt(toMinsComboBox.getValue().toString());
        } catch (NullPointerException ex)
        {
            return false;
        }

        // checks if the time had already passed
        LocalTime reservTime = LocalTime.of(fromHours, fromMinutes);
        if (LocalTime.now().isAfter(reservTime) && datePicker.getValue().isEqual(LocalDate.now())) {
            alerts.showErrorAlert("Invalid times", "This time has already passed");
            return false;
        }

        boolean result;

        // Checks if the after hours are before the from hours

        if (fromHours == toHours)
            result = toMinutes > fromMinutes;
        else
            result =  toHours > fromHours;

        if (!result) {
            alerts.showErrorAlert("Invalid times", "Please set valid times for the reservation.");
        }

        return result;
    }

    public boolean validateEmptyTableView(TableView tableView)
    {
        return tableView.getItems().size() != 0;
    }

    public boolean validateEmptyCart(TableView tableView)
    {
        if (!validateEmptyTableView(tableView)) {
            alerts.showErrorAlert("No Elements add to cart", "Please add one or more dishes to the cart before ordering");
            return false;
        }

        return true;
    }

    public boolean validateSelectedDish(Dish selectedDish)
    {
        if (selectedDish == null)
        {
            alerts.showErrorAlert("No item selected", "Please select an item to add it to the cart");
            return false;
        }

        return true;
    }
}
