package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

//TODO: might need to change some things to say this.allParts or this.allProducts depending on if the code works
public class Inventory {

    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public ObservableList<Part> getAllParts(){
        return allParts;
    }

    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    // @param either an InHouse or Outsourced object
    public void addPart(Part newPart){ allParts.add(newPart); }
    public void addProduct(Product newProduct) { allProducts.add(newProduct); }

    //Check if two strings match in any way, upper or lowercase, etc.  Left, insert part/product name, right, insert search.
    public boolean matches(String string1, String string2){
        // If
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

    public Part lookupPart(int partId){
        // Sequential search, not optimized
        for (Part allPart : allParts) {
            if (allPart.getId() == partId) {
                return allPart;
            }
        }
        // Return 2 billion.  If id = 2 billion, no results.
        return new InHouse(2000000000, "No Parts Found", 0, 0, 0, 0, 0);
    }

    public Product lookupProduct(int productId){
        for (Product allProduct : allProducts) {
            if (allProduct.getId() == productId) {
                return allProduct;
            }
        }
        // Return 2 billion.  If id = 2 billion, no results.
        return new Product(2000000000, "No Parts Found", 0, 0, 0, 0);
    }

    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> resultsList = FXCollections.observableArrayList();
        for (Part allPart : allParts) {
            if (matches(allPart.getName(), partName)){
                resultsList.add(allPart);
            }
        }
        return resultsList;
    }

    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> resultsList = FXCollections.observableArrayList();
        for (Product allProduct : allProducts){
            if (matches(allProduct.getName(), productName)){
                resultsList.add(allProduct);
            }
        }
        return resultsList;
    }

    public void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }

    public boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }

}
