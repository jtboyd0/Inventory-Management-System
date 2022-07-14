package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class simulates an inventory of parts and products.
 * @author Jake
 */
public class Inventory{
        
	private static ObservableList<Part> allParts = FXCollections.observableArrayList(); //Creates the observable list of Parts.
	private static ObservableList<Product> allProducts = FXCollections.observableArrayList(); //Creates the observable list of Products.
	
        /**
         * This method adds a part to the allParts Observable List.
         * @param newPart A Part to be added
         */
	public static void addPart(Part newPart){
            
		allParts.add(newPart);
	}
	
        /**
         * This method adds a product to the allProducts Observable List
         * @param newProduct A Product to be added
         */
	public static void addProduct(Product newProduct){
            
		allProducts.add(newProduct);
	}
	
        /**
         * This method finds the Part that exists at index in allParts and sets it to be the selected Part.
         * @param index The index in the allParts list.
         * @param selectedPart The part that is set at index.
         */
	public static void updatePart(int index, Part selectedPart){
            
            allParts.set(index, selectedPart);
        }
	
        /**
         * This method finds the Product that exists at index in allProducts and sets it to be the selected Product.
         * @param index The index in allProducts list.
         * @param newProduct The product that is set at index.
         */
	public static void updateProduct(int index, Product newProduct){
            
            allProducts.set(index, newProduct);
        }
	
        /**
         * This method removes a part from the allParts list.
         * @param selectedPart A part to be removed
         * @return True if the Product is deleted, False if it is not. 
         */
	public static boolean deletePart(Part selectedPart){
            
            return allParts.remove(selectedPart);
	}
	
        /**
         * This method removes a Product from the allProducts list.
         * @param selectedProduct A product to be removed.
         * @return True if the product is deleted, False if not.
         */
	public static boolean deleteProduct(Product selectedProduct){
            
            return allProducts.remove(selectedProduct);
	}
	
        /**
         * This method is a getter for the allParts field.
         * @return The observable list allParts.
         */
	public static ObservableList<Part> getAllParts(){
            
            return allParts;
        }
	
        /**
         * This is a getter for the allProducts field.
         * @return The observable list allProducts.
         */
	public static ObservableList<Product> getAllProducts(){
            
            return allProducts;
        }
	
        /**
         * This method searches for a part with a particular ID in the allParts list.
         * @param partId The Id that is being searched for.
         * @return part if a part is found with a matching id, null if no part was found to have that Id.
         */
        public static Part lookupPart(int partId){
            
            Part part = new InHouse(-1,"",-1.0,-1,-1,-1,-1);
            for(Part p : allParts){
                if(Integer.toString(p.getId()).equals(Integer.toString(partId))){
                    return p;
                }
            }
            return part;
        }
	
        /**
         * This method searches the allParts list for a part whose name contains a given string. 
         * @param partName The string that is searched for in part names.
         * @return A list of Parts that contains parts whose name's contain partName.
         */
        public static ObservableList<Part> lookupPart(String partName){
            
           ObservableList<Part> filteredParts = FXCollections.observableArrayList();
            if(!partName.equals("")){
                for(Part p : Inventory.getAllParts()){
                    if(p.getName().toLowerCase().contains(partName)){
                        filteredParts.add(p);
                    }
                }
            }
            return filteredParts;
        }
        
        /**
         * This method searches for a product with a given id that exists in the allProducts list.
         * @param productId The Id that is being searched for.
         * @return A product whose Id matches the given Id, else returns null.
         */
	public static Product lookupProduct(int productId){
            
            Product product = new Product(-1,-1,-1,-1,-1.0,"");
            for(Product p : allProducts){
                if(Integer.toString(p.getId()).equals(Integer.toString(productId))){
                    return p;
                }
            }
            
            return product;
        }   
	
        /**
         * This method searches for products whose name's contain a given string. 
         * @param productName The String that is being searched for in product names.
         * @return A list of Products whose name's contain productName.
         */
	public static ObservableList<Product> lookupProduct(String productName){
            
            ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
            if(!productName.equals("")){
                for(Product p : allProducts){
                    if(p.getName().toLowerCase().contains(productName)){
                        filteredProducts.add(p);
                    }
                }
            }
            return filteredProducts;
        }
}