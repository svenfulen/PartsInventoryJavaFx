package sample;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class addPart_Controller {
    /*
    FXML signatures
     */
    @FXML private Button SavePart;
    @FXML private Button cancelButton;
    @FXML private RadioButton radioInHouse;
    @FXML private RadioButton radioOutsourced;
    @FXML private Label locationLabel;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField partTypeField;


    /*
    Part ID auto gen
    */
    public static int nextPartId = Main.database.getAllParts().size(); //The next part ID will be the size of all parts + 1.
    public static int autoGenPartId(){
        nextPartId++;
        return nextPartId;
    }


    /*
    Radio Buttons
     */
    private boolean partIsOutsourced = false;

    public void setPartOutsourced(){
        if (!partIsOutsourced){
            partIsOutsourced = true;
        }
        this.locationLabel.setText("Company");
    }

    public void setPartInHouse(){
        if (partIsOutsourced){
            partIsOutsourced = false;
        }
        this.locationLabel.setText("Machine ID");
    }


    /*
    Cancel and Save buttons
     */
    public void cancel(){
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //Gets the window that the cancel button is in.
        stage.close(); // Makes the cancel button close the window.
    }

    public void savePart(){
        boolean partCanSave = true;

        String partName = this.nameField.getText();
        int inStock = 0;
        double price = 0.0;
        int min = 0;
        int max = 0;
        int machineID = 0;
        String companyName = "";

        try{ inStock = Integer.parseInt(this.invField.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            errorMessage("Please enter a number into the Inv field.");
        }
        try{ price = Double.parseDouble(this.priceField.getText()); }
        catch(NumberFormatException | NullPointerException notADouble) {
            partCanSave = false;
            errorMessage("Please enter a number into the Price/Cost field.");
        }
        try{ min = Integer.parseInt(this.minField.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            errorMessage("Please enter a number into the Min field.");
        }
        try{ max = Integer.parseInt(this.maxField.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            errorMessage("Please enter a number into the Max field.");
        }
        if (!partIsOutsourced){
            try{ machineID = Integer.parseInt(this.partTypeField.getText()); }
            catch(NumberFormatException notAnInt){
                partCanSave = false;
                errorMessage("Please enter a valid number into the Machine ID field.");
            }
        }
        if(partIsOutsourced){
            companyName = this.partTypeField.getText();
        }

        //This block can only execute if the form is filled out correctly.
        if(partCanSave){
            if (partIsOutsourced) {
                Outsourced newOutsourcedPart = new Outsourced(autoGenPartId(), partName, price, inStock, min, max, companyName);
                Main.database.addPart(newOutsourcedPart);
                System.out.println("Successfully added Outsourced part");
                cancel(); //closes the window
            }
            if(!partIsOutsourced) {
                InHouse newInHousePart = new InHouse(autoGenPartId(), partName, price, inStock, min, max, machineID);
                sample.Main.database.addPart(newInHousePart);
                System.out.println("Successfully added In-House part");
                cancel(); //closes the window
            }
        }
    }

    private void errorMessage(String errorText){
        Alert error = new Alert(AlertType.ERROR, errorText, ButtonType.OK);
        error.showAndWait();
        //if(error.getResult() == ButtonType.OK){}
    }

}