package sample;

public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public void setCompanyName(String name){
        this.companyName = name;
    }

    public String getCompanyName(){
        return this.companyName;
    }
}
