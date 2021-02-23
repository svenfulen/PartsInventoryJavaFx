package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class addProduct_Controller {

    // Buttons and Form Fields
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

    // Associated Parts table
    @FXML private TableView<Part> associated_part_table;
    @FXML private TableColumn<Part, Integer> associated_part_id_column;
    @FXML private TableColumn<Part, String> associated_part_name_column;
    @FXML private TableColumn<Part, Integer> associated_part_inv_column;
    @FXML private TableColumn<Part, Double> associated_part_price_column;


    /** Temporary list for Associated Parts form
     * Held temporarily so that the user can add/remove the desired associated parts before saving.
     */
    private ObservableList<Part> newAssociatedPartsList = FXCollections.observableArrayList();


    public void initialize(){
        showAllParts();  // Initialize the parts table.
        showAllAssociatedParts();  // Initialize the Associated parts table.
    }


    /*
    All parts & Associated Parts tables.
     */


    /**
     * Called when text is typed into the search bar.
     * @see Inventory, lookupPart();
     * Makes a resultsList and applies it to the Parts table.
     */
    public void searchParts(){
        // Get the text input into the search bar.
        String input = add_product_part_search.getText();

        // Create a list to hold search results.
        javafx.collections.ObservableList<Part> resultsList = FXCollections.observableArrayList();

        // @exception If the input text cannot be converted to an int, search by string.
        try {
            int intInput = Integer.parseInt(input);
            resultsList.add(Main.database.lookupPart(intInput)); // The Part is appended to the results list.
        } catch(NumberFormatException notInteger) {
            resultsList = Main.database.lookupPart(input);  // All parts containing the string are returned.
        }

        // If there are items in the list.
        if(resultsList.size() > 0){
            // If the index returned is not a dummy variable.
            if(resultsList.get(0).getId() != 2000000000) {
                // Show all the search results.
                add_product_part_table.getItems().setAll(resultsList);
            }
            else{
                // No results were found, clear the list.
                add_product_part_table.getItems().clear();
            }
        }
        else {
            // No results were found, clear the list.
            add_product_part_table.getItems().clear();
        }
    }

    /**
     * Applies the list of parts in the database to the Parts table.
     */
    private void showAllParts(){
        // If there are no items in the table, this will be displayed.
        add_product_part_table.setPlaceholder(new Label("Part not found"));

        //Set all the columns to take the correct types from Part objects.
        product_part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        product_part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        product_part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        product_part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column

        // Use the parts database to output to the table.
        add_product_part_table.getItems().setAll(Main.database.getAllParts());
    }

    /**
     * Applies the temporary list of Associated Parts to the Associated Parts table.
     */
    private void showAllAssociatedParts(){
        // If there are no items in the table, this will be displayed.
        associated_part_table.setPlaceholder(new Label("There are no parts associated with this product."));

        //Set all the columns to take the correct types from Part objects.
        associated_part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        associated_part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        associated_part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        associated_part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column

        // Use the parts database to output to the table.
        associated_part_table.getItems().setAll(newAssociatedPartsList);
    }

    /**  Handles adding a part from Parts table to Associated Parts table.
        FUTURE ENHANCEMENT: Be able to add a certain quantity of an associated part to the associated parts table.
        For example, you could add 100 nails and a plastic box into a Box of 100 Nails Product.  */
    public void addAssociatedPart(){
        //Add the selected item from the Parts list to the Associated Parts list.
        Part partToAdd = add_product_part_table.getSelectionModel().getSelectedItem();
        newAssociatedPartsList.add(partToAdd);

        //Refresh the associated parts table.
        showAllAssociatedParts();
    }

    /**
     * Removes a selected part from the Associated Parts table.
     */
    public void removeAssociatedPart(){
        // Get the selected part, and remove it from the associated parts list.
        Part partToRemove = associated_part_table.getSelectionModel().getSelectedItem();
        newAssociatedPartsList.remove(partToRemove);

        //Refresh the associated parts table.
        showAllAssociatedParts();
    }


    /*
    Product ID auto gen
    */

    // The next part ID will be the size of all parts + 1.
    public static int nextProductId = Main.database.getAllProducts().size();

    /** @return a product ID contiguous with the last product in the allProducts list.
        FUTURE ENHANCEMENT: Be able to fill product IDs from products that were deleted.  */
    public static int autoGenProductId(){
        nextProductId++;
        return nextProductId;
    }


    /*
    Cancel and Save buttons
    */

    /**
     * Closes the window.
     */
    public void cancel(){
        //Gets the window that the cancel button is in.
        Stage stage = (Stage) cancel_button.getScene().getWindow();

        // Makes the cancel button close the window.
        stage.close();
    }

    /**
     * Saves a product to the database.
     * Checks for errors in user input.
     */
    public void saveProduct(){
        // If an input error occurs, this value will be set to false and the product will not save.
        boolean productCanSave = true;

        // Initializing variables that will later be filled and used as parameters.
        String partName = this.product_name_field.getText();
        int inStock = 0;
        double price = 0.0;
        int min = 0;
        int max = 0;

        // Error checking for user input.

        // @exception If the user enters a non-int into the stock field, do not save the product.
        try{ inStock = Integer.parseInt(this.product_inv_field.getText()); }
        catch(NumberFormatException notAnInt){
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Inv field.");
        }

        // @exception If the user enters a non-double into the price field, do not save the product.
        try{ price = Double.parseDouble(this.product_price_field.getText()); }
        catch(NumberFormatException | NullPointerException notADouble) {
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Price/Cost field.");
        }

        // @exception If the user enters a non-int into the min field, do not save the product.
        try{ min = Integer.parseInt(this.product_min_field.getText()); }
        catch(NumberFormatException notAnInt){
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Min field.");
        }

        // @exception If the user enters a non-int into the max field, do not save the product.
        try{ max = Integer.parseInt(this.product_max_field.getText()); }
        catch(NumberFormatException notAnInt){
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Max field.");
        }

        // Checking if Min is less than Max.
        if (min > max) {
            productCanSave = false;
            Main.errorMessage("Max must be greater than Min.");
        }

        // Checking if inStock is between Min and Max.
        // FUTURE ENHANCEMENT: Allow user to enter in a value below the Min. (what if a part goes out of stock?)
        if (inStock < min | inStock > max) {
            productCanSave = false;
            Main.errorMessage("Inv must be between Min and Max values.");
        }

        //This block can only execute if the form is filled out correctly.
        if(productCanSave){
            // Creates a Product object to add to the database.
            Product newProduct = new Product(autoGenProductId(), partName, price, inStock, min, max);

            // Adds all associated parts in the temporary list to the product's associated parts list.
            for (Part part : newAssociatedPartsList) {
                newProduct.addAssociatedPart(part);
            }

            // Add the Product to the database.
            Main.database.addProduct(newProduct);

            // Close the window.
            cancel();
        }
    }

}
