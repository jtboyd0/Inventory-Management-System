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

/**
 * This class is the controller/manipulator of the AddPartForm.
 * @author Jake
 */
public class addPartFormController implements Initializable{
    
    //Stage and Scene declarations
    Stage stage;
    Parent scene;

    //FXML declarations
    @FXML private RadioButton inHouseRBtn;
    @FXML private Label machIDCompanyNameLbl;
    @FXML private TextField partNameTxt;
    @FXML private TextField partInvLvlTxt;
    @FXML private TextField partPriceTxt;
    @FXML private TextField partMaxTxt;
    @FXML private TextField partMinTxt;
    @FXML private TextField machIDCompanyNameTxt;
    @FXML private Label partNotif;
    
    /**
     * This method returns the user to the main menu.
     * @param event fires when the cancel button is pressed on the AddPartForm in the GUI. 
     * @throws IOException If the file cannot be read.
     */
    @FXML public void onActionCancel(ActionEvent event) throws IOException {
        
        switchViews(event, "/view/mainMenu.fxml");
    }
    
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
     * This method will add a new part to the allParts list in Inventory if the
     * proper conditions are met by the entries in the text fields.
     * @param event fires when the save button is clicked.
     * @throws IOException if the file cannot be read.
     */
    @FXML public void onActionSave(ActionEvent event) throws IOException {
       
        /*
         Initialization of machineId, companyName, id, stock, max, min, price, 
        name, error, and errorString.
        */
        int machineId = 0; 
        String companyName = "";
        int id = generateUniquePartId(); //A unique id is generated for the part. See method generateUniqueId.
        int stock = -1;
        int max = -1;
        int min = -1;
        double price = -1.0;
        String name = "";
        boolean error = false; //Flags an error that has occured.
        String errorString = ""; //String that is displayed when an error/ errors have occured.
       
        /*
         This block tries to assign the price variable as the price text field.
         If a Number format exception error is caught, an alert will be added to the errorString.
        */
        try{  
        
            price = Double.parseDouble(partPriceTxt.getText());
        }
        catch(NumberFormatException a){
        
            errorString += "Price must be a decimal value. \n";
            error = true;
        }
    
        /*
        This block tries to assign the stock variable as the stock text field.
        If a Number format exception error is caught a proper alert will be added to the errorString.
        */
        try{  
        
            stock = Integer.parseInt(partInvLvlTxt.getText());
        }
        catch(NumberFormatException b){
        
            errorString += "Stock must be an integer value. \n";
            error = true;
        }
    
        /*
        This block tries to assign the max variable as the max text field.
        If a Number format exception error is caught a proper alert will be added to the errorString.
        */
        try{  
        
            max = Integer.parseInt(partMaxTxt.getText());
        }
        catch(NumberFormatException c){
        
            errorString += "Max must be an integer value. \n";
            error = true;
        }
    
        /*
        This block tries to assign the min variable as the min text field.
        If a Number format exception error is caught a proper alert will be added to the errorString.
        */
        try{
       
            min = Integer.parseInt(partMinTxt.getText());
        }
        catch(NumberFormatException d){
        
            errorString += "Min must be an integer value. \n";
            error = true;
        }
    
        /*
        This block checks if the inHouse button is selected and if it is, it tries to assign
        the machineId variable as the machIdCompanyName.
        If a Number format exception error is caught a proper alert will be added to the errorString.
        */
        if(inHouseRBtn.isSelected()){
            try{
                machineId = Integer.parseInt(machIDCompanyNameTxt.getText());
            }
            catch(NumberFormatException e){
                errorString+= "Machine ID must be an integer value. \n"; 
                error = true;
            }
        }
        
        //Assigns name to the partNameTxt.
        name = partNameTxt.getText();
    
        /*
        This block makes some logical checks, and if they are not adds an error to the error string.
        */
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
    
        /*
        If an error has not occured, a new part will be created of the respective subclass and added to the inventory. The main form will then be shown.
        Else, the error string is shown to the user in red text.
        */
        if(!error){
            if(inHouseRBtn.isSelected()){
           
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }
            else{
                companyName = machIDCompanyNameTxt.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
            switchViews(event, "/view/mainMenu.fxml");
        }
        else{
            partNotif.setStyle("-fx-text-fill : Red");
            partNotif.setText(errorString);
        }
    }
    
    /**
     * This method overrides from javafx.fxml.Initializable.
     * @param url The url
     * @param rb The resource bundle
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    /**
     * This method switches the view from the addPartForm to the specified file location.
     * @param event fires when the cancel or save buttons are clicked and the proper conditions are met.
     * @param fileLocation What scene will be shown to the user.
     * @throws IOException If the file is not read.
     */
    void switchViews(ActionEvent event, String fileLocation) throws IOException{
        
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
        int partListSize = Inventory.getAllParts().size();
        
        for(int x = 0; x < partListSize ; x++){ 
           if(id  == Inventory.getAllParts().get(x).getId())
           {
               id++;
           }
        }
        
        return id;
    }
}
