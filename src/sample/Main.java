/*
WGU C482/Software 1 Project
Basic inventory system
@author Sven Fulenchek
@version 1.0
@since 2/15/2021
 */

package sample;

//javafx imports
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class Main extends Application {

    /**
    // Initializes database, which is an Inventory object that holds all data, that can be accessed by all classes.
     */
    public static Inventory database = new Inventory();


    public static void main(String[] args) {
        launch(args);
    }


    // Starts the JavaFX application.
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load sample data into the table when application starts.  For testing purposes.
        database.addPart(new Outsourced(1, "Tool Box", 0.95, 300, 30, 1000, "U-SQUIGGLE Plastic Boxes"));
        database.addPart(new Outsourced(2, "Plastic Box - Transparent", 0.95, 300, 30, 1000, "U-SQUIGGLE Plastic Boxes"));
        database.addPart(new Outsourced(3, "Box - 100 1INCH NAILS", 0.95, 300, 30, 1000, "U-SQUIGGLE Plastic Boxes"));
        database.addPart(new Outsourced(4, "Box - 100 2INCH NAILS", 0.95, 300, 30, 1000, "U-SQUIGGLE Plastic Boxes"));
        database.addPart(new InHouse(5, "Hammer", 0.95, 300, 30, 1000, 24873));
        database.addPart(new InHouse(6, "Screwdriver - Flathead", 0.95, 300, 30, 1000, 24874));
        database.addPart(new InHouse(7, "Screwdriver - Phillips", 0.95, 300, 30, 1000, 24874));
        database.addPart(new InHouse(8, "Nail - 1 inch", 0.95, 300, 30, 1000, 17984));
        database.addPart(new InHouse(9, "Nail - 2 inch", 0.95, 300, 30, 1000, 17984));
        database.addPart(new InHouse(10, "Screw - Flathead - 1 inch", 0.95, 300, 30, 1000, 17982));
        database.addPart(new InHouse(11, "Screw - Phillips - 1 inch", 0.95, 300, 30, 1000, 17983));
        database.addProduct(new Product(1, "Screwdriver Set - Phillips", 7.95, 300, 30, 1000));
        database.getAllProducts().get(0).addAssociatedPart(database.getAllParts().get(6));
        database.getAllProducts().get(0).addAssociatedPart(database.getAllParts().get(10));
        database.addProduct(new Product(2, "Screwdriver Set - Flathead", 7.95, 300, 30, 1000));
        database.getAllProducts().get(1).addAssociatedPart(database.getAllParts().get(5));
        database.getAllProducts().get(1).addAssociatedPart(database.getAllParts().get(9));

        // Loading the main screen FXML data into the application.
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

    /*
    Utility functions for all classes.  Makes code simpler.
     */

    /**
     * Generates a popup window with a notification message and a YES or NO option.
     * @param notificationText The text to put in the message box.
     * @return true if the user presses YES, else false
     */
    public static boolean confirmationMessage(String notificationText){
        Alert error = new Alert(Alert.AlertType.CONFIRMATION, notificationText, ButtonType.YES, ButtonType.NO);
        error.showAndWait();
        return error.getResult() == ButtonType.YES;  //Returns true if yes, else return false.
    }

    /**
     * Generates a popup window with an OK button and an error message.
     * @param errorText The error message to display.
     */
    public static void errorMessage(String errorText){
        Alert error = new Alert(Alert.AlertType.ERROR, errorText, ButtonType.OK);
        error.showAndWait();
        //if(error.getResult() == ButtonType.OK){}
    }

}
