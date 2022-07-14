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
 * This class is the controller for the modifyProductForm.
 * @author Jake
 */
public class modifyProductFormController implements Initializable{
    
    Stage stage;
    Parent scene;
    int index;
    Product product = new Product(0,0,0,0,0.0,"");
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    @FXML private TableView<Part> associatedPartsTableView;
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLvlCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML private TableColumn<Part, String> associatedPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartInvLvlCol;
    @FXML private TableColumn<Part, Double> associatedPartPriceCol;
    @FXML private TextField productIdTxt;
    @FXML private TextField productNameTxt;
    @FXML private TextField productInvTxt;
    @FXML private TextField productPriceTxt;
    @FXML private TextField productMaxTxt;
    @FXML private TextField productMinTxt;
    @FXML private TextField partsSearchTxt;
    @FXML private Label addProductNotif;
  
    /**
     * This method returns the GUI to the main menu.
     * @param event fires when the cancel button is clicked.
     * @throws IOException If the file cannot be read.
     */
    @FXML public void onActionCancel(ActionEvent event) throws IOException {
        
        switchViews(event, "/view/mainMenu.fxml");
    }

    /**
     * This method updates an index in the all products list if all of the checks are met.
     * @param event fires when the save button is clicked.
     * @throws IOException If the file cannot be read.
     */
    @FXML public void onActionSave(ActionEvent event) throws IOException {
        
        int Id = product.getId();
        int stock = -1;
        int max = -1;
        int min = -1;
        double price = -1.0;
        String name = "";
        boolean error = false;
        String errorString = "";
       
        try{  
        
            price = Double.parseDouble(productPriceTxt.getText());
        }
        catch(NumberFormatException a){
        
            errorString += "Price must be a decimal value. \n";
            error = true;
        }
    
        try{  
        
            stock = Integer.parseInt(productInvTxt.getText());
        }
        catch(NumberFormatException b){
        
            errorString += "Stock must be an integer value. \n";
            error = true;
        }
    
        try{  
        
            max = Integer.parseInt(productMaxTxt.getText());
        }
        catch(NumberFormatException c){
        
            errorString += "Max must be an integer value. \n";
            error = true;
        }
    
        try{
       
            min = Integer.parseInt(productMinTxt.getText());
        }
        catch(NumberFormatException d){
        
            errorString += "Min must be an integer value. \n";
            error = true;
        }
    
        name = productNameTxt.getText();
    
        if(stock <= 0){
        
            errorString += "Inv must be greater than or equal to zero. \n";
            error = true;
        }
    
        if(min <= 0){
        
            errorString += "Min must be greater than or equal to zero. \n";
            error = true;
        }
    
        if(max < 0){
        
            errorString += "Max must be greater than zero. \n";
            error = true;
        }
    
        if(price <= 0){
        
            errorString += "Price must be greater than zero. \n";
            error = true;
        }
    
        if(name.equals("")){
        
            errorString += "Name must not be blank. \n";
            error = true;
        }
    
        if(stock < min || stock > max){
        
            errorString += "Stock value must be between min and max. \n"; 
            error = true;
        }
    
        if(min >= max){
        
            errorString+= "Min must be less than and not equal to max. \n"; 
            error = true;
        }
    
        if(!error){
            product = new Product(Id, stock, min, max, price, name);
            for(int x = 0; x < associatedParts.size(); x++){
            
                product.addAssociatedPart(associatedParts.get(x));
            }
            Inventory.updateProduct(index, product);
            switchViews(event, "/view/mainMenu.fxml");
        }
        else{
            addProductNotif.setStyle("-fx-text-fill : Red");
            addProductNotif.setText(errorString);
        }
    }
    
    /**
     * This method adds a selected associated part to the associated parts list. 
     * @param event fires when the add button is clicked.
     */
    @FXML public void onActionAdd(ActionEvent event) {
    
        if((partsTableView.getSelectionModel().getSelectedItem() == null)){
            addProductNotif.setStyle("-fx-text-fill : Red");
            addProductNotif.setText("No Part Selected");
        }
        else{
           associatedParts.add(partsTableView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * This method removes an associated part from the associated part list.
     * @param event fires when the remove button is clicked.
     */
    @FXML public void onActionRemoveAssociatedPart(ActionEvent event) {
    
        if(associatedPartsTableView.getSelectionModel().getSelectedItem() == null){
                addProductNotif.setStyle("-fx-text-fill : Red");
                addProductNotif.setText("No Part selected");
        }
        else{
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete associated part?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES) {
                
                associatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
                addProductNotif.setStyle("-fx-text-fill : green");
                addProductNotif.setText("Part Deleted.");
            }
        }
    }
    
    /**
     * This method updates the parts table to show the parts the user has searched for.
     * @param event fires when the enter key is hit when the search bar is selected. 
     */
    @FXML public void onActionEnterParts(ActionEvent event){
        
        partsTableView.setItems(Inventory.getAllParts());
        partsTableView.getSelectionModel().clearSelection();
        String searchParam = partsSearchTxt.getText();
        searchParam = searchParam.toLowerCase();
        ObservableList<Part> filteredParts =  FXCollections.observableArrayList();;
        
        try{
            Part part = Inventory.lookupPart(Integer.parseInt(searchParam));
            if(part.getId() != -1){
                partsTableView.getSelectionModel().select(part);
                addProductNotif.setStyle("-fx-text-fill : Green");
                addProductNotif.setText("Parts Found");
            }
            else{
                partsTableView.setItems(filteredParts);
                addProductNotif.setStyle("-fx-text-fill : Red");
                addProductNotif.setText("No Parts Found");
            }
        }
        catch(NumberFormatException a){
            filteredParts = Inventory.lookupPart(searchParam);
            partsTableView.setItems(filteredParts);
            if(searchParam.equals("")){
                partsTableView.setItems(Inventory.getAllParts());
                addProductNotif.setStyle("-fx-text-fill : Red");
                addProductNotif.setText("Search is blank");
            }
            else{
                if(!filteredParts.isEmpty()){
                    addProductNotif.setStyle("-fx-text-fill : Green");
                    addProductNotif.setText("Parts Found");
                }
                else{
                    addProductNotif.setStyle("-fx-text-fill : Red");
                    addProductNotif.setText("No Parts Found");
                }
            }
        }
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

     /**
     * This method is used to pass data between forms. It serves to load
     * the text fields with the selected product's data and get the products associated parts.
     * @param index The index of the part in allParts 
     * @param product The selected part 
     * @param associatedParts The product's associated Parts
     */
    public void setText(ObservableList<Part> associatedParts, int index, Product product) {
        
        this.product = product;
        productIdTxt.setText(Integer.toString(product.getId()));
        productNameTxt.setText(product.getName());
        productInvTxt.setText(Integer.toString(product.getStock()));
        productPriceTxt.setText(Double.toString(product.getPrice()));
        productMinTxt.setText(Integer.toString(product.getMin()));
        productMaxTxt.setText(Integer.toString(product.getMax()));
        this.index = index;
        
        for(int x = associatedParts.size()-1; x >= 0; x--){
            if(!Inventory.getAllParts().contains(associatedParts.get(x))){
                associatedParts.remove(associatedParts.get(x));
            }
        }
        
        this.associatedParts.setAll(associatedParts);
    }

    /**
     * This method implements from javafx.fxml.Initializable.
     * Fills the rows of the part and associated parts tables.
     * @param url The url
     * @param rb The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       partsTableView.setItems(Inventory.getAllParts());
       partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       partPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       
       associatedPartsTableView.setItems(associatedParts);
       associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       associatedPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
