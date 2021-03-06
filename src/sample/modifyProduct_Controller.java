package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class modifyProduct_Controller {
    /*
    FXML signatures
     */
    @FXML private Button cancel_button;
    @FXML private TextField product_name_field;
    @FXML private TextField product_inv_field;
    @FXML private TextField product_price_field;
    @FXML private TextField product_min_field;
    @FXML private TextField product_max_field;
    @FXML private TextField product_id_field;

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

    /**
     * @see main_controller modifyProductScreen passes in a Product object to the selectedProduct variable.
     */
    public static Product selectedProduct;

    /**
     * The index of the selected Product in the allProducts list.
     */
    private int productIndex = Main.database.getAllProducts().indexOf(selectedProduct);

    /**
     * A list of parts that gets applied to the new Product object.
     */
    private ObservableList<Part> newAssociatedPartsList = selectedProduct.getAllAssociatedParts();

    /**
     * Populate the form with data from the selectedProduct object.
     */
    public void initialize(){
        showAllParts();  // Initialize the parts table.
        showAllAssociatedParts();  // Initialize the Associated parts table.

        product_id_field.setText(String.valueOf(selectedProduct.getId()));
        product_name_field.setText(selectedProduct.getName());
        product_inv_field.setText(String.valueOf(selectedProduct.getStock()));
        product_price_field.setText(String.valueOf(selectedProduct.getPrice()));
        product_min_field.setText(String.valueOf(selectedProduct.getMin()));
        product_max_field.setText(String.valueOf(selectedProduct.getMax()));

    }


    /*
    All parts & Associated Parts tables.
     */

    /**
     * Searches for parts in the Parts table.  Called when text is entered into the search bar.
     */
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


    /**
     * @see Inventory Populate the Parts table with data from the allParts list.
     * Can be called to refresh the Parts table.
     */
    private void showAllParts(){
        add_product_part_table.setPlaceholder(new Label("Part not found"));
        product_part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        product_part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        product_part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        product_part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        add_product_part_table.getItems().setAll(Main.database.getAllParts());  // Use the parts database to output to the table.
    }

    /**
     * Populate the Associated Parts table with data from the newAssociatedPartsList object.
     * Can be called to refresh the Associated Parts table.
     */
    private void showAllAssociatedParts(){
        associated_part_table.setPlaceholder(new Label("There are no parts associated with this product."));
        associated_part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        associated_part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        associated_part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        associated_part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        associated_part_table.getItems().setAll(newAssociatedPartsList);  // Use the parts database to output to the table.
    }

    /**
     * Retrieves the selected item from the Parts table, and adds the selected item to the Associated Parts list.
     */
    public void addAssociatedPart(){
        Part partToAdd = add_product_part_table.getSelectionModel().getSelectedItem();
        newAssociatedPartsList.add(partToAdd);
        showAllAssociatedParts();  //Refresh the associated parts table.
    }

    /**
     * Retrieves the selected item from the Associated Parts table, and removes it from the Associated Parts list.
     */
    public void removeAssociatedPart(){
        Part partToRemove = associated_part_table.getSelectionModel().getSelectedItem();
        newAssociatedPartsList.remove(partToRemove);
        showAllAssociatedParts();  //Refresh the associated parts table.
    }


    /*
    Product ID auto gen
    */
    public static int nextProductId = Main.database.getAllProducts().size(); //The next part ID will be the size of all parts + 1.

    /**
     * Generate a product ID that is contiguous with the last product in the allProducts list.
     * @see Inventory
     * @return A new product ID.
     */
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
        Stage stage = (Stage) cancel_button.getScene().getWindow(); //Gets the window that the cancel button is in.
        stage.close(); // Makes the cancel button close the window.
    }

    /**
     * Saves the product to the allProducts list in the main database.
     * @see Inventory
     * Checks for user input errors before saving the product.
     */
    public void saveProduct(){
        boolean productCanSave = true;

        String partName = this.product_name_field.getText();
        int inStock = 0;
        double price = 0.0;
        int min = 0;
        int max = 0;

        try{ inStock = Integer.parseInt(this.product_inv_field.getText()); }
        catch(NumberFormatException notAnInt){
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Inv field.");
        }
        try{ price = Double.parseDouble(this.product_price_field.getText()); }
        catch(NumberFormatException | NullPointerException notADouble) {
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Price/Cost field.");
        }
        try{ min = Integer.parseInt(this.product_min_field.getText()); }
        catch(NumberFormatException notAnInt){
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Min field.");
        }
        try{ max = Integer.parseInt(this.product_max_field.getText()); }
        catch(NumberFormatException notAnInt){
            productCanSave = false;
            Main.errorMessage("Please enter a number into the Max field.");
        }

        // Checking inv stuff.
        if (min > max) {
            productCanSave = false;
            Main.errorMessage("Max must be greater than Min.");
        }

        if (inStock < min | inStock > max) {
            productCanSave = false;
            Main.errorMessage("Inv must be between Min and Max values.");
        }

        //This block can only execute if the form is filled out correctly.
        if(productCanSave){
            Product newProduct = new Product(autoGenProductId(), partName, price, inStock, min, max);
            // Adds all associated parts to the product.
            for(int i = 0; i < newAssociatedPartsList.size(); i++){
                newProduct.addAssociatedPart(newAssociatedPartsList.get(i));
            }
            Main.database.updateProduct(productIndex, newProduct);
            System.out.println("Successfully added Product");
            cancel(); //closes the window
        }
    }

}
