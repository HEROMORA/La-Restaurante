package restaurant.gui.utils;

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

    public boolean isLongPassword(String password)
    {
        if (password.length() < 6)
        {
            alerts.showErrorAlert("Weak Password", "Please choose a password which is at least 6 chars long");
            return false;
        }

        return true;
    }
}
