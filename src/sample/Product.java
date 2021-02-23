package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product constructor.
     * @param id The ID of the product. Use autoGenProductId() for a non-conflicting, contiguous ID.
     * @param name String, The name of the product.
     * @param price Double, the price of the product.
     * @param stock The amount of product units in stock. (Inv field)
     * @param min The minimum amount of units that can be in stock.
     * @param max The maximum amount of units that can be in stock.
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // Mutators
    /**
     * Set the product ID.
     * @param id int, the ID to set.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Set the product name.
     * @param name String, the name to set.
     */
    public void setName(String name){ this.name = name; }

    /**
     * Set the product price.
     * @param price double, the price to set.
     */
    public void setPrice(double price){ this.price = price; }

    /**
     * Set the amount of product units in stock.
     * @param stock int, the amount of units in stock.
     */
    public void setStock(int stock){ this.stock = stock; }

    /**
     * Set the minimum amount of product units that can be in stock.
     * @param min int, the minimum product units that can be in stock.
     */
    public void setMin(int min){ this.min = min; }

    /**
     * Set the maximum amount of product units that can be in stock.
     * @param max int, the maximum amount of product units that can be in stock.
     */
    public void setMax(int max){ this.max = max; }

    // Accessors

    /**
     *
     * @return the product id
     */
    public int getId(){ return this.id; }

    /**
     *
     * @return the product name
     */
    public String getName(){ return this.name; }

    /**
     *
     * @return the product price
     */
    public double getPrice(){ return this.price; }

    /**
     *
     * @return the units of the product that are in stock
     */
    public int getStock(){ return this.stock; }

    /**
     *
     * @return the minimum amount of units of the product that can be in stock
     */
    public int getMin(){ return this.min; }

    /**
     *
     * @return the maximum amount of units of the product that can be in stock
     */
    public int getMax(){ return this.max; }

    // Associated parts

    /**
     * Add an a part to the product's associated parts list.
     * FUTURE ENHANCEMENT: be able to add an entire ObservableList object rather than adding just one part.
     * @param part The part to add to the list of associated parts.
     */
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /**
     * Remove a part from the product's associated parts list.
     * @param selectedAssociatedPart The Part object to remove from the Associated Parts list.
     * @return true if the part was successfully deleted, else return false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(this.associatedParts.remove(selectedAssociatedPart)) {
            return true;
        }
        Main.errorMessage("The program was unable to remove the Associated Part.");
        return false;
    }

    /**
     *
     * @return an ObservableList of all the parts associated with this product.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return this.associatedParts;
    }

}
