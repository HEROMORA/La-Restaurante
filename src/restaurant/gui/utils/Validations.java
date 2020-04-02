package restaurant.gui.utils;

import javafx.scene.control.*;
import restaurant.models.dish.Dish;

public class Validations {

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
        } catch (NullPointerException ex) {
            alerts.showErrorAlert("Empty Date Picker", "You should select a date");
            return false;
        }
        return  true;
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
