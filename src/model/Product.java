package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and manipulates Products.
 * @author Jake
 */
public class Product{
    
	private int id;
	private int stock;
	private int min;
	private int max;
	private double price;
	private String name;
        
        /**
         * This line caused logical errors before it was an instance variable. Making associated parts a static variable causes it to act like a pool for all of the product's associated parts.
         */
	private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        
        /**
         * This constructor initializes the product's id, stock, min, max, price,
         * and name. 
         * @param id The product's id
         * @param stock The product's stock
         * @param min The product's minimum stock
         * @param max The product's maximum stock
         * @param price The product's price
         * @param name The product's name
         */
	public Product(int id, int stock, int min, int max, double price, String name){
            
		this.id = id;
		this.stock = stock;
		this.min = min;
		this.max = max;
		this.price = price;
		this.name = name;
	}
	
        /**
         * setId is a setter for the id field.
         * @param id The product id
         */
	public void setId(int id){
            
		this.id = id;
	}
	
        /**
         * setStock is a setter for the stock field.
         * @param stock The product inventory level 
         */
	public void setStock(int stock){
            
		this.stock = stock;
	}
	
        /**
         * setMin is a setter for the min field.
         * @param min The product min
         */
	public void setMin(int min){
            
		this.min = min;
	}
	
        /**
         * setMax is a setter for the max field.
         * @param max The product max
         */
	public void setMax(int max){
            
		this.max = max;
	}
	
        /**
         * setPrice is a setter for the price field.
         * @param price The product price
         */
	public void setPrice(double price){
            
		this.price = price;
	}
	
        /**
         * setName is a setter for the name field.
         * @param name The product name
         */
	public void setName(String name){
            
		this.name = name;
	}
	
        /**
         * getId is a getter for the id field.
         * @return The value in the id field.
         */
	public int getId(){
            
		return id;
	}
	
        /**
         * getStock is a getter for the stock field.
         * @return The value in the stock field.
         */
	public int getStock(){
            
		return stock;
	}
	
        /**
         * getMin is a getter for the min field.
         * @return The value in the min field.
         */
	public int getMin(){
            
		return min;
	}
	
        /**
         * getMax is a getter for the max field.
         * @return The value in the max field.
         */
	public int getMax(){
            
		return max;
	}
	
        /**
         * getPrice is a getter for the price field.
         * @return The value in the price field.
         */
	public double getPrice(){
            
		return price;
	}
	
        /**
         * getName is a getter for the name field.
         * @return The value in the name field.
         */
	public String getName(){
            
		return name;
	}
	
        /**
         * This method adds a part to the Observable List associatedParts.
         * @param part A selected part
         */
	public void addAssociatedPart(Part part){
            
            associatedParts.add(part);
	}
	
        /**
         * This method deletes a part from the Observable List associatedParts.
         * @param selectedAssociatedPart A selected part
         * @return true if the product was removed, else false.
         */
        public boolean deleteAssociatedPart(Part selectedAssociatedPart){
		
            return associatedParts.remove(selectedAssociatedPart);
	}
	
        /**
         * This method is a getter for the field associatedParts.
         * @return The Observable List of associated parts.
         */
	public ObservableList<Part> getAllAssociatedParts(){
            
           return associatedParts;  
        }
}