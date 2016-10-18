/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication.fp.controller;

import javafxapplication.fp.model.User;
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
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.User;
import javafxapplication.fp.dao.UserDAO;

/**
 *
 * @author karthik
 */
public class DeleteUserController implements Initializable {

    private Stage dialogStage;
    @FXML
    private Label errorMsg;

    @FXML
    private TextField UserID_txt;

    private String UserID = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void DeleteUserProcess() {
        UserID = UserID_txt.getText().trim();
        try{
            int UID = Integer.parseInt(UserID);
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("UserID can only be an Integer");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        if (UserID.trim().equals("") || UserID == null) {
            errorMsg.setText("");
            errorMsg.setText("UserID cannot be empty");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        //Create a DAO instance of the model
        User u = new User();
        try{
        u.setId(Integer.parseInt(UserID));
        }catch(Exception e)
        {
            errorMsg.setText("");
            errorMsg.setText("Error! Check input data");
            errorMsg.setTextFill(Color.web("red"));
            return;
        }
        UserDAO udao = new UserDAO();
        //Use the DAO to persist the model to database
        if(!udao.search(u.getId()))
        {
            errorMsg.setText("");
            errorMsg.setText("User not found! Check again.");
            errorMsg.setTextFill(Color.web("red"));
            return;            
        }
        udao.delete(u.getId());
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

