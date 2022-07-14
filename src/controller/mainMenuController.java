package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * This class is the controller for the MainMenuForm
 * @author Jake
 */
public class mainMenuController implements Initializable{

    //stage and scene declarations
    Stage stage;
    Parent scene;
    
    //FXML Declarations
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLvlCol;
    @FXML private TableColumn<Part, Double> partPricePerUnitCol;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInvLvlCol;
    @FXML private TableColumn<Product, Double> productPricePerUnitCol;
    @FXML private TextField partsSearchTxt;
    @FXML private TextField productsSearchTxt;
    @FXML private Label productsNotifLbl;
    @FXML private Label partsNotifLbl;
    
    /**
     * This method switches the GUI scene to the AddPartForm.
     * @param event Fires when the add button is clicked under the parts table.
     * @throws IOException if the file cannot be read.
     */
    @FXML public void onActionAddPart(ActionEvent event) throws IOException {
        
        switchViews(event, "/view/AddPartForm.fxml");
    }
    
    /**
     * This method switches the GUI scene to the AddProductForm.
     * @param event Fires when the add button is clicked under the products table.
     * @throws IOException if the file cannot be read.
     */
    @FXML public void onActionAddProduct(ActionEvent event) throws IOException {
        
        
        switchViews(event, "/view/AddProductForm.fxml");
    }

    /**
     * This method switches the GUI scene to the ModifyPartForm.
     * @param event Fires when the modify button is clicked under the parts table.
     * @throws IOException if the file cannot be read.
     */
    @FXML public void onActionModifyPart(ActionEvent event) throws IOException {
        
        if((partTableView.getSelectionModel().getSelectedItem() == null)){
            partsNotifLbl.setStyle("-fx-text-fill : Red");
            partsNotifLbl.setText("No Part Selected");
        }
        else{
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            Parent modifyPartFormParent = Loader.load();
            Scene modifyPartFormScene = new Scene(modifyPartFormParent);
            modifyPartFormController controller = Loader.getController();
            controller.setText(partTableView.getSelectionModel().getSelectedIndex(),partTableView.getSelectionModel().getSelectedItem());
            Stage window = (Stage)((Button)event.getSource()).getScene().getWindow();
            window.setScene(modifyPartFormScene);
            window.show();
        }
    }   

    /**
     * This method switches the GUI scene to the ModifyProductForm.
     * @param event Fires when the modify button is clicked under the products table.
     * @throws IOException if the file cannot be read.
     */
    @FXML public void onActionModifyProduct(ActionEvent event) throws IOException {
        
        if(productTableView.getSelectionModel().getSelectedItem() == null){
            productsNotifLbl.setStyle("-fx-text-fill : Red");
            productsNotifLbl.setText("No Product Selected");
        }
        else{
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            Parent modifyProductFormParent = Loader.load();
            Scene modifyProductFormScene = new Scene(modifyProductFormParent);
            modifyProductFormController controller = Loader.getController();
            controller.setText(productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts(),productTableView.getSelectionModel().getSelectedIndex(),productTableView.getSelectionModel().getSelectedItem());
            Stage window = (Stage)((Button)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductFormScene);
            window.show();
        }
    }
    
    /**
     * This button closes the GUI window.
     * @param event fires if the exit button is clicked on the main form.
     */
    @FXML void onActionExit(ActionEvent event){
        
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         stage.close();
    }
    
