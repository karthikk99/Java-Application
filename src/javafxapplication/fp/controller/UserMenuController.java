/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafxapplication.fp.JavaFXApplication.stage;

/**
 *
 * @author karthik
 */
public class UserMenuController {

    public void BillProductProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/BillProduct.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
    
    public void CustomerListProcess()
    {
    try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/CustomerList.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }   
    }
    
    public void deleteOrderProcess() {
    try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/DeleteOrder.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
    public void DisplayOrderProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/DisplayOrder.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }

    public void LogoutProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }

    public void ModifyCustomerProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/ModifyCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
    
    public void DeleteCustomerProcess()
    {
            try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/DeleteCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }   
    }
    
    public void ProductListProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/ProductList.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
}
