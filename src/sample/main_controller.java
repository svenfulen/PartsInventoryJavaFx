package sample;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class main_controller {
    // Parts search bar
    @FXML private TextField part_search_bar;
    // Parts table
    @FXML private TableView<Part> parts_table;  //Makes the Parts table use Part objects.
    @FXML private TableColumn<Part, Integer> part_id_column;
    @FXML private TableColumn<Part, String> part_name_column;
    @FXML private TableColumn<Part, Integer> part_inv_column;
    @FXML private TableColumn<Part, Double> part_price_column;

    //Products search bar
    @FXML private TextField product_search_bar;
    //Products table
    @FXML private TableView<Product> products_table;  //Makes the Products table use Product objects.
    @FXML private TableColumn<Product, Integer> product_id_column;
    @FXML private TableColumn<Product, String> product_name_column;
    @FXML private TableColumn<Product, Integer> product_inv_column;
    @FXML private TableColumn<Product, Double> product_price_column;

    // Everything to do when the Scene starts.
    public void initialize() {
        showAllParts();
        showAllProducts();
    }

    /**
     * Fill the parts table with data.  Can be called to refresh the parts table.
     */
    public void showAllParts(){
        parts_table.setPlaceholder(new Label("Part not found"));
        part_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Part ID column
        part_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        part_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        part_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        parts_table.getItems().setAll(Main.database.getAllParts());  // Use the parts database to output to the table.
    }

    /**
     * Fill the products table with data.  Can be called to refresh the products table.
     */
    public void showAllProducts(){
        products_table.setPlaceholder(new Label("Product not found"));
        product_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));  //Product ID column
        product_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));  // Name column
        product_inv_column.setCellValueFactory(new PropertyValueFactory<>("stock"));  // Inv column
        product_price_column.setCellValueFactory(new PropertyValueFactory<>("price"));  // Price column
        products_table.getItems().setAll(Main.database.getAllProducts());  // Use the products database to output to the table.
    }

    /**
     * @see Inventory Searches for parts in the allParts list.  Called when text is entered into the search bar.
     */
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

    /**
     * @see Inventory Searches for products in the allProducts list.  Called when text is entered into the search bar.
     */
    public void searchProducts(){
        String input = product_search_bar.getText();  // The text that is input to the search bar.
        javafx.collections.ObservableList<Product> resultsList = FXCollections.observableArrayList();

        // If the input text cannot be converted to an int, search by string.
        try {
            int intInput = Integer.parseInt(input);
            resultsList.add(Main.database.lookupProduct(intInput)); // The Part is appended to the results list.
        } catch(NumberFormatException notInteger) {
            resultsList = Main.database.lookupProduct(input);  // All parts containing the string are returned.
        }

        if(resultsList.size() > 0){
            if(resultsList.get(0).getId() != 2000000000) {
                products_table.getItems().setAll(resultsList);
            }
            else{
                products_table.getItems().clear();
            }
        }
        else {
            products_table.getItems().clear();
        }
    }

    /**
     * Remove the selected item in the Parts table.
     */
    public void removePart(){
        Part selectedPart = parts_table.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            if (Main.confirmationMessage("Are you sure you want to delete this part?")) {
                Main.database.deletePart(selectedPart);
            }
        }
        showAllParts();
    }

    /**
     * Remove the selected item in the Products table.
     */
    public void removeProduct(){
        Product selectedProduct = products_table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                if (Main.confirmationMessage("This product has parts associated. Are you sure you want to delete it?")) {
                    Main.database.deleteProduct(selectedProduct);
                }
            }
            else {
                if (Main.confirmationMessage("Are you sure you want to delete this product?")) {
                    Main.database.deleteProduct(selectedProduct);
                }
            }
        }
        showAllProducts();
    }

    /**
     * Opens the Add Part window.
     * @throws IOException
     */
    public void addPartScreen() throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("addPart_scene.fxml"));
        Parent root = (Parent) loadFXML.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> showAllParts());  // Refreshes the parts table when the window is closed.
    }

    /**
     * Opens the Modify Part window.
     * RUNTIME ERROR: When no item is selected in the table, clicking the Modify button will cause the function to throw an error.
     * fixed by only completing function if selectedPart not null
     * @throws IOException if the window fails to open, throw an exception
     */
    public void modifyPartScreen() throws IOException {
        Part selectedPart = parts_table.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("modifyPart_scene.fxml"));
            modifyPart_Controller.selectedPart = selectedPart;
            Parent root = (Parent) loadFXML.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> showAllParts());  // Refreshes the parts table when the window is closed.
        }
    }

    /**
     * Open the Add Product window.
     * @throws IOException if the window fails to open, throw an exception
     */
    public void addProductScreen() throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("addProduct_scene.fxml"));
        Parent root = (Parent) loadFXML.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(event -> showAllProducts());  // Refreshes the parts table when the window is closed.
    }

    /**
     * Open the Modify Product screen.
     * RUNTIME ERROR: When no item is selected in the table, clicking the Modify button will cause the function to throw an error.
     * fixed by only completing function if selectedProduct not null
     * @throws IOException if the window fails to open, throw an exception.
     */
    public void modifyProductScreen() throws IOException {
        Product selectedProduct = products_table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("modifyProduct_scene.fxml"));
            modifyProduct_Controller.selectedProduct = selectedProduct;
            Parent root = (Parent) loadFXML.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(event -> showAllProducts());  // Refreshes the parts table when the window is closed.
        }
    }




}

