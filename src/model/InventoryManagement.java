package model;

import java.io.IOException;
import javafx.application.Application;  
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class contains the main method that launches the GUI.
 * 
 * G-a)During the creation of this project I had a logical error with the product associated parts.
 * The associated parts list was acting as a pool for all of the product associated parts instead
 * of an individual associated part list for a single product.
 * The error was fixed by making the product variables and methods to be non-static.
 * Line 22 in Product.java
 * 
 * G-b)For the next version of this project, a feature that could extend its usefulness would be to add a way to
 * see the products that the part is associated with. This would be similar to the associated parts table in
 * the modify/add product forms.
 * @author Jake
 * @version 1.0
 */
public class InventoryManagement extends Application{

    /**
     * This method initializes the tables Inventory by creating/adding parts and products.
     * @param args The arguments for main
     */
    public static void main(String[] args) {
       
        /*
         * Creates and adds preset parts and products to the Inventory.
         */
        InHouse part1 = new InHouse(1, "Screw", 0.59, 500, 1, 1000, 25);
        InHouse part2 = new InHouse(2, "Bolt", 5000.0, 10, 1, 1000, 69);
        Outsourced part3 = new Outsourced(3, "Washer", 5000.0, 10, 1, 1000, "Amazon");
        Product product1 = new Product(1,20,1,50,299.99,"Car");
        Product product2 = new Product(2,20,1,50,299.99,"Bike");
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        
        launch(args); //Launches the GUI
    }

    /**
     * start implements a method from Application
     * to set the stage for the GUI.
     * @param stage The window the GUI is set in. 
     * @throws java.io.IOException Fails to read file.
     */
    @Override public void start(Stage stage) throws IOException{
        
       Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
    }
}
