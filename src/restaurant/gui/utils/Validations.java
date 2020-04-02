package restaurant.gui.utils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
}
