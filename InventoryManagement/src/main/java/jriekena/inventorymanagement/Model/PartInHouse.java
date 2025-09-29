package jriekena.inventorymanagement.Model;

/**
 * The In-House parts class.
 * Inherits from the Part class.
 */
public class PartInHouse extends Part{

    private int machineID;

    /**
     * The In-House part constructor.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public PartInHouse(int id, String name, double price, int stock, int min, int max, int machineID) {

        super(id, name, price, stock, min, max);
        this.machineID = machineID;

    }

    /**
     * Sets the machine ID for In-House parts.
     * @param machineID
     */
    public void setMachineID(int machineID) { this.machineID = machineID; }

    /**
     * Returns the machine ID for In-House Parts.
     * @return machineID
     */
    public int getMachineID() { return machineID; }

}
