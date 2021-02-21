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


    static int selectedPartIndex;

    static void setSelectedPartIndex(int index) {
        selectedPartIndex = index;
    }

    public void initialize(){
        if(Main.database.getAllParts().get(selectedPartIndex) instanceof InHouse){
            int selectedMachineID = ((InHouse) Main.database.getAllParts().get(selectedPartIndex)).getMachineId();
            boolean isOutsourced = false;
            radioInHouse.setSelected(true);
            radioOutsourced.setSelected(false);
            partTypeField.setText(String.valueOf(selectedMachineID));
        }
        if(Main.database.getAllParts().get(selectedPartIndex) instanceof Outsourced){
            String selectedCompanyName = ((Outsourced) Main.database.getAllParts().get(selectedPartIndex)).getCompanyName();
            boolean isOutsourced = true;
            radioInHouse.setSelected(false);
            radioOutsourced.setSelected(true);
            partTypeField.setText(selectedCompanyName);
        }
        int selectedPartId = Main.database.getAllParts().get(selectedPartIndex).getId();
        String selectedPartName = Main.database.getAllParts().get(selectedPartIndex).getName();
        double selectedPartPrice = Main.database.getAllParts().get(selectedPartIndex).getPrice();
        int selectedPartStock = Main.database.getAllParts().get(selectedPartIndex).getStock();
        int selectedPartMin = Main.database.getAllParts().get(selectedPartIndex).getMin();
        int selectedPartMax = Main.database.getAllParts().get(selectedPartIndex).getMax();

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

        int partId = 0;
        int partIndex = selectedPartIndex;
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

        // Checking inv stuff.
        if (min > max) {
            partCanSave = false;
            errorMessage("Max must be greater than Min.");
        }

        if (inStock < min | inStock > max) {
            partCanSave = false;
            errorMessage("Inv must be between Min and Max values.");
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

    private void errorMessage(String errorText){
        Alert error = new Alert(AlertType.ERROR, errorText, ButtonType.OK);
        error.showAndWait();
        //if(error.getResult() == ButtonType.OK){}
    }

}