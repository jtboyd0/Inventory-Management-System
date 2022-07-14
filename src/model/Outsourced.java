package model;

/**
 * A subclass of the Part class: This class creates outsourced parts.
 * @author Jake
 */
public class Outsourced extends Part{
	
	private String companyName;//A unique field to this subclass
	
        /**
         * This constructor initializes the id,name,price,stock,min,max, and companyName of an Outsourced Part.
         * @param id The part's Id.
         * @param name The part's name.
         * @param price The part's price.
         * @param stock The part's current inventory level.
         * @param min The part's minimum stock.
         * @param max The part's maximum stock.
         * @param companyName The part's company name.
         */
	public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
		super(id, name, price, stock, min, max);
		this.companyName = companyName;
	}
	
        /**
         * This method is a setter for the companyName field.
         * @param companyName Is the company the part is from.
         */
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	
        /**
         * This method is a getter for the companyName field.
         * @return The value stored in companyName.
         */
	public String getCompanyName(){
		return companyName;	
	}
}