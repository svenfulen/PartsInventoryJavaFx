package sample;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class modifyPart_Controller {
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
    @FXML private TextField idField;

    /**
     * @see main_controller modifyPartScreen() passes in the selected item from the Parts table to this variable.
     */
    static Part selectedPart;

    /**
     * Used to store whether the part is Outsourced or In-House.
     */
    private boolean partIsOutsourced = true;

    /**
     * Populate the form with data from selectedPart object.
     */
    public void initialize(){

        if((selectedPart) instanceof InHouse){
            int selectedMachineID = ((InHouse) selectedPart).getMachineId();
            partIsOutsourced = false;
            radioInHouse.setSelected(true);
            radioOutsourced.setSelected(false);
            partTypeField.setText(String.valueOf(selectedMachineID));
        }
        if((selectedPart) instanceof Outsourced){
            String selectedCompanyName = ((Outsourced) selectedPart).getCompanyName();
            partIsOutsourced = true;
            radioInHouse.setSelected(false);
            radioOutsourced.setSelected(true);
            partTypeField.setText(selectedCompanyName);
        }
        int selectedPartId = selectedPart.getId();
        String selectedPartName = selectedPart.getName();
        double selectedPartPrice = selectedPart.getPrice();
        int selectedPartStock = selectedPart.getStock();
        int selectedPartMin = selectedPart.getMin();
        int selectedPartMax = selectedPart.getMax();

        idField.setText(String.valueOf(selectedPartId));
        nameField.setText(selectedPartName);
        priceField.setText(String.valueOf(selectedPartPrice));
        invField.setText(String.valueOf(selectedPartStock));
        minField.setText(String.valueOf(selectedPartMin));
        maxField.setText(String.valueOf(selectedPartMax));
    }

    /*
    Radio Buttons
     */

    /**
     * Called by pressing the radio button for Outsourced.
     */
    public void setPartOutsourced(){
        if (!partIsOutsourced){
            partIsOutsourced = true;
        }
        this.locationLabel.setText("Company");
    }

    /**
     * Called by pressing the radio button for In-House
     */
    public void setPartInHouse(){
        if (partIsOutsourced){
            partIsOutsourced = false;
        }
        this.locationLabel.setText("Machine ID");
    }


    /*
    Cancel and Save buttons
     */

    /**
     * Close the window.
     */
    public void cancel(){
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //Gets the window that the cancel button is in.
        stage.close(); // Makes the cancel button close the window.
    }

    /** Save the selected part.
     * Checks for user input errors before saving the selected part.
     */
    public void savePart(){
        boolean partCanSave = true;

        int partId = selectedPart.getId();;
        int partIndex = Main.database.getAllParts().indexOf(selectedPart);
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
            Main.errorMessage("Please enter a number into the Inv field.");
        }
        try{ price = Double.parseDouble(this.priceField.getText()); }
        catch(NumberFormatException | NullPointerException notADouble) {
            partCanSave = false;
            Main.errorMessage("Please enter a number into the Price/Cost field.");
        }
        try{ min = Integer.parseInt(this.minField.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            Main.errorMessage("Please enter a number into the Min field.");
        }
        try{ max = Integer.parseInt(this.maxField.getText()); }
        catch(NumberFormatException notAnInt){
            partCanSave = false;
            Main.errorMessage("Please enter a number into the Max field.");
        }

        // Checking inv stuff.
        if (min > max) {
            partCanSave = false;
            Main.errorMessage("Max must be greater than Min.");
        }

        if (inStock < min | inStock > max) {
            partCanSave = false;
            Main.errorMessage("Inv must be between Min and Max values.");
        }

        if (!partIsOutsourced){
            try{ machineID = Integer.parseInt(this.partTypeField.getText()); }
            catch(NumberFormatException notAnInt){
                partCanSave = false;
                Main.errorMessage("Please enter a valid number into the Machine ID field.");
            }
        }
        if(partIsOutsourced){
            companyName = this.partTypeField.getText();
        }

        //This block can only execute if the form is filled out correctly.
        if(partCanSave){
            if (partIsOutsourced) {
                Outsourced newOutsourcedPart = new Outsourced(partId, partName, price, inStock, min, max, companyName);
                Main.database.updatePart(partIndex, newOutsourcedPart);
                System.out.println("Successfully updated Outsourced part");
                cancel(); //closes the window
            }
            if(!partIsOutsourced) {
                InHouse newInHousePart = new InHouse(partId, partName, price, inStock, min, max, machineID);
                sample.Main.database.updatePart(partIndex, newInHousePart);
                System.out.println("Successfully updated In-House part");
                cancel(); //closes the window
            }
        }
    }


}