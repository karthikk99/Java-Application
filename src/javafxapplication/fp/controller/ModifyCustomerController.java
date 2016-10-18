/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafxapplication.fp.model.Customer;
import javafxapplication.fp.dao.CustomerDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;

/**
 *
 * @author karthik
 */
public class ModifyCustomerController {

    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField CustomerID_txt;

    @FXML
    private TextField CustomerName_txt;

    @FXML
    private TextField CustomerAddress_txt;

    @FXML
    private TextField CCNo_txt;

    @FXML
    private TextField CAge_txt;

    private static String CustomerID = null;
    private static String CustomerName = null;
    private static String CAddress = null;
    private static String CCNo = null;
    private static String CAge = null;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void modifyCustomerProcess() {
        CustomerID = CustomerID_txt.getText().trim();
        CustomerName = CustomerName_txt.getText().trim();
        CAddress = CustomerAddress_txt.getText().trim();
        CCNo = CCNo_txt.getText().trim();
        CAge = CAge_txt.getText().trim();
        if (CustomerID.trim().equals("") || CustomerID == null) {
            errorMsg.setText("");
            errorMsg.setText("Customer ID cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (CustomerName.trim().equals("") || CustomerName == null) {
            errorMsg.setText("");
            errorMsg.setText("Customer Name cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (CAddress.trim().equals("") || CAddress == null) {
            errorMsg.setText("");
            errorMsg.setText("Customer Address cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (CCNo.trim().equals("") || CCNo == null) {
            errorMsg.setText("");
            errorMsg.setText("Customer Contact No. cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (CAge.trim().equals("") || CAge == null) {
            errorMsg.setText("");
            errorMsg.setText("Customer Age cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        //Create the model object
        Customer customer = new Customer();
        //Create a DAO instance of the model
        CustomerDAO cdao = new CustomerDAO();
        try{
        //Set the values from the input form
        int PID = Integer.parseInt(CustomerID);
        customer.setId(PID);
        customer.setCustomerName(CustomerName);
        customer.setCustomerAddress(CAddress);
        customer.setCustomerContactNo(Long.parseLong(CCNo));
        customer.setAge(Integer.parseInt(CAge));
        //Use the DAO to persist the model to database
        if(!cdao.search(PID))
        {
            errorMsg.setText("");
            errorMsg.setText("Customer not found. Check again");
            errorMsg.setTextFill(Color.web("red"));
            return;            
        }
        cdao.modify(customer);
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Check your input data");
            errorMsg.setTextFill(Color.web("red"));
            return;           
        }

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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/UserMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
}
