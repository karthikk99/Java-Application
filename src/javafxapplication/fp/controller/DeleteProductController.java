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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.Product;
import javafxapplication.fp.dao.ProductDAO;

/**
 *
 * @author karthik
 */
public class DeleteProductController {

    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField ProductID_txt;

    public static String PID = null;

    public void deleteProductProcess() {
        PID = ProductID_txt.getText().trim();
        if (PID.trim().equals("") || PID == null) {
            errorMsg.setText("");
            errorMsg.setText("Product ID cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        //Create a DAO instance of the model
        ProductDAO pdao = new ProductDAO();
        int pid = 0;
        //Set the values from the input form
        try {
            pid = Integer.parseInt(PID);
        } catch (Exception e) {
            errorMsg.setText("");
            errorMsg.setText("Error! Check input data");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        //Use the DAO to persist the model to database
        if(!pdao.search(pid))
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Prouduct does not exist");
            errorMsg.setTextFill(Color.web("red"));
            return; 
        }
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
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/AdminMenu.fxml"));
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
}
