package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//TODO: might need to change some things to say this.allParts or this.allProducts depending on if the code works
public class Inventory {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public ObservableList<Part> getAllParts(){
        return allParts;
    }

    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    // @param either an InHouse or Outsourced object
    public void addPart(Part newPart){ allParts.add(newPart); }
    public void addProduct(Product newProduct) { allProducts.add(newProduct); }

    public Part lookupPart(int partId){
        // Sequential search, not optimized
        for (Part allPart : allParts) {
            if (allPart.getId() == partId) {
                return allPart;
            }
        }
        //TODO: return all products if no matches are found.
        return allParts.get(0);
    }

    public Product lookupProduct(int productId){
        for (Product allProduct : allProducts) {
            if (allProduct.getId() == productId) {
                return allProduct;
            }
        }
        //TODO: return all products if no matches are found.
        return allProducts.get(0);
    }

    //TODO: have this return all products if no matches are found
    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> resultsList = FXCollections.observableArrayList();
        for (Part allPart : allParts) {
            if (allPart.getName().equals(partName)){
                resultsList.add(allPart);
            }
        }
        return resultsList;
    }

    //TODO: have this return all products if no matches are found
    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> resultsList = FXCollections.observableArrayList();
        for (Product allProduct : allProducts){
            if (allProduct.getName().equals(productName)){
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

    //TODO: fix the return value here
    public boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }

    //TODO: fix the return value here
    public boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }

}
