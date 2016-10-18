/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;
import static javafxapplication.fp.controller.CreateProductController.ProductName;
import static javafxapplication.fp.controller.CreateProductController.ProductPrice;
import static javafxapplication.fp.controller.CreateProductController.ProductType;
import javafxapplication.fp.dao.ProductDAO;
import javafxapplication.fp.model.Product;
/**
 *
 * @author karthik
 */
public class ModifyProductController {
    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField ProductID_txt1;
    
    @FXML
    private TextField ProductName_txt;
    
    @FXML
    private TextField ProductPrice_txt;

    @FXML
    RadioButton Alcoholic;
    
    @FXML
    RadioButton NonAlcohol;
    
    private static String ProductID = null;
    private static String ProductName = null;
    private static String ProductType = null;
    private static String ProductPrice = null;
 
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setAlcohol()
    {
       ProductType = Alcoholic.getText();
    }
    
    public void setNonAlcohol()
    {
        ProductType = NonAlcohol.getText().trim();
    }
    
    public void modifyProductProcess()
    {
       ProductID = ProductID_txt1.getText().trim(); 
       ProductName = ProductName_txt.getText().trim();
       ProductPrice = ProductPrice_txt.getText().trim();
        if (ProductID.trim().equals("") || ProductID == null) {
            errorMsg.setText("");
            errorMsg.setText("Product Name cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
       if (ProductName.trim().equals("") || ProductName == null) {
            errorMsg.setText("");
            errorMsg.setText("Product Name cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (ProductType == null) {
            errorMsg.setText("");
            errorMsg.setText("Product Type cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (ProductPrice.trim().equals("") || ProductPrice == null) {
            errorMsg.setText("");
            errorMsg.setText("Product Price cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        //Create the model object
        Product product = new Product();
        int PID = 0;
        double PPrice = 0.0;
        //Create a DAO instance of the model
        ProductDAO pdao = new ProductDAO();
        //Set the values from the input form
        try{
        PID = Integer.parseInt(ProductID);
        PPrice = Double.parseDouble(ProductPrice);
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Check input data");
            errorMsg.setTextFill(Color.web("red"));
            return;            
        }
        product.setId(PID);
        product.setProductName(ProductName);
        product.setProductType(ProductType);
        product.setProductPrice(PPrice);
        //Use the DAO to persist the model to database
        if(PPrice == 0.0)
        {
            errorMsg.setText("");
            errorMsg.setText("Product price cannot be 0.0");
            errorMsg.setTextFill(Color.web("red"));
            return;             
        }
        if(!pdao.search(PID))
        {
            errorMsg.setText("");
            errorMsg.setText("Product not found");
            errorMsg.setTextFill(Color.web("red"));
            return;              
        }
        pdao.modify(product);
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/success.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
    public void cancelProcess() {
                try{
        Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/AdminMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }catch (IOException ex) {
                System.out.println("Exception :" + ex);
            }
    }
            public void LogoutProcess() {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }catch (IOException ex) {
                System.out.println("Exception :" + ex);
            }
    }
}
