package sample;

public class InHouse extends Part{
    /** FUTURE ENHANCEMENT: allow the machineId to be a string rather than an int. (could call the machine A3058 etc..)
    */
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** @param id the machine ID to set for the object. */
    public void setMachineId(int id) {
        this.machineId = id;
    }

    /** @return the machine ID of the given object. */
    public int getMachineId(){
        return this.machineId;
    }
}
