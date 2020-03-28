package restaurant.gui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utilities {

    private Alerts alerts = new Alerts();

    public void showPage(String fileName ,String title, int width, int height)
    {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(fileName));
            Stage detailsStage = new Stage();
            detailsStage.setTitle(title);
            Scene sc = new Scene(root, width, height);
            detailsStage.setScene(sc);
            detailsStage.show();
        } catch (IOException ex) {
            alerts.showErrorAlert("Data Error", "Something wrong happened!");
        }

    }

    public void closeCurrentPage()
    {

    }
}
