package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class addProduct_Controller {
    /*
    FXML signatures
     */
    @FXML private Button cancel_button;
    @FXML private TextField product_name_field;
    @FXML private TextField product_inv_field;
    @FXML private TextField product_price_field;
    @FXML private TextField product_min_field;
    @FXML private TextField product_max_field;

    // Parts Table
    @FXML private TextField add_product_part_search;  // Search bar
    @FXML private TableView<Part> add_product_part_table;
    @FXML private TableColumn<Part, Integer> product_part_id_column;
    @FXML private TableColumn<Part, String> product_part_name_column;
    @FXML private TableColumn<Part, Integer> product_part_inv_column;
    @FXML private TableColumn<Part, Double> product_part_price_column;

    //Associated Parts table
    @FXML private TableView<Part> associated_part_table;
    @FXML private TableColumn<Part, Integer> associated_part_id_column;
    @FXML private TableColumn<Part, String> associated_part_name_column;
    @FXML private TableColumn<Part, Integer> associated_part_inv_column;
    @FXML private TableColumn<Part, Double> associated_part_price_column;


    //Global list for associated parts.
    private ObservableList<Part> newAssociatedPartsList = FXCollections.observableArrayList();

    public void initialize(){
        showAllParts();  // Initialize the parts table.
        showAllAssociatedParts();  // Initialize the Associated parts table.
    }


    /*
    All parts & Associated Parts tables.
     */

    //Handles the search bar for product screen.
    public void searchParts(){
        String input = add_product_part_search.getText();  // The text that is input to the search bar.
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
                add_product_part_table.getItems().setAll(resultsList);
            }
            else{
                add_product_part_table.getItems().clear();
            }
        }
        else {
            add_product_part_table.getItems().clear();
        }
    }

    //Handles all parts table for product screen.
    private void showAllParts(){
        add_product_part_table.setPlaceholder(new Label("Part not found"));
        product_part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        product_part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        product_part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        product_part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        add_product_part_table.getItems().setAll(Main.database.getAllParts());  // Use the parts database to output to the table.
    }

    //Handles associated parts table.
    private void showAllAssociatedParts(){
        associated_part_table.setPlaceholder(new Label("There are no parts associated with this product."));
        associated_part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        associated_part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        associated_part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        associated_part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        associated_part_table.getItems().setAll(newAssociatedPartsList);  // Use the parts database to output to the table.
    }

    //Handles adding a part from Parts table to Associated Parts table.
    public void addAssociatedPart(){
        Part partToAdd = add_product_part_table.getSelectionModel().getSelectedItem();
        newAssociatedPartsList.add(partToAdd);
        showAllAssociatedParts();  //Refresh the associated parts table.
    }

    //Handles removing a part from the Associated parts table.
    public void removeAssociatedPart(){
        Part partToRemove = associated_part_table.getSelectionModel().getSelectedItem();
        newAssociatedPartsList.remove(partToRemove);
        showAllAssociatedParts();  //Refresh the associated parts table.
    }


    /*
    Product ID auto gen
    */
    public static int nextProductId = Main.database.getAllProducts().size(); //The next part ID will be the size of all parts + 1.
    public static int autoGenProductId(){
        nextProductId++;
        return nextProductId;
    }

    /*
    Cancel and Save buttons
    */
    public void cancel(){
        Stage stage = (Stage) cancel_button.getScene().getWindow(); //Gets the window that the cancel button is in.
        stage.close(); // Makes the cancel button close the window.
    }

    public void saveProduct(){

    }
}
