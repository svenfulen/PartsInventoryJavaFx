package sample;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class addPart_Controller {

    // Variables linked to FXML elements
    @FXML private Button cancel_button;
    @FXML private Label location_label;
    @FXML private TextField name_field;
    @FXML private TextField inv_field;
    @FXML private TextField price_field;
    @FXML private TextField min_field;
    @FXML private TextField max_field;
    @FXML private TextField part_type_field;


    public static int nextPartId = Main.database.getAllParts().size(); //The next ID will be the number of parts + 1
    /**
     Part ID auto gen
     @return a part id for a new part, will be contiguous with the part added before it.
     @see addPart_Controller, public static int nextPartId = Main.database.getAllParts().size();
     */
    public static int autoGenPartId(){
        nextPartId++;
        return nextPartId;
    }


    /*
    Radio Buttons
    When pressing the radio buttons, these functions get called to set the boolean variable partIsOutsourced.
     */
    private boolean partIsOutsourced = false;

    /**
     * @see addPart_Controller, private boolean partIsOutSourced = false;
     * Sets the temporary boolean value partIsOutsourced to true
     * partIsOutsourced is later used to create an object of the correct type.
     */
    public void setPartOutsourced(){
        if (!partIsOutsourced){
            partIsOutsourced = true;
        }
        this.location_label.setText("Company");
    }

    /**
     * @see addPart_Controller, private boolean partIsOutSourced = false;
     * Sets the temporary boolean value partIsOutsourced to false
     * partIsOutsourced is later used to create an object of the correct type.
     */
    public void setPartInHouse(){
        if (partIsOutsourced){
            partIsOutsourced = false;
        }
        this.location_label.setText("Machine ID");
    }


    /*
    Cancel and Save buttons
     */

    /**
     * Closes the window.
     */
    public void cancel(){
        Stage stage = (Stage) cancel_button.getScene().getWindow(); // Gets the window that the cancel button is in.
        stage.close(); // Makes the cancel button close the window.
    }

    /**
     * Adds a new part to the database using the Add Part form data.
     * Checks user input to make sure data is entered correctly.
     * FUTURE ENHANCEMENT: if the part fails to add to the database, show the user an error message.
     */
    public void savePart(){
        // If an input error occurs, this value will be set to false and the part will not save.
        boolean partCanSave = true;

        // Initializing variables that will later be filled and used as parameters.
        String partName = this.name_field.getText();
        int inStock = 0;
        double price = 0.0;
        int min = 0;
        int max = 0;
        int machineID = 0;
        String companyName = "";

        // Error checking for user input

        // @exception If the user enters a non-int into the stock field, do not save the part.
        try{ inStock = Integer.parseInt(this.inv_field.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            Main.errorMessage("Please enter a number into the Inv field.");
        }

        // @exception If the user enters a non-double into the price field, do not save the part.
        try{ price = Double.parseDouble(this.price_field.getText()); }
        catch(NumberFormatException | NullPointerException notADouble) {
            partCanSave = false;
            Main.errorMessage("Please enter a number into the Price/Cost field.");
        }

        // @exception If the user enters a non-int into the min field, do not save the part.
        try{ min = Integer.parseInt(this.min_field.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            Main.errorMessage("Please enter a number into the Min field.");
        }

        // @exception If the user enters a non-int into the max field, do not save the part.
        try{ max = Integer.parseInt(this.max_field.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            Main.errorMessage("Please enter a number into the Max field.");
        }

        // Checking if the Max value is greater than the Min value.
        if (min > max) {
            partCanSave = false;
            Main.errorMessage("Max must be greater than Min.");
        }

        // Checking if the inStock value is between Min and Max.
        // FUTURE ENHANCEMENT: Allow user to enter in a value below the Min. (what if a part goes out of stock?)
        if (inStock < min | inStock > max) {
            partCanSave = false;
            Main.errorMessage("Inv must be between Min and Max values.");
        }

        // If the part is not outsourced, get the machine ID from the field as an int.
        if (!partIsOutsourced){
            // @exception if the user enters a non-int into the Machine ID field, do not save the part.
            try{ machineID = Integer.parseInt(this.part_type_field.getText()); }
            catch(NumberFormatException notAnInt){
                partCanSave = false;
                Main.errorMessage("Please enter a valid number into the Machine ID field.");
            }
        }

        // If the part is outsourced, get the Company Name from the field as a String.
        if(partIsOutsourced){
            companyName = this.part_type_field.getText();
        }

        // This block can only execute if the form is filled out correctly.
        if(partCanSave){
            if (partIsOutsourced) {
                // Creates a new outsourced part object with the form data.
                Outsourced newOutsourcedPart = new Outsourced(autoGenPartId(), partName, price, inStock, min, max, companyName);
                // Adds the new outsourced part to the database.
                Main.database.addPart(newOutsourcedPart);
                // Closes the window.
                cancel();
            }
            if(!partIsOutsourced) {
                // Creates a new in-house part object with the form data.
                InHouse newInHousePart = new InHouse(autoGenPartId(), partName, price, inStock, min, max, machineID);
                // Adds the new in-house part to the database.
                sample.Main.database.addPart(newInHousePart);
                // Closes the window.
                cancel();
            }
        }
    }


}