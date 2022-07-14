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
 * This class is the controller for the AddProductForm
 * @author Jake
 */
public class addProductFormController implements Initializable {
    
    //Declaration of stage and scene
    Stage stage;
    Parent scene;
    
    //Initialization of product and associatedParts
    Product product = new Product(0,0,0,0,0.0,"");
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    //FXML Declarations
    @FXML private TextField productNameTxt;
    @FXML private TextField productInvLvlTxt;
    @FXML private TextField productPriceTxt;
    @FXML private TextField productMaxTxt;
    @FXML private TextField productMinTxt;
    @FXML private TextField partsSearchTxt;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLvlCol;
    @FXML private TableColumn<Part, Double> partPricePerUnitCol;
    @FXML private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML private TableColumn<Part, String> associatedPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartInvLvlCol;
    @FXML private TableColumn<Part, Double> associatedPartPriceCol;
    @FXML private Label addProductNotif;
    
    /**
     * This method changes the scene back to the Main menu.
     * @param event fires when the cancel button is clicked.
     * @throws IOException if the file cannot be read.
     */
    @FXML public void onActionCancel(ActionEvent event) throws IOException {
        
        switchViews(event, "/view/mainMenu.fxml");
    }
    
    /**
     * This method adds a part from allParts to the associatedParts list if a
     * part is selected, else a warning is displayed.
     * @param event fires when the add button is clicked.
     */
    @FXML public void onActionAdd(ActionEvent event){
        
        if((partTableView.getSelectionModel().getSelectedItem() == null)){
            addProductNotif.setStyle("-fx-text-fill : Red");
            addProductNotif.setText("No Part Selected");
        }
        else{
            associatedParts.add(partTableView.getSelectionModel().getSelectedItem());
        }
    }
    
    /**
     * This method adds a new product to Inventory's product list if the proper conditions are met.
     * If the product is not added, a string of errors will be displayed as to why. 
     * @param event fires if the save button is clicked.
     * @throws IOException if main menu file cannot be read.
     */
    @FXML public void onActionSave(ActionEvent event) throws IOException{
        
        int id = generateUniquePartId();//Generates a unique id for the product.
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
        
            stock = Integer.parseInt(productInvLvlTxt.getText());
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
            product = new Product(id,stock,min,max,price,name);
            for(int x=0; x < associatedParts.size(); x++){
                product.addAssociatedPart(associatedParts.get(x));
            }
            Inventory.addProduct(product);
            switchViews(event, "/view/mainMenu.fxml");
        }
        else{
            addProductNotif.setStyle("-fx-text-fill : Red");
            addProductNotif.setText(errorString);
        }
}
    
    /**
     * This method removes a selected part from the associated parts list.
     * @param event fires when the remove button is clicked.
     */
    @FXML public void onActionRemoveAssociatedPart(ActionEvent event) {
        
        if(associatedPartTableView.getSelectionModel().getSelectedItem() == null){
                addProductNotif.setStyle("-fx-text-fill : Red");
                addProductNotif.setText("No Part selected");
        }
        else{
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete associated part?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES) {
                
                associatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
                addProductNotif.setStyle("-fx-text-fill : green");
                addProductNotif.setText("Part Deleted.");
            }
        }
    }
    
    /**
     * This method displays to the user the part/parts that they have searched for.
     * @param event fires when the enter key is hit when engaged with the search bar.
     */
    @FXML public void onActionEnterParts(ActionEvent event){
        
        partTableView.setItems(Inventory.getAllParts());
        partTableView.getSelectionModel().clearSelection();
        String searchParam = partsSearchTxt.getText();
        searchParam = searchParam.toLowerCase();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        
        try{
            Part part = Inventory.lookupPart(Integer.parseInt(searchParam));
            if(part.getId() != -1){
                partTableView.getSelectionModel().select(part);
                addProductNotif.setStyle("-fx-text-fill : Green");
                addProductNotif.setText("Parts Found");
            }
            else{
                partTableView.setItems(filteredParts);
                addProductNotif.setStyle("-fx-text-fill : Red");
                addProductNotif.setText("No Parts Found");
            }
        }
        catch(NumberFormatException a){
            filteredParts = Inventory.lookupPart(searchParam);
            partTableView.setItems(filteredParts);
            if(searchParam.equals("")){
                partTableView.setItems(Inventory.getAllParts());
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
     * This method will generate a unique id for the new part. 
     * @return The unique Id for the part.
     */
    private int generateUniquePartId() {
        
        int id = 1;
        int productListSize = Inventory.getAllProducts().size();
        
        for(int x = 0; x < productListSize ; x++){ 
           if(id  == Inventory.getAllProducts().get(x).getId())
           {
               id++;
           }
        }
        
        return id;
    }

    /**
     * This method implements from javafx.fxml.Initializable.
     * Fills the rows of the part and associated parts tables.
     * @param url The url
     * @param rb The resource bundle
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        
       partTableView.setItems(Inventory.getAllParts());
       partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       
       associatedPartTableView.setItems(associatedParts);
       associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       associatedPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
