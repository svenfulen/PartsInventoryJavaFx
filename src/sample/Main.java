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

    public static Inventory database = new Inventory();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load sample data into the table.
        database.addPart(new Outsourced(1, "Tool Box", 0.95, 300, 30, 1000, "U-SQUIGGLE Plastic Boxes"));
        database.addPart(new Outsourced(2, "Plastic Box - Transparent", 0.95, 300, 30, 1000, "U-SQUIGGLE Plastic Boxes"));
        database.addPart(new InHouse(3, "Hammer", 0.95, 300, 30, 1000, 24873));
        database.addPart(new InHouse(4, "Screwdriver", 0.95, 300, 30, 1000, 24874));
        database.addPart(new InHouse(5, "Nail", 0.95, 300, 30, 1000, 17973));

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
