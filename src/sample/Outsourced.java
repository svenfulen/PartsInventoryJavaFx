package sample;

public class Outsourced extends Part{
    private String companyName;

    /**
     * Outsourced Part constructor
     * @param id The ID of the part.  Use autoGenPartId() for a non-conflicting, contiguous ID.
     * @param name The name of the part.
     * @param price double, the price of the part.
     * @param stock The amount of parts currently in inventory. (Inv field)
     * @param min The minimum amount of parts that can be in inventory.
     * @param max The maximum amount of parts that can be in inventory.
     * @param companyName The name of the company where the part originated.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the company name of the Outsourced (Part) object.
     * @param name String, the name of the company to set.
     */
    public void setCompanyName(String name){
        this.companyName = name;
    }

    /**
     *
     * @return String, the company name.
     */
    public String getCompanyName(){
        return this.companyName;
    }
}
