package sample;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class main_controller {
    // Parts Controls
    @FXML private Button add_part;
    @FXML private TextField part_search_bar;

    // Parts table
    @FXML private TableView<Part> parts_table;  //Makes the Parts table use Part objects.
    @FXML private TableColumn<Part, Integer> part_id_column;
    @FXML private TableColumn<Part, String> part_name_column;
    @FXML private TableColumn<Part, Integer> part_inv_column;
    @FXML private TableColumn<Part, Double> part_price_column;


    // Everything to do when the Scene starts.
    public void initialize() {
        showAllParts();
    }

    // Fill the Parts table with data.  Can be used to refresh the table.
    public void showAllParts(){
        parts_table.setPlaceholder(new Label("Part not found"));
        part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        parts_table.getItems().setAll(Main.database.getAllParts());  // Use the parts database to output to the table.
    }

    //Called when part is searched using the search bar.
    public void searchParts(){
        String input = part_search_bar.getText();  // The text that is input to the search bar.
        javafx.collections.ObservableList<Part> resultsList = FXCollections.observableArrayList();

        // If the input text cannot be converted to an int, search by string.
        try {
            int intInput = Integer.parseInt(input);
            resultsList.add(Main.database.lookupPart(intInput)); // The Part is appended to the results list.
        } catch(NumberFormatException notInteger) {
            resultsList = Main.database.lookupPart(input);  // All parts containing the string are returned.
        }

        if(resultsList.size() > 0){
            if(resultsList.get(0).getId() != 2000000000) {
                parts_table.getItems().setAll(resultsList);
            }
            else{
                parts_table.getItems().clear();
            }
        }
        else {
            parts_table.getItems().clear();
        }
    }

    // Remove a selected part.
    public void removePart(){
        int selectedPartIndex = parts_table.getSelectionModel().getSelectedIndex();
        if (confirmationMessage("Are you sure you want to delete this part?")) {
            Main.database.deletePart(Main.database.getAllParts().get(selectedPartIndex));
        }
        showAllParts();
    }

    // Open the Add Part popup window.
    public void addPartScreen() throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("addPart_scene.fxml"));
        Parent root = (Parent) loadFXML.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> showAllParts());  // Refreshes the parts table when the window is closed.
    }

    public void modifyPartScreen() throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("modifyPart_scene.fxml"));
        modifyPart_Controller.selectedPartIndex = parts_table.getSelectionModel().getSelectedIndex();
        Parent root = (Parent) loadFXML.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> showAllParts());  // Refreshes the parts table when the window is closed.
    }

    // Makes a pop-up with buttons YES and NO, returns TRUE or FALSE
    private boolean confirmationMessage(String notificationText){
        Alert error = new Alert(Alert.AlertType.CONFIRMATION, notificationText, ButtonType.YES, ButtonType.NO);
        error.showAndWait();
        return error.getResult() == ButtonType.YES;
    }




}

