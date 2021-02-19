package sample;

//imports

//javafx imports
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent main = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // Putting the layout container in the scene.
        Scene scene = new Scene(main, 600, 300);

        // Adding the scene to the stage.
        primaryStage.setScene(scene);

        // Set the stage title.
        primaryStage.setTitle("Inventory Management System");

        //Show the window.
        primaryStage.show();

    }

}
