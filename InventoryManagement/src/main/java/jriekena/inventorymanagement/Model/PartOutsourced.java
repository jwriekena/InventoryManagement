package jriekena.inventorymanagement.Model;

/**
 * The Outsourced part class.
 * Inherits from the Part class.
 */
public class PartOutsourced extends Part{

    private String companyName;

    /**
     * The Outsourced part constructor.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public PartOutsourced(int id, String name, double price, int stock, int min, int max, String companyName) {

        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * Sets the company name for outsourced parts.
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the company name.
     * @return companyName.
     */
    public String getCompanyName() { return companyName; }

}
