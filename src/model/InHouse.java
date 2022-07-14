package model;

/**
 * A subclass of the Part class: This class creates inHouse parts.
 * @author Jake
 */
public class InHouse extends Part{
    
	private int machineId; //A unique field to this subclass
	
        /**
         * This constructor initializes the values for the fields id,name,price,stock,min,max, and machineId.
         * @param id The part's Id.
         * @param name The part's name.
         * @param price The part's price.
         * @param stock The part's current inventory level.
         * @param min The part's minimum stock.
         * @param max The part's maximum stock.
         * @param machineId The part's machineId.
         */
	public InHouse(int id, String name, double price,int stock, int min, int max, int machineId){
		
                super(id, name, price, stock, min, max);
                this.machineId = machineId;
	}
	
        /**
         * This method is a setter for the field machineId.
         * @param machineId The part's machine Id
         */
	public void setMachineId(int machineId){
		this.machineId = machineId;
	}
	
        /**
         * This method is a getter for the field machineId.
         * @return The machineId of a part.
         */
	public int getMachineId(){
		return machineId;
	}
}