    /**
     * This method removes a selected part from the parts table if the deletion of the part is confirmed by the user.
     * @param event fires when the delete button has been pressed.
     */
    @FXML public void onActionDeletePart(ActionEvent event){
        
        if(partTableView.getSelectionModel().getSelectedItem() == null){
                partsNotifLbl.setStyle("-fx-text-fill : Red");
                partsNotifLbl.setText("No Part selected");
        }
        else{
            
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete part?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES) {
                
                Inventory.getAllParts().remove(partTableView.getSelectionModel().getSelectedItem());
                partsNotifLbl.setStyle("-fx-text-fill : green");
                partsNotifLbl.setText("Part Deleted.");
                partTableView.setItems(Inventory.getAllParts());
                partsSearchTxt.setText("");
            }
        }
    }  

    /**
     * This method removes a selected product from the products table if the deletion of the part is confirmed by the user.
     * @param event fires when the delete button is clicked under the product table.
     */
    @FXML public void onActionDeleteProduct(ActionEvent event){

        if(productTableView.getSelectionModel().getSelectedItem() == null){
                productsNotifLbl.setStyle("-fx-text-fill : Red");
                productsNotifLbl.setText("No Product selected");
        }
        else if(!productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
            
                productsNotifLbl.setStyle("-fx-text-fill : Red");
                productsNotifLbl.setText("Product cannot be deleted if it has parts associated to it.");
        }
        else{
            
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete product?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES) {
                
                Inventory.getAllProducts().remove(productTableView.getSelectionModel().getSelectedItem());
                productsNotifLbl.setStyle("-fx-text-fill : green");
                productsNotifLbl.setText("Product Deleted.");
                productTableView.setItems(Inventory.getAllProducts());
                productsSearchTxt.setText("");
            }
        }
    }
    
    /**
     * This method updates the parts table with parts that match the search query.
     * @param event fires when the user hits enter after selecting the parts search text field.
     */
    @FXML public void onActionEnterParts(ActionEvent event){
        
        partTableView.setItems(Inventory.getAllParts());
        partTableView.getSelectionModel().clearSelection();
        String searchParam = partsSearchTxt.getText();
        searchParam = searchParam.toLowerCase();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();;
        
        try{
            Part part = Inventory.lookupPart(Integer.parseInt(searchParam));
            if(part.getId() != -1){
                partTableView.getSelectionModel().select(part);
                partsNotifLbl.setStyle("-fx-text-fill : Green");
                partsNotifLbl.setText("Parts Found");
            }
            else{
                partTableView.setItems(filteredParts);
                partsNotifLbl.setStyle("-fx-text-fill : Red");
                partsNotifLbl.setText("No Parts Found");
            }
        }
        catch(NumberFormatException a){
            filteredParts = Inventory.lookupPart(searchParam);
            partTableView.setItems(filteredParts);
            if(searchParam.equals("")){
                partTableView.setItems(Inventory.getAllParts());
                partsNotifLbl.setStyle("-fx-text-fill : Red");
                partsNotifLbl.setText("Search is blank");
            }
            else{
                if(!filteredParts.isEmpty()){
                    partsNotifLbl.setStyle("-fx-text-fill : Green");
                    partsNotifLbl.setText("Parts Found");
                }
                else{
                    partsNotifLbl.setStyle("-fx-text-fill : Red");
                    partsNotifLbl.setText("No Parts Found");
                }
            }
        }
    }
    
    /**
     * This method updates the products table with products that match the search query.
     * @param event fires when the user hits enter after selecting the products search text field.
     */
    @FXML public void onActionEnterProducts(ActionEvent event){
        
        productTableView.setItems(Inventory.getAllProducts());
        productTableView.getSelectionModel().clearSelection();
        String searchParam = productsSearchTxt.getText();
        searchParam = searchParam.toLowerCase();
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        
        try{
            Product product = Inventory.lookupProduct(Integer.parseInt(searchParam));
            if(product.getId() != -1){
                productTableView.getSelectionModel().select(product);
                productsNotifLbl.setStyle("-fx-text-fill : Green");
                productsNotifLbl.setText("Products Found");
            }
            else{
                productTableView.setItems(filteredProducts);
                productsNotifLbl.setStyle("-fx-text-fill : Red");
                productsNotifLbl.setText("No Products Found");
            }
        }
        catch(NumberFormatException a){
            filteredProducts = Inventory.lookupProduct(searchParam);
            productTableView.setItems(filteredProducts);
            if(searchParam.equals("")){
                productTableView.setItems(Inventory.getAllProducts());
                productsNotifLbl.setStyle("-fx-text-fill : Red");
                productsNotifLbl.setText("Search is blank");
            }
            else{
                if(!filteredProducts.isEmpty()){
                    productsNotifLbl.setStyle("-fx-text-fill : Green");
                    productsNotifLbl.setText("Products Found");
                }
                else{
                    productsNotifLbl.setStyle("-fx-text-fill : Red");
                    productsNotifLbl.setText("No Products Found");
                }
            }
        }
    }
     
    /**
     * This method fills the rows of the part and table view with information.
     * @param url The url
     * @param rb The resource bundle
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        
       partTableView.setItems(Inventory.getAllParts());
       partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       
       productTableView.setItems(Inventory.getAllProducts());
       productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       productInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
     /**
     * This method switches the view from the addPartForm to the specified file location.
     * @param event fires when the cancel or save buttons are clicked and the proper conditions are met.
     * @param fileLocation What scene will be shown to the user.
     * @throws IOException If the file is not read.
     */
    public void switchViews(ActionEvent event, String fileLocation) throws IOException{
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(fileLocation));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}

