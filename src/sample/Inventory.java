package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    /** Database of all parts */
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Database of all products. */
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** @return A list of all parts that are currently stored in the database. */
    public ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** @return A list of all products that are currently stored in the database. */
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Adds a part to the database.
     * @param newPart an InHouse or Outsourced object.*/
    public void addPart(Part newPart){ allParts.add(newPart); }

    /** Adds a product to the database.
     * @param newProduct A product object. */
    public void addProduct(Product newProduct) { allProducts.add(newProduct); }

    /**
    Used for searching for parts, without case sensitivity.
    @param string1 is for the item in the database
    @param string2 is for the search text given by the user
    @return true if string2 matches a part of string1, else return false
     */
    public boolean matches(String string1, String string2){
        if(string1.toLowerCase().equals(string2.toLowerCase())) {
            return true;
        }
        else if(string1.toLowerCase().contains(string2.toLowerCase())){
            return true;
        }
        else {
            return false;
        }
    }

    /**
    Lookup part function
    @param partId int or string, int will search by part id, string will search by part name
    @return a list of results found, or a single product if searched by id
    FUTURE ENHANCEMENT: allow to search ints (what if the part name is a number, or starts with a number?)
     */
    public Part lookupPart(int partId){
        // FUTURE ENHANCEMENT: do a binary search rather than a for-each search.
        for (Part allPart : allParts) {
            if (allPart.getId() == partId) {
                return allPart;
            }
        }
        // @return dummy object with ID of 2 billion.  If this value is returned, there were no results.
        // FUTURE ENHANCEMENT: make this return an empty observable list rather than a dummy variable.
        return new InHouse(2000000000, "No Parts Found", 0, 0, 0, 0, 0);
    }

    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> resultsList = FXCollections.observableArrayList();
        // FUTURE ENHANCEMENT: Sort, and then do a binary search rather than a for-each search.
        for (Part allPart : allParts) {
            if (matches(allPart.getName(), partName)){
                resultsList.add(allPart);
            }
        }
        // @return all the matches found.
        return resultsList;
    }

    /**
    Lookup product function
    @param productId int or string, int will search by product id, string will search by product name
    @return a list of results found, or a single product if searched by id
    FUTURE ENHANCEMENT: search by string only (what if the product name is a number, or starts with a number?)
    */
    public Product lookupProduct(int productId){
        // FUTURE ENHANCEMENT: do a binary search rather than a for-each search.
        for (Product allProduct : allProducts) {
            if (allProduct.getId() == productId) {
                return allProduct;
            }
        }
        // @return dummy object with ID of 2 billion.  If this value is returned, there were no results.
        // FUTURE ENHANCEMENT: make this return an empty observable list rather than a dummy variable.
        return new Product(2000000000, "No Parts Found", 0, 0, 0, 0);
    }

    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> resultsList = FXCollections.observableArrayList();
        // FUTURE ENHANCEMENT: Sort, and then do a binary search rather than a for-each search.
        for (Product allProduct : allProducts){
            if (matches(allProduct.getName(), productName)){
                resultsList.add(allProduct);
            }
        }
        //@return a list of all matches found.
        return resultsList;
    }

    /**
     *
     * @param index index in the database of the part to update
     * @param selectedPart new part object that replaces the old part
     */
    public void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     *
     * @param index index in the database of the product to update
     * @param newProduct new product object to replace the old product
     */
    public void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     *
     * @param selectedPart The part object in the database to delete.
     * @return true if the part is deleted, false if the part is not deleted.
     * FUTURE ENHANCEMENT: have permanent parts that cannot be deleted, to be set by an admin
     */
    public boolean deletePart(Part selectedPart){
        if(allParts.remove(selectedPart)) {
            return true;
        }
        Main.errorMessage("The program was unable to delete the selected part.");
        return false;
    }

    /**
     *
     * @param selectedProduct The product object in the database to delete.
     * @return true if the part is deleted, false if the part is not deleted.
     */
    public boolean deleteProduct(Product selectedProduct){
        if(allProducts.remove(selectedProduct)) {
            return true;
        }
        Main.errorMessage("The program was unable to delete the selected product.");
        return false;
    }

}
