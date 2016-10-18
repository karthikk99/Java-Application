/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.dao.CustomerDAO;

/**
 *
 * @author karthik
 */
public class DeleteCustomerController {

    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField CustomerID_txt;

    public static String cID = null;

    public void DeleteCustomerProcess() {
        cID = CustomerID_txt.getText().trim();
        if (cID.trim().equals("") || cID == null) {
            errorMsg.setText("");
            errorMsg.setText("Customer ID cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        int pid = 0;
        //Create a DAO instance of the model
        CustomerDAO pdao = new CustomerDAO();
        //Set the values from the input form
        try{
        pid = Integer.parseInt(cID);
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Check your input");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if(!pdao.search(pid))
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Customer does not exists");
            errorMsg.setTextFill(Color.web("red"));
            return;            
        }
        //Use the DAO to persist the model to database
        pdao.delete(pid);
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
