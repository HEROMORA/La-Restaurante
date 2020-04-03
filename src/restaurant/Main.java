package restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.xml.transform.TransformerException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/pages/LoginPage.fxml"));

        Image ApplicationIcon = new Image(getClass().getResourceAsStream("gui/images/AppIcon.png"));
        primaryStage.getIcons().add(ApplicationIcon);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }


    public static void main(String[] args) throws TransformerException {
        launch(args);
    }
}
