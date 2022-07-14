package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * This class is the controller for the ModifyPartForm.
 * @author Jake
 */
public class modifyPartFormController implements Initializable{
    
    //Declarations for stage,scene, and index.
    Stage stage;
    Parent scene;
    int index;
    
    //FXML declarations
    @FXML private RadioButton inHouseRBtn;
    @FXML private RadioButton outSourcedRBtn;
    @FXML private TextField partIdTxt;
    @FXML private TextField partNameTxt;
    @FXML private TextField partInvLvlTxt;
    @FXML private TextField partPriceTxt;
    @FXML private TextField partMaxTxt;
    @FXML private TextField partMinTxt;
    @FXML private TextField partTypeTxt;
    @FXML private Label machIDCompanyNameLbl;
    @FXML private Label partNotif;
    
    /**
     * This method changes the text of the label machIDCompanyNameLbl to 
     * machineId when inHouse is selected.
     * @param event fires when the radio button inHouse is selected/clicked.
     */
    @FXML public void onActionSwitchInHouse(ActionEvent event) {
        
        machIDCompanyNameLbl.setText("Machine ID");
    }

    /**
     * This method changes the text of the label machIDCompanyNameLbl to Company
     * Name when the outsourced button is selected.
     * @param event fires when the radio button outSourced is selected/clicked.
     */
    @FXML public void onActionSwitchOutsourced(ActionEvent event) {
        
        machIDCompanyNameLbl.setText("Company Name");
    }
    
    /**
     * This method returns the user to the main menu.
     * @param event fires when the cancel button is pressed on the AddPartForm in the GUI. 
     * @throws IOException If the file cannot be read.
     */
    @FXML public void onActionCancel(ActionEvent event) throws IOException {
        
        switchViews(event, "/view/mainMenu.fxml");
    }
    
    /**
     * This method updates an index in Inventory's allParts list.
     * @param event fires when the save button is clicked.
     * @throws IOException If file cannot be read.
     */
    @FXML public void onActionUpdate(ActionEvent event) throws IOException {
       
       int id = Inventory.getAllParts().get(index).getId();
       int machineId = 0;
       String companyName = "";
       int stock = -1;
       int max = -1;
       int min = -1;
       double price = -1.0;
       String name = "";
       boolean error = false;
       String errorString = "";
       
        try{  
        
            price = Double.parseDouble(partPriceTxt.getText());
        }
        catch(NumberFormatException a){
        
            errorString += "Price must be a decimal value. \n";
            error = true;
        }
    
        try{  
        
            stock = Integer.parseInt(partInvLvlTxt.getText());
        }
        catch(NumberFormatException b){
        
            errorString += "Stock must be an integer value. \n";
            error = true;
        }
    
        try{  
        
            max = Integer.parseInt(partMaxTxt.getText());
        }
        catch(NumberFormatException c){
        
            errorString += "Max must be an integer value. \n";
            error = true;
        }
    
        try{
       
            min = Integer.parseInt(partMinTxt.getText());
        }
        catch(NumberFormatException d){
        
            errorString += "Min must be an integer value. \n";
            error = true;
        }
    
        if(inHouseRBtn.isSelected()){
            try{
                machineId = Integer.parseInt(partTypeTxt.getText());
            }
            catch(NumberFormatException e){
                errorString+= "Machine ID must be an integer value. \n"; 
                error = true;
            }
        }
    
        name = partNameTxt.getText();
    
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
            if(inHouseRBtn.isSelected()){
           
                Inventory.updatePart(index,new InHouse(id, name, price, stock, min, max, machineId));
            }
            else{
                
            companyName = partTypeTxt.getText();
            Inventory.updatePart(index,new Outsourced(id, name, price, stock, min, max, companyName));
            }
            
            switchViews(event, "/view/mainMenu.fxml");
        }
        else{
            partNotif.setStyle("-fx-text-fill : Red");
            partNotif.setText(errorString);
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
     * the text fields with the selected part's data.
     * @param index The index of the part in allParts 
     * @param part The selected part 
     */
    public void setText(int index, Part part){
        
        partIdTxt.setText(Integer.toString(part.getId()));
        partNameTxt.setText(part.getName());
        partInvLvlTxt.setText(Integer.toString(part.getStock()));
        partPriceTxt.setText(Double.toString(part.getPrice()));
        partMaxTxt.setText(Integer.toString(part.getMax()));
        partMinTxt.setText(Integer.toString(part.getMin()));
        this.index = index;
        
        if(part.getClass().getTypeName().equals("model.InHouse")){
            machIDCompanyNameLbl.setText("Machine ID");
            partTypeTxt.setText(Integer.toString(((InHouse) part).getMachineId()));
        }
        else{
            machIDCompanyNameLbl.setText("Company Name");
            partTypeTxt.setText(((Outsourced) part).getCompanyName());
            outSourcedRBtn.setSelected(true);
        }
    }
    
    /**
     * This method implements from javafx.fxml.Initializable.
     * Fills the rows of the part and associated parts tables.
     * @param url The url
     * @param rb The resource bundle
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
